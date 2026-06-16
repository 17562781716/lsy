package com.yupi.yuoj.model.vo;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class ThemeTemplateVO implements Serializable {

    private Long id;

    private Long categoryId;

    private String categoryName;

    private String templateName;

    private String templateIcon;

    private String templateDescription;

    private String templateTags;

    private String templateIdeas;

    private Integer participants;

    private String difficulty;

    private LocalDateTime createTime;

    private static final long serialVersionUID = 1L;
}