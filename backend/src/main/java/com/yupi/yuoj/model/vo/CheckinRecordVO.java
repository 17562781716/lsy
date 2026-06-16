package com.yupi.yuoj.model.vo;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class CheckinRecordVO implements Serializable {

    private Long id;

    private Long weekId;

    private Long userId;

    private LocalDate checkinDate;

    private Integer dayIndex;

    private String checkinContent;

    private Integer checkinDuration;

    private LocalDateTime createTime;

    private static final long serialVersionUID = 1L;
}