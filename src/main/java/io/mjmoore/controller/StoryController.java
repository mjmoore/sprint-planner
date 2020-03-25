package io.mjmoore.controller;

import io.mjmoore.dto.StoryDto;
import io.mjmoore.model.Story;
import io.mjmoore.service.IssueService;
import io.mjmoore.service.StoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;

@Validated
@RepositoryRestController
public class StoryController {

    @Autowired
    public StoryService storyService;

    @Autowired
    private IssueService issueService;

    @PostMapping("/stories")
    public @ResponseBody Story createStory(@Valid @RequestBody final StoryDto storyDto) {
        return storyService.createStory(storyDto);
    }

    @PostMapping("/stories/{storyId}/complete")
    public @ResponseBody Story completeStory(@PathVariable final Long storyId) {
        return storyService.completeStory(storyId);
    }

    @PostMapping("stories/{storyId}/estimate/{estimation}")
    public @ResponseBody Story estimateStory(@PathVariable final Long storyId,
                                             @PathVariable final Integer estimation) {
        return storyService.estimateStory(storyId, estimation);
    }

    @PatchMapping("/stories/{storyId}")
    public @ResponseBody Story updateStory(@RequestBody final StoryDto storyDto,
                                           @PathVariable final Long storyId) {
        return storyService.updateStory(storyDto, storyId);
    }

    @PostMapping("/stories/{storyId}/assign/{userId}")
    public @ResponseBody Story assignBug(@PathVariable final Long storyId,
                                         @PathVariable final Long userId) {
        issueService.assignIssue(storyId, userId);
        return storyService.getStory(storyId);
    }
}
