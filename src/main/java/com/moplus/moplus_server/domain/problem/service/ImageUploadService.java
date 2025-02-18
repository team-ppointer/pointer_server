package com.moplus.moplus_server.domain.problem.service;

import com.amazonaws.HttpMethod;
import com.moplus.moplus_server.domain.problem.domain.problem.ProblemCustomId;
import com.moplus.moplus_server.domain.problem.domain.problem.ProblemImageType;
import com.moplus.moplus_server.domain.problem.repository.ProblemRepository;
import com.moplus.moplus_server.global.utils.s3.S3Util;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ImageUploadService {

    private static final String PROBLEM_IMAGE_PREFIX = "problems/";
    private final S3Util s3Util;
    private final ProblemRepository problemRepository;

    public String generateProblemImagePresignedUrl(String problemId, ProblemImageType imageType) {
        problemRepository.existsByProblemAdminIdElseThrow(new ProblemCustomId(problemId));
        String fileName = generateProblemImageFileName(problemId, imageType);
        return s3Util.getS3PresignedUrl(fileName, HttpMethod.PUT);
    }

    private String generateProblemImageFileName(String problemId, ProblemImageType imageType) {
        String uuid = UUID.randomUUID().toString();
        return String.format("%s%s/%s/%s.jpg",
                PROBLEM_IMAGE_PREFIX,
                problemId,
                imageType.getType(),
                uuid
        );
    }

    public String getImageUrl(String fileName) {
        return s3Util.getS3ObjectUrl(fileName);
    }
} 