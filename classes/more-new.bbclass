KERNEL_OUTPUT_DIR ?= "arch/${ARCH}/boot"
IMAGE_NAME_SUFFIX ??= ".rootfs"

inherit rootfs-postcommands
############ changes from kernel.bbclass
python __anonymous () {

    image = d.getVar('INITRAMFS_IMAGE', True)
    if image:
        d.appendVarFlag('do_bundle_initramfs', 'depends', ' ${INITRAMFS_IMAGE}:do_image_complete')
}

############ from image_types.bbclass
IMAGE_CMD_tar = "tar -cvf ${IMGDEPLOYDIR}/${IMAGE_NAME}.rootfs.tar -C ${IMAGE_ROOTFS} ."

oe_mkext234fs_new () {
        fstype=$1
        extra_imagecmd=""

        if [ $# -gt 1 ]; then
                shift
                extra_imagecmd=$@
        fi

        # If generating an empty image the size of the sparse block should be large
        # enough to allocate an ext4 filesystem using 4096 bytes per inode, this is
        # about 60K, so dd needs a minimum count of 60, with bs=1024 (bytes per IO)
        eval local COUNT=\"0\"
        eval local MIN_COUNT=\"60\"
        if [ $ROOTFS_SIZE -lt $MIN_COUNT ]; then
                eval COUNT=\"$MIN_COUNT\"
        fi
        # Create a sparse image block
        dd if=/dev/zero of=${IMGDEPLOYDIR}/${IMAGE_NAME}${IMAGE_NAME_SUFFIX}.$fstype seek=$ROOTFS_SIZE count=$COUNT bs=1024
        mkfs.$fstype -F $extra_imagecmd ${IMGDEPLOYDIR}/${IMAGE_NAME}${IMAGE_NAME_SUFFIX}.$fstype -d ${IMAGE_ROOTFS}
}

IMAGE_CMD_ext2 = "oe_mkext234fs_new ext2 ${EXTRA_IMAGECMD}"
IMAGE_CMD_ext3 = "oe_mkext234fs_new ext3 ${EXTRA_IMAGECMD}"
IMAGE_CMD_ext4 = "oe_mkext234fs_new ext4 ${EXTRA_IMAGECMD}"

do_image_cpio[cleandirs] += "${WORKDIR}/cpio_append"
IMAGE_CMD_cpio () {
        (cd ${IMAGE_ROOTFS} && find . | cpio -o -H newc >${IMGDEPLOYDIR}/${IMAGE_NAME}${IMAGE_NAME_SUFFIX}.cpio)
        # We only need the /init symlink if we're building the real
        # image. The -dbg image doesn't need it! By being clever
        # about this we also avoid 'touch' below failing, as it
        # might be trying to touch /sbin/init on the host since both
        # the normal and the -dbg image share the same WORKDIR
        if [ "${IMAGE_BUILDING_DEBUGFS}" != "true" ]; then
                if [ ! -L ${IMAGE_ROOTFS}/init ] && [ ! -e ${IMAGE_ROOTFS}/init ]; then
                        if [ -L ${IMAGE_ROOTFS}/sbin/init ] || [ -e ${IMAGE_ROOTFS}/sbin/init ]; then
                                ln -sf /sbin/init ${WORKDIR}/cpio_append/init
                        else
                                touch ${WORKDIR}/cpio_append/init
                        fi
                        (cd  ${WORKDIR}/cpio_append && echo ./init | cpio -oA -H newc -F ${IMGDEPLOYDIR}/${IMAGE_NAME}${IMAGE_NAME_SUFFIX}.cpio)
                fi
        fi
}

############ from toaster.bbclass
# get list of artifacts from sstate manifest
python toaster_artifacts() {
    if e.taskname in ["do_deploy", "do_image_complete", "do_populate_sdk", "do_populate_sdk_ext"]:
        d2 = d.createCopy()
        d2.setVar('FILE', e.taskfile)
        d2.setVar('SSTATE_MANMACH', d2.expand("${MACHINE}"))
        manifest = oe.sstatesig.sstate_get_manifest_filename(e.taskname[3:], d2)[0]
        if os.access(manifest, os.R_OK):
            with open(manifest) as fmanifest:
                artifacts = [fname.strip() for fname in fmanifest]
                data = {"task": e.taskid, "artifacts": artifacts}
                bb.event.fire(bb.event.MetadataEvent("TaskArtifacts", data), d2)
}

addhandler toaster_artifacts
toaster_artifacts[eventmask] = "bb.runqueue.runQueueTaskSkipped bb.runqueue.runQueueTaskCompleted"

############# changes from license.bbclass
python write_deploy_manifest() {
    license_deployed_manifest(d)
}

def license_deployed_manifest(d):
    """
    Write the license manifest for the deployed recipes.
    The deployed recipes usually includes the bootloader
    and extra files to boot the target.
    """

    dep_dic = {}
    man_dic = {}
    lic_dir = d.getVar("LICENSE_DIRECTORY", True)

    dep_dic = get_deployed_dependencies(d)
    for dep in dep_dic.keys():
        man_dic[dep] = {}
        # It is necessary to mark this will be used for image manifest
        man_dic[dep]["IMAGE_MANIFEST"] = True
        man_dic[dep]["PN"] = dep
        man_dic[dep]["FILES"] = \
            " ".join(get_deployed_files(dep_dic[dep]))
        with open(os.path.join(lic_dir, dep, "recipeinfo"), "r") as f:
            for line in f.readlines():
                key,val = line.split(": ", 1)
                man_dic[dep][key] = val[:-1]

    lic_manifest_dir = os.path.join(d.getVar('LICENSE_DIRECTORY', True),
                                    d.getVar('IMAGE_NAME', True))
    bb.utils.mkdirhier(lic_manifest_dir)
    image_license_manifest = os.path.join(lic_manifest_dir, 'image_license.manifest')
    write_license_files(d, image_license_manifest, man_dic)

#IMAGE_POSTPROCESS_COMMAND_prepend = "write_deploy_manifest; "
#do_image[recrdeptask] += "do_populate_lic"

def get_deployed_dependencies(d):
    """
    Get all the deployed dependencies of an image
    """

    deploy = {}
    # Get all the dependencies for the current task (rootfs).
    # Also get EXTRA_IMAGEDEPENDS because the bootloader is
    # usually in this var and not listed in rootfs.
    # At last, get the dependencies from boot classes because
    # it might contain the bootloader.
    taskdata = d.getVar("BB_TASKDEPDATA", False)
    depends = list(set([dep[0] for dep
                    in list(taskdata.values())
                    if not dep[0].endswith("-native")]))
    extra_depends = d.getVar("EXTRA_IMAGEDEPENDS", True)
    boot_depends = get_boot_dependencies(d)
    depends.extend(extra_depends.split())
    depends.extend(boot_depends)
    depends = list(set(depends))

    # To verify what was deployed it checks the rootfs dependencies against
    # the SSTATE_MANIFESTS for "deploy" task.
    # The manifest file name contains the arch. Because we are not running
    # in the recipe context it is necessary to check every arch used.
    sstate_manifest_dir = d.getVar("SSTATE_MANIFESTS", True)
    sstate_archs = d.getVar("SSTATE_ARCHS", True)
    extra_archs = d.getVar("PACKAGE_EXTRA_ARCHS", True)
    archs = list(set(("%s %s" % (sstate_archs, extra_archs)).split()))
    for dep in depends:
        # Some recipes have an arch on their own, so we try that first.
        special_arch = d.getVar("PACKAGE_ARCH_pn-%s" % dep, True)
        if special_arch:
            sstate_manifest_file = os.path.join(sstate_manifest_dir,
                    "manifest-%s-%s.deploy" % (special_arch, dep))
            if os.path.exists(sstate_manifest_file):
                deploy[dep] = sstate_manifest_file
                continue

        for arch in archs:
            sstate_manifest_file = os.path.join(sstate_manifest_dir,
                    "manifest-%s-%s.deploy" % (arch, dep))
            if os.path.exists(sstate_manifest_file):
                deploy[dep] = sstate_manifest_file
                break

    return deploy
get_deployed_dependencies[vardepsexclude] = "BB_TASKDEPDATA"

def get_boot_dependencies(d):
    """
    Return the dependencies from boot tasks
    """

    depends = []
    boot_depends_string = ""
    taskdepdata = d.getVar("BB_TASKDEPDATA", False)
    # Only bootimg and bootdirectdisk include the depends flag
    boot_tasks = ["do_bootimg", "do_bootdirectdisk",]

    for task in boot_tasks:
        boot_depends_string = "%s %s" % (boot_depends_string,
                d.getVarFlag(task, "depends", True) or "")
    boot_depends = [dep.split(":")[0] for dep
                in boot_depends_string.split()
                if not dep.split(":")[0].endswith("-native")]
    for dep in boot_depends:
        info_file = os.path.join(d.getVar("LICENSE_DIRECTORY", True),
                dep, "recipeinfo")
        # If the recipe and dependency name is the same
        if os.path.exists(info_file):
            depends.append(dep)
        # We need to search for the provider of the dependency
        else:
            for taskdep in taskdepdata.values():
                # The fifth field contains what the task provides
                if dep in taskdep[4]:
                    info_file = os.path.join(
                            d.getVar("LICENSE_DIRECTORY", True),
                            taskdep[0], "recipeinfo")
                    if os.path.exists(info_file):
                        depends.append(taskdep[0])
                        break
    return depends
get_boot_dependencies[vardepsexclude] = "BB_TASKDEPDATA"

def get_deployed_files(man_file):
    """
    Get the files deployed from the sstate manifest
    """

    dep_files = []
    excluded_files = ["README_-_DO_NOT_DELETE_FILES_IN_THIS_DIRECTORY.txt"]
    with open(man_file, "r") as manifest:
        all_files = manifest.read()
    for f in all_files.splitlines():
        if ((not (os.path.islink(f) or os.path.isdir(f))) and
                not os.path.basename(f) in excluded_files):
            dep_files.append(os.path.basename(f))
    return dep_files

###########from populate_sdk_base.bbclass
def sdk_command_variables(d):
    return ['OPKG_PREPROCESS_COMMANDS','OPKG_POSTPROCESS_COMMANDS','POPULATE_SDK_POST_HOST_COMMAND','POPULATE_SDK_POST_TARGET_COMMAND','SDK_POSTPROCESS_COMMAND','RPM_PREPROCESS_COMMANDS',
            'RPM_POSTPROCESS_COMMANDS']


do_kernel_checkout_append() {
        cd ${WORKDIR}/git
        git checkout -b master
        cd -
}

