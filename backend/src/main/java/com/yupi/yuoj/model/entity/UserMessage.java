package com.yupi.yuoj.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@TableName("user_message")
public class UserMessage implements Serializable {

    @TableId(type = IdType.AUTO)
    private Long id;

    private Long userId;

    private String messageType;

    private String messageContent;

    private Integer isRead;

    private LocalDateTime createTime;

    private static final long serialVersionUID = 1L;
}
