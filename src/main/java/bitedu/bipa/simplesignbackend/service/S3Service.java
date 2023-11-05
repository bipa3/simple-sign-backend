package bitedu.bipa.simplesignbackend.service;

import bitedu.bipa.simplesignbackend.validation.CustomErrorCode;
import bitedu.bipa.simplesignbackend.validation.RestApiException;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.util.IOUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class S3Service {
    private final AmazonS3Client amazonS3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    public String upload(MultipartFile file, String uniqueFileName) throws IOException {
        File convertedFile = convert(file);
        return uploadToS3(convertedFile, uniqueFileName);
    }

    public String makeUniqueFileName(MultipartFile file, String folderName) {
        return folderName + "/" + UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
    }


    private File convert(MultipartFile file) throws IOException {
        File convertedFile = new File(file.getOriginalFilename());
        try (FileOutputStream fos = new FileOutputStream(convertedFile)) {
            fos.write(file.getBytes());
        }
        return convertedFile;
    }

    private String uploadToS3(File file, String fileName) {
        amazonS3Client.putObject(new PutObjectRequest(bucket, fileName, file));
        file.delete();
        return amazonS3Client.getUrl(bucket, fileName).toString();
    }

    public byte[] downloadFile(String resourcePath) {
        validateFileExistsAtUrl(resourcePath);
        S3Object s3Object = amazonS3Client.getObject(bucket, resourcePath);
        S3ObjectInputStream inputStream = s3Object.getObjectContent();
        try {
            return IOUtils.toByteArray(inputStream);
        } catch (IOException e) {
            throw new RestApiException(CustomErrorCode.FILE_DOWNLOAD_FAIL);
        }
    }

    private void validateFileExistsAtUrl(String resourcePath) {
        if (!amazonS3Client.doesObjectExist(bucket, resourcePath)) {
            throw new RestApiException(CustomErrorCode.FILE_NOT_FOUND);
        }
    }

    public boolean isValidation(MultipartFile file){
        String contentType = file.getContentType();
        if(contentType == null) return false;
        if(contentType.contains("image/jpeg")){
            return true;
        } else if (contentType.contains("image/png")) {
            return true;
        } else if (contentType.contains("image/gif")) {
            return true;
        }
        return false;
    }
}
