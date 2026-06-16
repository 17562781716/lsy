package com.yupi.yuoj.model.vo;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class UserThemeWeekVO implements Serializable {

    private Long id;

    private Long userId;

    private Long templateId;

    private String templateName;

    private String weekIcon;

    private String weekName;

    private String weekDescription;

    private String weekDifficulty;

    private String weekStatus;

    private LocalDate startDate;

    private LocalDate endDate;

    private Integer progress;

    private LocalDateTime createTime;

    private static final long serialVersionUID = 1L;
}