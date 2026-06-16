package com.yupi.yuoj.model.dto.theme;

import lombok.Data;

import java.io.Serializable;

@Data
public class ThemeCategoryUpdateRequest implements Serializable {

    private Long id;

    private String categoryName;

    private String categoryIcon;

    private String categoryDescription;

    private Integer sortOrder;

    private static final long serialVersionUID = 1L;
}