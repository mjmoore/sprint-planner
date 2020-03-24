package io.mjmoore.dto;

import io.mjmoore.model.Bug;

public class BugDto {
    private String title;
    private String description;
    private Bug.Priority priority;

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

    public Bug.Priority getPriority() {
        return priority;
    }

    public void setPriority(Bug.Priority priority) {
        this.priority = priority;
    }
}
