package com.yupi.yuoj.model.vo;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class ThemeCategoryVO implements Serializable {

    private Long id;

    private String categoryName;

    private String categoryIcon;

    private String categoryDescription;

    private Integer sortOrder;

    private LocalDateTime createTime;

    private static final long serialVersionUID = 1L;
}