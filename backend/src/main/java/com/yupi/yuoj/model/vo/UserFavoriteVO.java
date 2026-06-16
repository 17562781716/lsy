package com.yupi.yuoj.model.vo;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class UserFavoriteVO implements Serializable {

    private Long id;

    private Long userId;

    private Long templateId;

    private String templateName;

    private String templateIcon;

    private LocalDateTime createTime;

    private static final long serialVersionUID = 1L;
}