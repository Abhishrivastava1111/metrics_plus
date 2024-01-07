package com.taskmaster.manager.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class WorklogEditDto {

    @NotBlank(message = "Name is required")
    @Size(max = 100, message = "Name of Worklog cannot be longer than 100 characters")
    private String name;

    @NotBlank(message = "Description is required")
    @Size(max = 500, message = "Description cannot be longer than 500 characters")
    private String description;

}
