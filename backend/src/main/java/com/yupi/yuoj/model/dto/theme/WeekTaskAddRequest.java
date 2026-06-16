package com.yupi.yuoj.model.dto.theme;

import lombok.Data;

import java.io.Serializable;

@Data
public class WeekTaskAddRequest implements Serializable {

    private Long weekId;

    private String taskName;

    private Integer taskOrder;

    private static final long serialVersionUID = 1L;
}