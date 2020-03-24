package io.mjmoore.issue.controller;

import io.mjmoore.issue.dto.BugDto;
import io.mjmoore.issue.model.Bug;
import io.mjmoore.issue.service.BugService;
import io.mjmoore.issue.validation.RestError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.NoSuchElementException;

@RepositoryRestController
public class BugController {

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
}
