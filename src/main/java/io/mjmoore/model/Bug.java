package io.mjmoore.model;

import io.mjmoore.dto.BugDto;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
public class Bug {

    public enum Status {
        New {
            public Status next() {
                return Verified;
            }
        }, Verified {
            public Status next() {
                return Resolved;
            }
        }, Resolved {
            public Status next() {
                return Resolved;
            }
        };

        public abstract Status next();
    }

    public enum Priority {
        Critical, Major, Minor
    }

    @Id
    @GeneratedValue
    private Long id;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "Issue_Id")
    private Issue issue;

    @Enumerated(EnumType.STRING)
    private Status status = Status.New;

    @Enumerated(EnumType.STRING)
    private Priority priority = Priority.Major;

    public Bug() {}

    public Bug(final Long id, final Issue issue) {
        this.id = id;
        this.issue = issue;
    }

    private Bug(final Issue issue, final Priority priority) {
        this.setIssue(issue);

        if(priority != null) {
            this.setPriority(priority);
        }
    }

    public static Bug fromDto(final BugDto bugDto, final Issue issue) {
        return new Bug(issue, bugDto.getPriority());
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
