package com.yupi.yuoj.model.dto.theme;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserThemeWeekUpdateRequest implements Serializable {

    private Long id;

    private String weekName;

    private String weekDescription;

    private String weekDifficulty;

    private String weekStatus;

    private Integer progress;

    private static final long serialVersionUID = 1L;
}