package io.mjmoore.model;

import io.mjmoore.dto.StoryDto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import java.util.EnumSet;

@Entity
public class Story {

    public enum Status {
        New, Estimated, Completed
    }

    @Id
    @GeneratedValue
    public Long id;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn//(name = "IssueId")
    private Issue issue;

    private Integer estimate;

    @Enumerated(EnumType.STRING)
    private Status status = Status.New;

    public Story() {}

    public Story(final Long id, final Issue issue){
        this.id = id;
        this.setIssue(issue);
    }

    private Story(final Issue issue, final Integer estimate) {
        this.setIssue(issue);
        setEstimate(estimate);
    }

    public static Story fromDto(final Issue issue, final StoryDto storyDto) {
        return new Story(issue, storyDto.getEstimate());
    }

    public Integer getEstimate() {
        return estimate;
    }

    public void setEstimate(final Integer estimate) {

        // Should only estimate new tickets, or change existing ones
        if(!EnumSet.of(Status.New, Status.Estimated).contains(getStatus())) {
            return;
        }

        this.estimate = estimate;
        if(estimate != null) {
            this.setStatus(Status.Estimated);
        }
    }


    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Issue getIssue() {
        return issue;
    }

    public void setIssue(Issue issue) {
        this.issue = issue;
    }
}
