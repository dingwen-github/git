package net.qqxh.common.ossclient;

import io.minio.MinioClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.io.InputStream;

/**
 * oss客户端
 */
public class MagicOssClient {
    private final static Logger LOG = LoggerFactory.getLogger(MagicOssClient.class);

    @Autowired
    MinioClient minioClient;

    /**
     * @param bucketName
     * @param biz
     * @param type
     * @param stream
     * @return 文件下载地址
     */
    public String upload(String bucketName, String biz, FolderType type, InputStream stream, String fix) {
        try {
            boolean found = minioClient.bucketExists(bucketName);
            if (found) {
                LOG.info("bucket:" + bucketName + " already exists");
            } else {
                // 创建名为'my-bucketname'的存储桶。
                minioClient.makeBucket(bucketName);
                LOG.info("bucket:" + bucketName + " is created successfully");
            }
            /*对象名称和文件路径名称*/
            String objectName = FileNameBuilder.build(biz, type, fix);
            minioClient.putObject(bucketName, objectName, stream, stream.available(), "application/octet-stream");
            return minioClient.getObjectUrl(bucketName, objectName);
        } catch (Exception e) {
            e.printStackTrace();
            LOG.error("upload fail{}", e);
        } finally {
            if (stream != null) {
                try {
                    stream.close();
                } catch (IOException e) {
                    LOG.error("close stream fail{}", e);
                }
            }
        }
        return null;
    }

}
