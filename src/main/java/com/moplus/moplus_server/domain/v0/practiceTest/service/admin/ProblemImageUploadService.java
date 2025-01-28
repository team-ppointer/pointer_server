package com.moplus.moplus_server.domain.v0.practiceTest.service.admin;

import com.moplus.moplus_server.domain.v0.practiceTest.domain.FileExtension;
import com.moplus.moplus_server.domain.v0.practiceTest.domain.PracticeTest;
import com.moplus.moplus_server.domain.v0.practiceTest.domain.ProblemForTest;
import com.moplus.moplus_server.domain.v0.practiceTest.domain.ProblemImageForTest;
import com.moplus.moplus_server.domain.v0.practiceTest.dto.admin.request.ProblemImageRequest;
import com.moplus.moplus_server.domain.v0.practiceTest.repository.PracticeTestRepository;
import com.moplus.moplus_server.domain.v0.practiceTest.repository.ProblemImageRepository;
import com.moplus.moplus_server.domain.v0.practiceTest.repository.ProblemRepository;
import com.moplus.moplus_server.global.error.exception.ErrorCode;
import com.moplus.moplus_server.global.error.exception.NotFoundException;
import com.moplus.moplus_server.global.utils.s3.S3Util;
import java.io.File;
import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class ProblemImageUploadService {

    private final S3Util s3Util;
    private final PracticeTestRepository practiceTestRepository;
    private final ProblemRepository problemRepository;
    private final ProblemImageRepository problemImageRepository;

    @Transactional(readOnly = true)
    public void setProblemImagesByPracticeTestId(Long practiceTestId, Model model) {
        List<ProblemImageRequest> imageRequests = problemRepository.findAllByPracticeTestId(practiceTestId).stream()
                .map(ProblemImageRequest::of)
                .toList();
        model.addAttribute("problemImageRequests", imageRequests);
    }

    @Transactional
    public void uploadImage(Long practiceId, Long problemId, MultipartFile image) {
        PracticeTest practiceTest = practiceTestRepository.findById(practiceId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.PRACTICE_TEST_NOT_FOUND));
        ProblemForTest problemForTest = problemRepository.findById(problemId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.PROBLEM_NOT_FOUND));
        String fileName = uploadFile(image, problemId, practiceTest.getName());
        String s3ObjectUrl = s3Util.getS3ObjectUrl(fileName);
        ProblemImageForTest problemImageForTest = ProblemImageForTest.builder()
                .fileName(fileName)
                .problemId(problemId)
                .imageUrl(s3ObjectUrl)
                .build();
        ProblemImageForTest saved = problemImageRepository.save(problemImageForTest);
        problemForTest.addImage(saved);
        problemRepository.save(problemForTest);
    }

    public String uploadFile(MultipartFile file, Long problemId, String practiceTestName) {
        String fileName = "";
        try {
            File fileObj = convertMultiPartFileToFile(file);
            String originalFilename = file.getOriginalFilename();
            String imageFileExtension = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);

            fileName = createImageFileName(
                    practiceTestName,
                    problemId,
                    FileExtension.from(imageFileExtension));
            // 파일을 Amazon S3에 업로드합니다.
            s3Util.putObject(fileObj, fileName);
            fileObj.delete(); // 임시 파일 정리
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fileName;
    }

    private File convertMultiPartFileToFile(MultipartFile file) throws IOException {
        // 프로젝트의 루트 디렉토리 경로를 가져옵니다.
        String rootPath = System.getProperty("user.dir");
        // 업로드된 파일의 원본 이름을 가져옵니다.
        String originalFileName = file.getOriginalFilename();
        // 파일을 저장할 전체 경로를 구성합니다.
        String fullPath = rootPath + File.separator + (originalFileName != null ? originalFileName : "defaultFileName");

        File convFile = new File(fullPath);
        // MultipartFile의 내용을 새로 생성된 File 객체로 복사합니다.
        file.transferTo(convFile);
        return convFile;
    }

    private String createImageFileName(
            String practiceTestName,
            Long problemId,
            FileExtension fileExtension
    ) {
        return practiceTestName
                + "/"
                + problemId
                + "."
                + fileExtension.getUploadExtension();
    }
}
