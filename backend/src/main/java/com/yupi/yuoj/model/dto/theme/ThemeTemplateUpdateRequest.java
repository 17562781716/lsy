package com.yupi.yuoj.model.dto.theme;

import lombok.Data;

import java.io.Serializable;

@Data
public class ThemeTemplateUpdateRequest implements Serializable {

    private Long id;

    private Long categoryId;

    private String templateName;

    private String templateIcon;

    private String templateDescription;

    private String templateTags;

    private String templateIdeas;

    private String difficulty;

    private static final long serialVersionUID = 1L;
}