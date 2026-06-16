package com.yupi.yuoj.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("week_task")
public class WeekTask implements Serializable {

    @TableId(type = IdType.AUTO)
    private Long id;

    private Long weekId;

    private String taskName;

    private Integer taskOrder;

    private Integer isCompleted;

    private static final long serialVersionUID = 1L;
}
