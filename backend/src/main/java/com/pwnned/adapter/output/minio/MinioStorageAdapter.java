package com.pwnned.adapter.output.minio;

import com.pwnned.port.output.StorageRepositoryPort;
import io.minio.*;
import io.minio.http.Method;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.io.InputStream;
import java.util.concurrent.TimeUnit;

@Component
public class MinioStorageAdapter implements StorageRepositoryPort {
    private final MinioClient minioClient;
    private final String bucket;
    private final String accessKey;
    private final String secretKey;
    private final String externalEndpoint;

    public MinioStorageAdapter(
            MinioClient minioClient,
            @Value("${minio.bucket}") String bucket,
            @Value("${minio.external-endpoint}") String externalEndpoint,
            @Value("${minio.accessKey}") String accessKey,
            @Value("${minio.secretKey}") String secretKey) {
        this.minioClient = minioClient;
        this.bucket = bucket;
        this.externalEndpoint = externalEndpoint;
        this.accessKey = accessKey;
        this.secretKey = secretKey;
    }

    @Override
    public String uploadFile(String fileName, InputStream inputStream, String contentType) {
        try {
            ensureBucketExists();
            minioClient.putObject(PutObjectArgs.builder()
                    .bucket(bucket).object(fileName).stream(inputStream, -1, 10485760)
                    .contentType(contentType)
                    .build());
            return fileName;
        } catch (Exception e) {
            throw new RuntimeException("Error uploading file to MinIO.", e);
        }
    }

    @Override
    public void deleteFile(String fileName) {
        try {
            minioClient.removeObject(
                    RemoveObjectArgs.builder()
                            .bucket(bucket)
                            .object(fileName)
                            .build()
            );
        } catch (Exception e) {
            throw new RuntimeException("Error deleting file from MinIO: " + fileName, e);
        }
    }

    @Override
    public String generatePresignedUrl(String fileName) {
        return "http://localhost:9000/" + bucket + "/" + fileName;
    }

    private void ensureBucketExists() throws Exception {
        boolean found = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucket).build());
        if (!found) {
            minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucket).build());
            String config = "{\n" +
                    "    \"Version\": \"2012-10-17\",\n" +
                    "    \"Statement\": [\n" +
                    "        {\n" +
                    "            \"Action\": [\"s3:GetObject\"],\n" +
                    "            \"Effect\": \"Allow\",\n" +
                    "            \"Principal\": {\"AWS\": [\"*\"]},\n" +
                    "            \"Resource\": [\"arn:aws:s3:::" + bucket + "/*\"]\n" +
                    "        }\n" +
                    "    ]\n" +
                    "}";

            minioClient.setBucketPolicy(
                    SetBucketPolicyArgs.builder().bucket(bucket).config(config).build()
            );
        }
    }
}