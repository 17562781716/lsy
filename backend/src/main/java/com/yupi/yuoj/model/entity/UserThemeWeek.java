package com.yupi.yuoj.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@TableName("user_theme_week")
public class UserThemeWeek implements Serializable {

    @TableId(type = IdType.AUTO)
    private Long id;

    private Long userId;

    private Long templateId;

    private String weekIcon;

    private String weekName;

    private String weekDescription;

    private String weekDifficulty;

    private String weekStatus;

    private LocalDate startDate;

    private LocalDate endDate;

    private Integer progress;

    private static final long serialVersionUID = 1L;
}
