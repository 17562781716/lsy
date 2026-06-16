package com.yupi.yuoj.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@TableName("theme_category")
public class ThemeCategory implements Serializable {

    @TableId(type = IdType.AUTO)
    private Long id;

    private String categoryName;

    private String categoryIcon;

    private String categoryDescription;

    private Integer sortOrder;

    private Integer isDelete;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private static final long serialVersionUID = 1L;
}
