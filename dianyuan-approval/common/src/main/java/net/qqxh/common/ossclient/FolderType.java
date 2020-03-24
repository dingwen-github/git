package net.qqxh.common.ossclient;

public enum FolderType {
    yyyyMMdd("yyyyMMdd"),yyyyMM("yyyyMM"),yyyyww("yyyyww");
    private String fmt;

    public String getFmt() {
        return fmt;
    }

    FolderType(String fmt) {
        this.fmt = fmt;
    }
}
