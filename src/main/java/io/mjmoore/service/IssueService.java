package io.mjmoore.service;

import io.mjmoore.model.Issue;
import io.mjmoore.model.User;
import io.mjmoore.repository.IssueRepository;
import io.mjmoore.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IssueService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private IssueRepository issueRepository;

    public Issue assignIssue(final Long issueId, final Long userId) {
        final User user = userRepository.findById(userId).orElseThrow();
        final Issue issue = issueRepository.findById(issueId).orElseThrow();

        issue.setAssignee(user);
        return issueRepository.save(issue);
    }
}
