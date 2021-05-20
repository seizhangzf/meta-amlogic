#!/bin/bash -e

SOURCE_PATH=$(pwd -P)
SCRIPT_NAME=$(basename $0)
function usage() {
    cat << EOF
    Usage: $SCRIPT_NAME
                        [-w workspace]
                        [-p aml-patches-folder]
                        [-f aml-patches-file]
                        [-u aml-patches-url]
                        [-a]
                        [-d]
                        [-h|?]
Options:
    -w       Specify the workspace, default to take current folder
    -p       Specify the aml patches folder
    -f       Specify the aml patches tgz file (from jenkins buid)
    -u       Specify the aml patches tgz file url (from jenkins build)
    -a       Enable Advance Mode, which will search aml-comp automatically
    -d       Enable Debug Mode
    -h|?     Show this usage document
EOF
    exit 0
}

[[ "$#" -eq 0 ]] && usage
#args=$(getopt -n $SCRIPT_NAME -o w:p:t:dh -l \
#workspace:,patch:,topic:,debug,help -- $@)
#eval set -- "$args"
#while [ $# -gt 0 ]; do
while getopts "w:p:f:u:dah" opt; do
    case $opt in
        w)
            SOURCE_PATH=$(realpath $OPTARG)
            ;;
        p)
            AML_PATCHES_PATH=$(realpath $OPTARG)
            ;;
        f)
            AML_PATCHES_FILE=$(realpath $OPTARG)
            ;;
        u)
            AML_PATCHES_URL=$OPTARG
            ;;
        d)
            DEBUG_MODE="YES"
            ;;
        a)
            ADVANCE_MODE="YES"
            ;;
          h|?)
            usage
            ;;
    esac
done

export PS4='+ [$(basename ${BASH_SOURCE})] [${LINENO}] '
[[ -n "$DEBUG_MODE" ]] && set -x

AML_PATCHES_FILE_TMP=$(mktemp /tmp/aml_yocto_patch_file_XXXXXX.tgz )
AML_PATCHES_PATH_TMP=$(mktemp -d /tmp/aml_yocto_patch_folder_XXXXXX)

function check_input_param() {
    local _RET_VALUE=0

    if [ -f $SOURCE_PATH/.aml-yocto-patch-done ]; then
        echo "Patches already be applied to this workspace: $SOURCE_PATH"
        echo "Please clean up and delete .aml-yocto-patch-done in workspace"
        _RET_VALUE=1
    fi

    if [ ! -d $SOURCE_PATH/.repo ];
    then
        echo "$SOURCE_PATH don't have .repo folder"
        _RET_VALUE=1
    fi

    #1, Check patch file URL, download it to local as a aml-patch-tgz file
    if [ -n "$AML_PATCHES_URL" ]; then
        rm -f $AML_PATCHES_FILE_TMP
        wget $AML_PATCHES_URL -O $AML_PATCHES_FILE_TMP
        if [ -f "$AML_PATCHES_FILE_TMP" ]; then
            AML_PATCHES_FILE=$AML_PATCHES_FILE_TMP
        else
            echo "Download from $AML_PATCHES_URL failed"
            _RET_VALUE=1
            return $_RET_VALUE
        fi
    fi

    #2, Check local aml patch file
    if [ -n "$AML_PATCHES_FILE" ]; then
        if [ ! -f "$AML_PATCHES_FILE" ]; then
            echo "$AML_PATCHES_FILE is not a valid patch tgz file"
            _RET_VALUE=1
            return $_RET_VALUE
        else
            tar -zxf $AML_PATCHES_FILE -C $AML_PATCHES_PATH_TMP
            AML_PATCHES_PATH=$AML_PATCHES_PATH_TMP/Patches
        fi
    fi

    #3, Check local aml patch folder
    if [ ! -d "$AML_PATCHES_PATH" ]; then
        echo "$AML_PATCHES_PATH is not a folder"
        _RET_VALUE=1
    fi

    return $_RET_VALUE
}

EXIT_VALUE=0


if check_input_param; then
  touch $SOURCE_PATH/.aml-yocto-patch-done
  for PatchFolder in $(for p in $(find $AML_PATCHES_PATH -name *.patch -printf "%P\n"); do dirname $p; done | sort -u | xargs);
  do
        if [ -d $SOURCE_PATH/$PatchFolder ]; then
            PROJ_PATH=$SOURCE_PATH/$PatchFolder
        elif [ -d $SOURCE_PATH/aml-comp/$PatchFolder ] && [ -n "$ADVANCE_MODE" ]; then
            PROJ_PATH=$SOURCE_PATH/aml-comp/$PatchFolder
        else
            echo "$SOURCE_PATH/$PatchFolder not exist"
            continue
        fi
        for Patch in $(find $AML_PATCHES_PATH/$PatchFolder -maxdepth 1 -name '*.patch' -printf "%P\n" | sort | xargs)
        do
            echo "Try applying patch: $AML_PATCHES_PATH/$PatchFolder/$Patch..."
            if git -C $PROJ_PATH apply --check $AML_PATCHES_PATH/$PatchFolder/$Patch; then
                git -C $PROJ_PATH am $AML_PATCHES_PATH/$PatchFolder/$Patch
            else
                echo "$PatchFolder/$Patch can't be applied to $PROJ_PATH"
                exit 1
            fi
            echo "....Done"
        done
  done

else
	echo "Error: Input parameters check failed"
	EXIT_VALUE=1
fi

rm -fr $AML_PATCHES_FILE_TMP
rm -fr $AML_PATCHES_PATH_TMP

exit $EXIT_VALUE

# vim: set shiftwidth=4 tabstop=4 expandtab
