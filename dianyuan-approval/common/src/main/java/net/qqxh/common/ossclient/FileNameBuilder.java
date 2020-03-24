package net.qqxh.common.ossclient;

import org.joda.time.DateTime;

import java.util.UUID;

/**
 * 文件名称生成策略
 */
public class FileNameBuilder {

    public static String build(String biz, FolderType type, String fix) {
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        return biz + "_" + getFolderName(type) + "_" + uuid + "."+fix;
    }

    public static String getFolderName(FolderType type) {
        return DateTime.now().toString(type.getFmt());
    }

}
