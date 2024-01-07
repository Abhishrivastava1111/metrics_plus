package com.taskmaster.manager.dto.report;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProjectReportResponseDto {

    private int completionCount;
    private int unCompletionCount;
}
