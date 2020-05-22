def get_patch_list(patchpath):
    patchlist = []
    for patchname in sorted(os.listdir(patchpath)):
        if patchname.endswith('.patch'):
            patchlist.append(" file://" + patchname )
    return ''.join(patchlist)


