def get_patch_list(patchpath):
    patchlist = []
    if os.path.exists(patchpath):
        for patchname in sorted(os.listdir(patchpath)):
            if patchname.endswith('.patch'):
                patchlist.append(" file://" + patchname )
    return ''.join(patchlist)

def get_patch_list_with_patchdir(patchpath,patchdir ):
    patchlist = []
    if os.path.exists(patchpath):
        for patchname in sorted(os.listdir(patchpath)):
            if patchname.endswith('.patch'):
                patchlist.append(" file://" + patchname + ";patchdir=" + patchdir )
    return ''.join(patchlist)



