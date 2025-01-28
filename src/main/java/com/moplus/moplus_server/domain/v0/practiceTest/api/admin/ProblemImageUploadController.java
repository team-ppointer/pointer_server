package com.moplus.moplus_server.domain.v0.practiceTest.api.admin;

import com.moplus.moplus_server.domain.v0.practiceTest.service.admin.ProblemImageUploadService;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Hidden
@Controller
@RequestMapping("/admin/practiceTests")
@RequiredArgsConstructor
public class ProblemImageUploadController {

    private final ProblemImageUploadService problemImageUploadService;

    @GetMapping("/imageUploadPage/{practiceTestId}")
    @Operation(summary = "문제 이미지 업로드 페이지 HTML 요청")
    public String showImageUploadPage(@PathVariable("practiceTestId") Long practiceTestId, Model model) {
        problemImageUploadService.setProblemImagesByPracticeTestId(practiceTestId, model);
        model.addAttribute("practiceTestId", practiceTestId);

        return "imageUploadPage";
    }

    @PostMapping("/uploadImage/{problemId}")
    @Operation(summary = "문제 이미지 업로드 요청")
    public String uploadImage(@RequestParam("practiceTestId") Long practiceTestId,
                              @PathVariable("problemId") Long problemId, @RequestParam("image") MultipartFile image) {
        // 이미지 업로드 처리
        problemImageUploadService.uploadImage(practiceTestId, problemId, image);

        return "redirect:/admin/practiceTests/imageUploadPage/" + practiceTestId;
    }
}
