#!/bin/sh

LOCAL_DIR=$(pwd)
if [ -z $BUILD_DIR ]; then
	BUILD_DIR="build"
fi

DEFCONFIG_ARRAY=("mesongxl_p230"
				 "mesongxl_p230_32b"
				 "mesongxl_p230_wayland_fbdev"
				 "mesongxl_p230_wayland_fbdev_32b"
				 "mesongxl_p230_wayland_fbdev_playready"
				 "mesongxl_p230_wayland_fbdev_playready_32b"
				 "mesongxl_p230_wayland_drm"
				 "mesongxl_p230_wayland_drm_32b"
				 "mesongxl_p230_wayland_drm_playready"
				 "mesongxl_p230_wayland_drm_playready_32b"
				 "mesongxl_p212"
				 "mesongxl_p212_32b"
				 "mesongxl_p212_wayland_fbdev"
				 "mesongxl_p212_wayland_fbdev_32b"
				 "mesongxl_p212_wayland_fbdev_playready"
				 "mesongxl_p212_wayland_fbdev_playready_32b"
				 "mesongxl_p212_wayland_drm"
				 "mesongxl_p212_wayland_drm_32b"
				 "mesongxl_p212_wayland_drm_playready"
				 "mesongxl_p212_wayland_drm_playready_32b"
				 "mesontxlx_r321_wayland_fbdev_playready"
				 "mesontxlx_r321_wayland_fbdev_playready_32b"
				 "mesontxlx_r321_wayland_drm_playready"
				 "mesontxlx_r321_wayland_drm_playready_32b"
				 "mesong12a_u200_wayland_drm"
				 "mesong12a_u200_wayland_drm_32b"
				 "mesong12a_u212_wayland_drm"
				 "mesong12a_u212_wayland_drm_32b"
				 "mesong12a_u212_wayland_drm_playready"
				 "mesong12a_u212_wayland_drm_playready_32b"
				 "mesong12b_w400_wayland_drm"
				 "mesong12b_w400_wayland_drm_32b"
				 "mesong12b_w400_wayland_drm_playready"
				 "mesong12b_w400_wayland_drm_playready_32b"
				 )

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
	DEFAULT_VALUE="mesongxl_p230_32b"
	
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
		MACHINE=$TARGET_MACHINE source meta-meson/setup-environment $BUILD_DIR
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
