package org.web.resumeandjobtracking.Controller;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomePage {
    @GetMapping({"/","/home"})
    public String HomePage() {
        return "HomePage";
    }
}
