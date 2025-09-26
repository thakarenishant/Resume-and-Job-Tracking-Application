package org.web.resumeandjobtracking.Controller;

import org.web.resumeandjobtracking.Model.Resume;
import org.web.resumeandjobtracking.Service.ResumeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
//@RequestMapping("/resume")
public class ResumeController {
   @Autowired
    private final ResumeService resumeService;

    @Autowired
    public ResumeController(ResumeService resumeService) {
        this.resumeService = resumeService;
    }

    @PostMapping("/resume")
    public ResponseEntity<?> uploadResume(@RequestParam("file") MultipartFile file,
                                          @RequestParam("userId") int userId) {
        try {
            Resume resume = resumeService.uploadFile(file, userId);
            return ResponseEntity.ok().body(
                    "Resume uploaded successfully with ID: " + resume.getId()
            );
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to upload resume: " + e.getMessage());
        }
    }
}
