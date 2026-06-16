package com.yupi.yuoj.model.dto.theme;

import lombok.Data;

import java.io.Serializable;

@Data
public class WeekTaskUpdateRequest implements Serializable {

    private Long id;

    private String taskName;

    private Integer taskOrder;

    private Integer isCompleted;

    private static final long serialVersionUID = 1L;
}