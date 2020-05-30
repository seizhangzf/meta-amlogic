def get_patch_list(patchpath, patchdir=None):
    patchlist = []
    if os.path.exists(patchpath):
        for patchname in sorted(os.listdir(patchpath)):
            if patchname.endswith('.patch'):
                if patchdir is None:
                    patchlist.append(" file://" + patchname )
                else:
                    patchlist.append(" file://" + patchname + ";patchdir=" + patchdir )
    return ''.join(patchlist)

def get_patch_list_with_path(patchpath, patchdir=None):
    patchlist = []
    if os.path.exists(patchpath):
        for patchname in sorted(os.listdir(patchpath)):
            if patchname.endswith('.patch'):
                if patchdir is None:
                    patchlist.append(" file://" + patchpath + "/" + patchname )
                else:
                    patchlist.append(" file://" + patchpath + "/" + patchname + ";patchdir=" + patchdir )
    return ''.join(patchlist)

