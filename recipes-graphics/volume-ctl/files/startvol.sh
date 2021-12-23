#!/bin/bash
export "XDG_RUNTIME_DIR=/run"
export "WAYLAND_DISPLAY=vol_overlay"
set -x # verbose


echo "Creating volume overlay surface" >&2
count=10
    while [ $count -gt 0 ]; do
        # Get Thunder Security Token
        t=`/usr/bin/WPEFrameworkSecurityUtility`; t=${t%\",*}; t=${t#*:\"}
        token="Authorization: Bearer $t"
        echo "createDisplay" >&2
        curl -H "$token" -d '{"jsonrpc":"2.0","id":"3","method": "org.rdk.RDKShell.1.createDisplay", "params": {"client":"vol_overlay", "displayName":"vol_overlay"}}' http://127.0.0.1:9998/jsonrpc
        response=$(curl -H "$token" -d ' {"jsonrpc":"2.0", "id":3, "method":"org.rdk.RDKShell.1.getClients", "params":{ }}' http://localhost:9998/jsonrpc)
        vol_created=`echo $response | grep vol_overlay`
        if [ -n "$vol_created" ]; then
            echo "Created vol surface." >&2
            break
        else
            echo "Failed to create vol surface; $count retries remaining." >&2
        fi
        sleep 1
        let count=$count-1
    done
    if [ $count -eq 0 ]; then
        echo "Failed to create vol surface; giving up." >&2
        exit 1
    fi


curl -H "$token" -d '{"jsonrpc":"2.0","id":"3","method": "org.rdk.RDKShell.1.setTopmost", "params": {"client":"vol_overlay", "topmost":true}}' http://127.0.0.1:9998/jsonrpc
