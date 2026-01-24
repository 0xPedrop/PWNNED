package com.pwnned.port.output;

import java.io.InputStream;

public interface StorageRepositoryPort {
    String uploadFile(String fileName, InputStream inputStream, String contentType);
}