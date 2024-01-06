package com.taskmaster.manager.dto;

import java.util.Date;
import java.util.List;
import com.taskmaster.manager.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class ProjectResponseDto {
    private Long userId;
    private String name;
    private Date startDate;
    private Date endDate;
    private String description;
    private List<UserResponse> members;
}
