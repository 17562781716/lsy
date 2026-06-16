package com.yupi.yuoj.model.vo;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class UserMessageVO implements Serializable {

    private Long id;

    private Long userId;

    private String messageType;

    private String messageContent;

    private Integer isRead;

    private LocalDateTime createTime;

    private static final long serialVersionUID = 1L;
}