package com.yupi.yuoj.model.dto.theme;

import lombok.Data;

import java.io.Serializable;

@Data
public class ThemeCategoryAddRequest implements Serializable {

    private String categoryName;

    private String categoryIcon;

    private String categoryDescription;

    private Integer sortOrder;

    private static final long serialVersionUID = 1L;
}