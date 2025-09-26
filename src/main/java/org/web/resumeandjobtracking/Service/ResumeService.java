package org.web.resumeandjobtracking.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.web.resumeandjobtracking.Model.Resume;
import org.web.resumeandjobtracking.Model.User;
import org.web.resumeandjobtracking.repo.ResumeRepository;
import org.web.resumeandjobtracking.repo.UserRepository;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class ResumeService {
    @Autowired
    private ResumeRepository resumeRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ResumeParserService resumeParserService;

    @Autowired
    public ResumeService(ResumeRepository resumeRepository,UserRepository userRepository, ResumeParserService resumeParserService) {
        this.resumeRepository = resumeRepository;
        this.userRepository = userRepository;
        this.resumeParserService = resumeParserService;
    }

    public Resume uploadFile(MultipartFile file,int id) throws Exception {
        User user= userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        String uploadDir = "C:\\uploads\\resumes";
        Path uploadPath = Paths.get(uploadDir);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        // Create unique filename
        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        Path filePath = uploadPath.resolve(fileName);

        // Save file to local storage
        file.transferTo(filePath.toFile());

        Resume resume = new Resume();
        resume.setFileName(file.getOriginalFilename());
        resume.setFileType(file.getContentType());
        resume.setFileSize(file.getSize());
        resume.setFilePath(filePath.toString());
        resume.setUser(user);
        resume.setUploadDate(LocalDateTime.now());

        Resume savedResume = resumeRepository.save(resume);

        // Parse resume immediately
        String resumeText = resumeParserService.parseResume(savedResume.getFilePath(), savedResume.getFileType());

        // For now, we can log it or store it if needed
        System.out.println("Parsed Resume Text:\n" + resumeText);
    return savedResume;
    }

}
