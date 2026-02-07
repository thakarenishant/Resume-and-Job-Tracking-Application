package org.web.resumeandjobtracking.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.web.resumeandjobtracking.DTO.MatchRequest;
import org.web.resumeandjobtracking.DTO.MatchResponse;
import org.web.resumeandjobtracking.Service.ResumeMatchService;
import org.web.resumeandjobtracking.Service.ResumeService;

@RestController
public class ResumeMatchController {
    @Autowired
    private ResumeMatchService resumeMatchService;


    @PostMapping("/score")
    public MatchResponse getMatchScore(@RequestBody MatchRequest request) {
        double score = resumeMatchService.getMatchScore(
                request.getResumeText(),
                request.getJobDescription()
        );
        return new MatchResponse(score);
    }
}
