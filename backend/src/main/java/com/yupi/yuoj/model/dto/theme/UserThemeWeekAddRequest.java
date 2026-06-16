package com.yupi.yuoj.model.dto.theme;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;

@Data
public class UserThemeWeekAddRequest implements Serializable {

    private Long templateId;

    private String weekName;

    private String weekIcon;

    private String weekDescription;

    private String weekDifficulty;

    private LocalDate startDate;

    private LocalDate endDate;

    private static final long serialVersionUID = 1L;
}