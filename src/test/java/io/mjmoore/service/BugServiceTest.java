package io.mjmoore.service;

import io.mjmoore.dto.BugDto;
import io.mjmoore.model.Bug;
import io.mjmoore.model.Issue;
import io.mjmoore.repository.BugRepository;
import io.mjmoore.repository.IssueRepository;
import io.mjmoore.validation.BugStatusValidation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.AdditionalAnswers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BugServiceTest {

    private Issue issue;
    private Bug bug;

    @Mock
    private IssueRepository issueRepository;

    @Mock
    private BugRepository bugRepository;

    @Mock
    private BugStatusValidation validator;

    @InjectMocks
    private BugService bugService = new BugService();

    @BeforeEach
    public void setup() {
        issue = new Issue(1L, "Test bug", "Test description");
        bug = new Bug(2L, issue);
    }

    @Test
    public void createValidBug() {
        when(issueRepository.save(any(Issue.class)))
                .thenReturn(issue);

        when(bugRepository.save(any(Bug.class)))
                .thenReturn(bug);

        final BugDto bugDto = new BugDto();
        bugDto.setTitle(issue.getTitle());
        bugDto.setDescription(issue.getDescription());

        final Bug createdBug = bugService.createBug(bugDto);
        assertEquals(issue, createdBug.getIssue());
        assertEquals(Bug.Priority.Major, createdBug.getPriority());
    }

    @Test
    public void updateBugStatus() {
        when(bugRepository.findById(anyLong()))
                .thenReturn(Optional.of(bug));
        when(bugRepository.save(any(Bug.class)))
                .then(AdditionalAnswers.returnsFirstArg());

        bug.setStatus(Bug.Status.New);
        final Bug verifiedBug = bugService.verifyBug(bug.getId());
        assertEquals(Bug.Status.Verified, verifiedBug.getStatus());

        final Bug resolvedBug = bugService.resolveBug(bug.getId());
        assertEquals(Bug.Status.Resolved, resolvedBug.getStatus());
    }

    @Test
    public void nonUpdateBugStatus() {
        when(bugRepository.findById(anyLong()))
                .thenReturn(Optional.of(bug));

        bug.setStatus(Bug.Status.Verified);
        bugService.verifyBug(bug.getId());
        verify(bugRepository, times(0)).save(any(Bug.class));

    }

    @Test
    public void updateBug() {
        when(bugRepository.findById(anyLong()))
                .thenReturn(Optional.of(bug));
        when(bugRepository.save(any(Bug.class)))
                .then(AdditionalAnswers.returnsFirstArg());

        final BugDto bugDto = new BugDto();
        bugDto.setDescription("updated-description");
        bugDto.setTitle("updated-title");
        bugDto.setPriority(Bug.Priority.Critical);

        final Bug updatedBug = bugService.updateBug(bugDto, bug.getId());
        assertEquals(bugDto.getPriority(), updatedBug.getPriority());
        assertEquals(bugDto.getDescription(), issue.getDescription());
        assertEquals(bugDto.getTitle(), issue.getTitle());
    }
}
