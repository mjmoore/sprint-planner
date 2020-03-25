package io.mjmoore.dto;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

public class StoryDto {

    @NotBlank(message = "Title is mandatory.")
    private String title;
    private String description;

    @Min(value = 1, message = "Minimum estimation is 1.")
    @Max(value = 10, message = "Maximum estimation is 10.")
    private Integer estimate;

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

    public Integer getEstimate() {
        return estimate;
    }

    public void setEstimate(Integer estimate) {
        this.estimate = estimate;
    }
}
