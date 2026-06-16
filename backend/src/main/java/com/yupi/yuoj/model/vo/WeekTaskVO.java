package com.yupi.yuoj.model.vo;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class WeekTaskVO implements Serializable {

    private Long id;

    private Long weekId;

    private String taskName;

    private Integer taskOrder;

    private Integer isCompleted;

    private LocalDateTime createTime;

    private static final long serialVersionUID = 1L;
}