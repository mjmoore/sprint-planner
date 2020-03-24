package io.mjmoore.issue.service;

import io.mjmoore.issue.dto.BugDto;
import io.mjmoore.issue.model.Bug;
import io.mjmoore.issue.model.Issue;
import io.mjmoore.issue.repository.BugRepository;
import io.mjmoore.issue.repository.IssueRepository;
import io.mjmoore.issue.validation.BugStatusValidation;
import io.mjmoore.issue.validation.RestError;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BugService {

    @Autowired
    private IssueRepository issueRepository;

    @Autowired
    private BugRepository bugRepository;

    @Autowired
    private BugStatusValidation statusValidator;

    private final ModelMapper mapper = new ModelMapper();

    public BugService() {
        this.mapper.getConfiguration().setSkipNullEnabled(true);
    }

    public Bug createBug(final BugDto bug) {
        final Issue issue = issueRepository.save(Issue.fromDto(bug));
        return bugRepository.save(Bug.fromDto(bug, issue));
    }

    public Bug verifyBug(final Long bugId) throws RestError.BadRequest {
        return updateStatus(bugId, Bug.Status.Verified);
    }

    public Bug resolveBug(final Long bugId) throws RestError.BadRequest {
        return updateStatus(bugId, Bug.Status.Resolved);
    }

    public Bug updateBug(final BugDto bugDto, final Long bugId) {
        final Bug bug = bugRepository.findById(bugId).orElseThrow();
        mapper.map(bugDto, bug);
        mapper.map(bugDto, bug.getIssue());
        return bugRepository.save(bug);
    }

    private Bug updateStatus(final Long bugId, final Bug.Status status)
            throws RestError.BadRequest {

        final Bug bug = bugRepository.findById(bugId).orElseThrow();

        if(status == bug.getStatus()) {
            return bug;
        }

        statusValidator.validate(bug, status);
        bug.setStatus(bug.getStatus().next());
        return bugRepository.save(bug);
    }
}
