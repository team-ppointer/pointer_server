package com.moplus.moplus_server.global.utils.s3;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.moplus.moplus_server.global.error.exception.ErrorCode;
import com.moplus.moplus_server.global.error.exception.NotFoundException;
import java.io.File;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

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
