package net.qqxh.sunflow.server.oss;

import io.minio.MinioClient;
import io.minio.errors.InvalidEndpointException;
import io.minio.errors.InvalidPortException;
import net.qqxh.common.ossclient.MagicOssClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OssConfig {

    @Bean
    public MinioClient minioClient(@Value("${oss.server}") String server, @Value("${oss.access}") String access, @Value("${oss.secret}") String secret) throws InvalidPortException, InvalidEndpointException {
        return new MinioClient(server, access, secret);
    }

    @Bean
    MagicOssClient magicOssClient() {
        return new MagicOssClient();
    }
}
