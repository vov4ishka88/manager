package com.challenge.manager.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PlayerCreateRequest {
    @NotBlank(message = "Name is required")
    private String name;

    @NotNull(message = "Age is required")
    @Min(value = 16, message = "Age must be at least 16")
    private Integer age;

    @NotNull(message = "Experience months are required")
    @Min(value = 0, message = "Experience months must be a positive number")
    private Integer experienceMonths;

    @NotNull(message = "TeamId is required")
    private Long teamId;
}
