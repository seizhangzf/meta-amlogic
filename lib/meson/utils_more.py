class ImageQAFailed(bb.build.FuncFailed):
    def __init__(self, description, name=None, logfile=None):
        self.description = description
        self.name = name
        self.logfile=logfile

    def __str__(self):
        msg = 'Function failed: %s' % self.name
        if self.description:
            msg = msg + ' (%s)' % self.description

        return msg
