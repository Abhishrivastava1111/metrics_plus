package com.taskmaster.manager.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class TaskDto {

    @NotBlank(message = "Name is required")
    @Size(max = 100, message = "Name of Task cannot be longer than 100 characters")
    private String name;

    @NotBlank(message = "Description is required")
    @Size(max = 500, message = "Description cannot be longer than 500 characters")
    private String desc;

    private boolean status;

    @NotNull(message = "Project ID is required")
    private Long projectId;

}
