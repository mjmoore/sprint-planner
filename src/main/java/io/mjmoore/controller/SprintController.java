package io.mjmoore.controller;

import io.mjmoore.model.Story;
import io.mjmoore.service.IssueService;
import io.mjmoore.service.StoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@RepositoryRestController
public class SprintController {

    @Autowired
    public StoryService storyService;


    @GetMapping("/sprint")
    public @ResponseBody List<Story> getSprint() {
        return storyService.getEstimatedStories();
    }
}
