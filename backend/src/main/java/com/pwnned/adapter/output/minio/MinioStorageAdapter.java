package com.pwnned.adapter.output.minio;

import com.pwnned.port.output.StorageRepositoryPort;
import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.io.InputStream;

@Component
public class MinioStorageAdapter implements StorageRepositoryPort {
    private final MinioClient minioClient;
    private final String bucket;

    public MinioStorageAdapter(MinioClient minioClient, @Value("${minio.bucket}") String bucket) {
        this.minioClient = minioClient;
        this.bucket = bucket;
    }

    @Override
    public String uploadFile(String fileName, InputStream inputStream, String contentType) {
        try {
            boolean found = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucket).build());
            if (!found) {
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucket).build());
            }
            minioClient.putObject(PutObjectArgs.builder()
                    .bucket(bucket).object(fileName).stream(inputStream, -1, 10485760)
                    .contentType(contentType)
                    .build());
            return fileName;
        } catch (Exception e) {
            throw new RuntimeException("Error uploading file to MinIO.", e);
        }
    }
}