#!/bin/sh

if [ -n "$BASH_SOURCE" ]; then
  THIS_SCRIPT=$BASH_SOURCE
elif [ -n "$ZSH_NAME" ]; then
  THIS_SCRIPT=$0
else
  THIS_SCRIPT="$(pwd)/oe-init-build-env"
fi
MESON_PATH=$(cd `dirname $(realpath -P $THIS_SCRIPT)`; pwd)

LOCAL_DIR=$(pwd)
if [ -z $BUILD_DIR ]; then
	BUILD_DIR="$LOCAL_DIR/build"
fi

DEFCONFIG_ARRAY=($(pushd $MESON_PATH/conf/machine 2>&1 >> /dev/null; find -name '*\.conf' | sed 's@./@@' | sed 's@\.conf@@' | sort))

DEFCONFIG_ARRAY_LEN=${#DEFCONFIG_ARRAY[@]}

i=0
while [[ $i -lt $DEFCONFIG_ARRAY_LEN ]]
do
    let i++
done

function choose_info()
{
    echo
    echo "You're building on Linux"
    echo "Lunch menu...pick a combo:"
    i=0
    while [[ $i -lt $DEFCONFIG_ARRAY_LEN ]]
    do
        echo -e "$((${i}+1)).\t${DEFCONFIG_ARRAY[$i]}"
        let i++
    done
    echo
}

function get_index() {
    if [ $# -eq 0 ]; then
        return 0
    fi

    i=0
    while [[ $i -lt $DEFCONFIG_ARRAY_LEN ]]
    do
        if [ $1 = "${DEFCONFIG_ARRAY[$i]}" ]; then
            let i++
            return ${i}
        fi
        let i++
    done
    return 0
}

function choose_type()
{
    choose_info
    local DEFAULT_NUM DEFAULT_VALUE
    DEFAULT_NUM=2
    DEFAULT_VALUE="mesong12a_u212.conf"

    export TARGET_MACHINE=
    local ANSWER
    while [ -z $TARGET_MACHINE ]
    do
        echo -n "Which would you like? ["$DEFAULT_NUM"] "
        if [ -z "$1" ]; then
            read ANSWER
        else
            echo $1
            ANSWER=$1
        fi

        if [ -z "$ANSWER" ]; then
            ANSWER="$DEFAULT_NUM"
        fi

        if [ -n "`echo $ANSWER | sed -n '/^[0-9][0-9]*$/p'`" ]; then
            if [ $ANSWER -le $DEFCONFIG_ARRAY_LEN ] && [ $ANSWER -gt 0 ]; then
                index=$((${ANSWER}-1))
                TARGET_MACHINE=${DEFCONFIG_ARRAY[$index]}
            else
                echo
                echo "number not in range. Please try again."
                echo
            fi
        else
            get_index $ANSWER
            ANSWER=$?
            if [ $ANSWER -gt 0 ]; then
                index=$((${ANSWER}-1))
                TARGET_MACHINE=${DEFCONFIG_ARRAY[$index]}
            else
                echo
                echo "I didn't understand your response.  Please try again."
                echo
            fi
        fi
        if [ -n "$1" ]; then
            break
        fi
    done
}

function lunch()
{
    if [ -n "$TARGET_MACHINE" ]; then
        MACHINE=$TARGET_MACHINE source meta-amlogic/setup-environment $BUILD_DIR
    if [ "$OPENLINUX_BUILD" == "1" ];then
        cat >> $BUILD_DIR/conf/local.conf <<EOF
#Force OpenLinux Access
AML_GIT_ROOT = "git@openlinux.amlogic.com/yocto"
AML_GIT_PROTOCOL = "ssh"
AML_GIT_ROOT_YOCTO_SUFFIX = ""
AML_GIT_ROOT_PR = "git@openlinux.amlogic.com"
AML_GIT_ROOT_WV = "git@openlinux.amlogic.com/yocto"
AML_GIT_ROOT_PROTOCOL = "ssh"
EOF
    fi
        export MACHINE=$TARGET_MACHINE
        echo "==========================================="
		echo
		echo "MACHINE=${TARGET_MACHINE}"
		echo "OUTPUT_DIR=${BUILD_DIR}"
		echo
		echo "==========================================="
	fi
}
function function_stuff()
{
	choose_type $@
	lunch
}
function_stuff $@
