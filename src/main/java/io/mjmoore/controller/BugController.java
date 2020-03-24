package io.mjmoore.controller;

import io.mjmoore.dto.BugDto;
import io.mjmoore.model.Bug;
import io.mjmoore.service.BugService;
import io.mjmoore.service.IssueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@RepositoryRestController
public class BugController {

    @Autowired
    public IssueService issueService;

    @Autowired
    public BugService bugService;

    @PostMapping("/bugs")
    public @ResponseBody Bug createBug(@RequestBody final BugDto bugDto) {
        return bugService.createBug(bugDto);
    }

    @PostMapping("/bugs/{bugId}/verify")
    public @ResponseBody Bug verifyBug(@PathVariable final Long bugId) {
       return bugService.verifyBug(bugId);
    }

    @PostMapping("/bugs/{bugId}/resolve")
    public @ResponseBody Bug resolveBug(@PathVariable final Long bugId) {
        return bugService.resolveBug(bugId);
    }

    @PatchMapping("/bugs/{bugId}")
    public @ResponseBody Bug updateBug(@RequestBody final BugDto bugDto,
                                       @PathVariable final Long bugId) {
        return bugService.updateBug(bugDto, bugId);
    }

    @PostMapping("/bugs/{bugId}/assign/{userId}")
    public @ResponseBody Bug assignBug(@PathVariable final Long bugId,
                                       @PathVariable final Long userId) {
        issueService.assignIssue(bugId, userId);
        return bugService.getBug(bugId);
    }
}
