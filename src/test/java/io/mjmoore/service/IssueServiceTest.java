package io.mjmoore.service;

import io.mjmoore.model.Issue;
import io.mjmoore.model.User;
import io.mjmoore.repository.IssueRepository;
import io.mjmoore.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.AdditionalAnswers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class IssueServiceTest {

    private Issue issue;
    private User user;

    @Mock
    private IssueRepository issueRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private IssueService issueService = new IssueService();

    @BeforeEach
    public void setup() {
        issue = new Issue(1L, "title", "description");
        user = new User(2L, "test-user");
    }

    @Test
    public void assignIssue() {
        when(issueRepository.save(any(Issue.class)))
                .then(AdditionalAnswers.returnsFirstArg());
        when(issueRepository.findById(anyLong()))
                .thenReturn(Optional.of(issue));

        when(userRepository.findById(anyLong()))
                .thenReturn(Optional.of(user));

        final Issue assignedIssue = issueService.assignIssue(issue.getId(), user.getId());
        assertEquals(user.getId(), assignedIssue.getAssignee().getId());
    }

    @Test
    public void assignInvalidIssue() {
        when(userRepository.findById(anyLong()))
                .thenReturn(Optional.of(user));
        when(issueRepository.findById(anyLong()))
                .thenThrow(new NoSuchElementException());

        assertThrows(NoSuchElementException.class, () -> issueService.assignIssue(issue.getId(), user.getId()));
    }

    @Test
    public void assignIssueToInvalidUser() {
        when(userRepository.findById(anyLong()))
                .thenThrow(new NoSuchElementException());

        assertThrows(NoSuchElementException.class, () -> issueService.assignIssue(issue.getId(), user.getId()));
    }

}