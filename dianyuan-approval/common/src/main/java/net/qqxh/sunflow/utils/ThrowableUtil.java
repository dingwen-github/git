package net.qqxh.sunflow.utils;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * Copyright (C), 2019-2030, sunflow开发团队
 * 异常工具
 *
 * @author jwy
 * @fileName: ThrowableUtil
 * @date: 23/05/2019 16:20
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class ThrowableUtil {

    /**
     * 获取堆栈信息
     *
     * @param throwable
     * @return
     * @throws
     * @Author jwy
     * @date 23/05/2019 16:21
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public static String getStackTrace(Throwable throwable) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        try {
            throwable.printStackTrace(pw);
            return sw.toString();
        } finally {
            pw.close();
        }
    }
}
