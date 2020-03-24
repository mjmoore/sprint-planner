package io.mjmoore.service;

import io.mjmoore.dto.BugDto;
import io.mjmoore.model.Bug;
import io.mjmoore.model.Issue;
import io.mjmoore.repository.BugRepository;
import io.mjmoore.repository.IssueRepository;
import io.mjmoore.validation.BugStatusValidation;
import io.mjmoore.validation.RestError;
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

    public Bug getBug(final Long bugId) {
        return bugRepository.findById(bugId).orElseThrow();
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
