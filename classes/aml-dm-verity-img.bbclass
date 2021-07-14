inherit dm-verity-img

process_verity() {
    local ENV="${STAGING_VERITY_DIR}/${IMAGE_BASENAME}.$TYPE.verity.env"
    install -d ${STAGING_VERITY_DIR}
    rm -f $ENV

    # Each line contains a key and a value string delimited by ':'. Read the
    # two parts into separate variables and process them separately. For the
    # key part: convert the names to upper case and replace spaces with
    # underscores to create correct shell variable names. For the value part:
    # just trim all white-spaces.
    IFS=":"
    while read KEY VAL; do
        if [ "$KEY" != "" ] && [ "$VAL" != "" ]; then
            printf '%s=%s\n' \
                "$(echo "$KEY" | tr '[:lower:]' '[:upper:]' | sed 's/ /_/g')" \
                "$(echo "$VAL" | tr -d ' \t')" >> $ENV
        fi
    done

    # Add partition size
    echo "DATA_SIZE=$SIZE" >> $ENV

    # bbwarn "`cat $ENV`"
}

verity_setup() {
    local TYPE=$1
    local INPUT=${IMAGE_NAME}${IMAGE_NAME_SUFFIX}.$TYPE
    local SIZE=$(stat --printf="%s" $INPUT)
    #local OUTPUT=$INPUT.verity
    VERITYSETUP_LOG=${STAGING_VERITY_DIR}/${INPUT}.veritysetup.log

    # Let's drop the first line of output (doesn't contain any useful info)
    # and feed the rest to another function.
    if [ -f $INPUT ]; then
        bbwarn "`veritysetup --debug --data-block-size=1024 --hash-offset=$SIZE format $INPUT $INPUT | tee $VERITYSETUP_LOG`"
    else
        bberror "Cannot find $INPUT"
        exit 1
    fi
    cat $VERITYSETUP_LOG | tail -n +2 | process_verity
}
