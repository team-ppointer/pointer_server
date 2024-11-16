package com.moplus.moplus_server.global.utils.s3;

import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.moplus.moplus_server.domain.practiceTest.domain.FileExtension;
import com.moplus.moplus_server.global.error.exception.ErrorCode;
import com.moplus.moplus_server.global.error.exception.NotFoundException;
import com.moplus.moplus_server.global.utils.UUIDGenerator;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
@RequiredArgsConstructor
public class S3Util {

    private final AmazonS3 amazonS3;

    @Value("${cloud.aws.s3.bucket}")
    private String bucketName;

    public void putObject(File file, String fileName) {
        amazonS3.putObject(new PutObjectRequest(bucketName, fileName, file));
    }

    public String getS3ObjectUrl(String fileName) {
        if (!amazonS3.doesObjectExist(bucketName, fileName)) {
            throw new NotFoundException(ErrorCode.IMAGE_FILE_NOT_FOUND_IN_S3);
        }
        return amazonS3.getUrl(bucketName, fileName).toString();
    }


}
