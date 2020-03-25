package io.mjmoore.controller;

import io.mjmoore.model.Story;
import io.mjmoore.service.SprintService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SprintController {

    @Autowired
    public SprintService sprintService;

    @GetMapping("/sprint")
    public @ResponseBody List<Story> getSprint() {
        return sprintService.getSprint();
    }
}
