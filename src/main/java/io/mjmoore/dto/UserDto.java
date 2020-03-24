package io.mjmoore.user.dto;

public class UserDto {

    private String name;

    public UserDto() {}

    public UserDto(final String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
