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

    public MinioStorageAdapter(MinioClient minioClient, @Value("${minio.bucket}") String bucket) {
        this.minioClient = minioClient;
        this.bucket = bucket;
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

    public String generatePresignedUrl(String fileName) {
        try {
            // Gera uma URL válida por 7 dias (tempo máximo padrão do MinIO/S3)
            return minioClient.getPresignedObjectUrl(
                    GetPresignedObjectUrlArgs.builder()
                            .method(Method.GET)
                            .bucket(bucket)
                            .object(fileName)
                            .expiry(7, TimeUnit.DAYS)
                            .build()
            );
        } catch (Exception e) {
            throw new RuntimeException("Error generating URL for file: " + fileName, e);
        }
    }

    // Helper method para evitar repetição de código
    private void ensureBucketExists() throws Exception {
        boolean found = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucket).build());
        if (!found) {
            minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucket).build());
        }
    }
}