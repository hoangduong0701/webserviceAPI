package com.dripg.drip_shop.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
@Service
public class FileUploadService {

    @Value("${FILE_ZONE}")
    private String storageZone;

    @Value("${FILE_UPLOAD_API_KEY}")
    private String fileUploadKey;

    @Value("${FILE_UPLOAD_HOST_URL}")
    private String fileHostName;

    private String key = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6InRidmNybGtwenNtYmRqZWt4c2ZyIiwicm9sZSI6InNlcnZpY2Vfcm9sZSIsImlhdCI6MTczMzY2Njg5OSwiZXhwIjoyMDQ5MjQyODk5fQ.9cbyQ3mAWhjIItF6N7BLs4sdJVLXPzxPNVjkw36RSAI";
    private String url = "https://tbvcrlkpzsmbdjekxsfr.supabase.co/storage/v1/object/products";
    public int uploadFile(MultipartFile file, String fileName) {

        try {
            //String urlString = fileHostName + "/" + storageZone + "/" + fileName;
            String urlString = url + "/" + fileName;
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("PUT");
            connection.setRequestProperty("Authorization", "Bearer " + key);
            connection.setRequestProperty("Content-Type", "image/jpeg");
            connection.setDoOutput(true);


            long fileSize = file.getSize();

            try (BufferedInputStream inputStream = new BufferedInputStream(file.getInputStream());
                 BufferedOutputStream outputStream = new BufferedOutputStream(connection.getOutputStream())) {

                byte[] buffer = new byte[8192];
                int bytesRead;
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                }
            }

            int responseCode = connection.getResponseCode();
            String responseMsg = connection.getResponseMessage();
            return responseCode;
        } catch (Exception e) {
            e.printStackTrace();
            return 500;
        }
    }
}
