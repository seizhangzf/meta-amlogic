#!/bin/sh

case "$1" in
  start)
    if [ -f /lib/firmware/dspboota.bin ]; then
        # load firmware
        /usr/bin/dsp_util --load --dsp hifi4a -f dspboota.bin

        # Launch DSP rtos
        /usr/bin/dsp_util -S --dsp hifi4a
    fi

    if [ -f /lib/firmware/dspbootb.bin ]; then
        # load firmware
        /usr/bin/dsp_util --load --dsp hifi4b -f dspbootb.bin

        # Launch DSP rtos
        /usr/bin/dsp_util -S --dsp hifi4b
    fi
    ;;
  stop)
    echo -n "Do thing: "
    ;;
  *)
    echo $"Usage: $0 {start|stop|restart}"
    exit 1
esac

exit 0
