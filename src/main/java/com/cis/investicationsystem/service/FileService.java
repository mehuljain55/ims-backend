package com.cis.investicationsystem.service;

import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;


@Service
public class FileService {

    private static final int CHUNK_SIZE = 1048576; // 1MB chunks


    public ResponseEntity<Resource> getChunkedVideo( String videoURL, String rangeHeader) throws IOException {


        if(videoURL==null)
        {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        File videoFile = new File(videoURL);

        if (!videoFile.exists()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        long fileSize = videoFile.length();
        long rangeStart = 0;
        long rangeEnd = Math.min(CHUNK_SIZE - 1, fileSize - 1);

        if (rangeHeader != null && rangeHeader.startsWith("bytes=")) {
            String[] ranges = rangeHeader.substring(6).split("-");
            rangeStart = Long.parseLong(ranges[0]);
            if (ranges.length > 1) {
                rangeEnd = Long.parseLong(ranges[1]);
            } else {
                rangeEnd = Math.min(rangeStart + CHUNK_SIZE, fileSize - 1);
            }
        }

        if (rangeEnd >= fileSize) {
            rangeEnd = fileSize - 1;
        }

        long contentLength = rangeEnd - rangeStart + 1;
        InputStream inputStream = new FileInputStream(videoFile);
        inputStream.skip(rangeStart);

        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.ACCEPT_RANGES, "bytes");
        headers.set(HttpHeaders.CONTENT_RANGE, "bytes " + rangeStart + "-" + rangeEnd + "/" + fileSize);
        headers.set(HttpHeaders.CONTENT_LENGTH, String.valueOf(contentLength));
        headers.setContentType(getMediaTypeForExtension(getFileExtension(videoFile)));

        return ResponseEntity.status(HttpStatus.PARTIAL_CONTENT)
                .headers(headers)
                .body(new InputStreamResource(new LimitedInputStream(inputStream, contentLength)));
    }


    public byte[] getEvidenceFile(String filePath) throws IOException {
        Path path = Paths.get(filePath);
        return Files.readAllBytes(path);
    }


    private String getFileExtension(File file) {
        String filename = file.getName();
        int dotIndex = filename.lastIndexOf(".");
        return (dotIndex == -1) ? "" : filename.substring(dotIndex + 1).toLowerCase();
    }

    private MediaType getMediaTypeForExtension(String extension) {
        switch (extension) {
            case "mp4":
                return MediaType.valueOf("video/mp4");
            case "mkv":
                return MediaType.valueOf("video/x-matroska");
            case "avi":
                return MediaType.valueOf("video/x-msvideo");
            case "mov":
                return MediaType.valueOf("video/quicktime");
            case "webm":
                return MediaType.valueOf("video/webm");
            default:
                return MediaType.APPLICATION_OCTET_STREAM; // Default binary stream
        }
    }

    // Helper class to limit InputStream
    static class LimitedInputStream extends InputStream {
        private final InputStream inputStream;
        private long remainingBytes;

        public LimitedInputStream(InputStream inputStream, long maxBytes) {
            this.inputStream = inputStream;
            this.remainingBytes = maxBytes;
        }

        @Override
        public int read() throws IOException {
            if (remainingBytes <= 0) return -1;
            int data = inputStream.read();
            if (data != -1) remainingBytes--;
            return data;
        }

        @Override
        public int read(byte[] b, int off, int len) throws IOException {
            if (remainingBytes <= 0) return -1;
            int bytesRead = inputStream.read(b, off, (int) Math.min(len, remainingBytes));
            if (bytesRead != -1) remainingBytes -= bytesRead;
            return bytesRead;
        }

        @Override
        public void close() throws IOException {
            inputStream.close();
        }
    }
}
