package io.mjmoore.model;

import io.mjmoore.dto.BugDto;
import io.mjmoore.dto.StoryDto;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import java.util.Date;

@Entity
@Embeddable
public class Issue {

    public static Issue fromDto(final BugDto bug) {
        return new Issue(bug.getTitle(), bug.getDescription());
    }

    public static Issue fromDto(final StoryDto story) {
        return new Issue(story.getTitle(), story.getDescription());
    }

    public Issue() {}

    private Issue(final String title, final String description) {
        this.title = title;
        this.description = description;
    }

    public Issue(final Long id, final String title, final String description) {
        this(title, description);
        this.id = id;
    }

    @Id
    @GeneratedValue
    private Long id;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String title;
    public String description;

    @CreatedDate
    public Date creationDate = new Date();

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "AssigneeId")
    private User assignee;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getAssignee() {
        return assignee;
    }

    public void setAssignee(final User assignee) {
        this.assignee = assignee;
    }
}
