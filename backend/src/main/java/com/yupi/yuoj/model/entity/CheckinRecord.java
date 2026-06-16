package com.yupi.yuoj.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@TableName("checkin_record")
public class CheckinRecord implements Serializable {

    @TableId(type = IdType.AUTO)
    private Long id;

    private Long weekId;

    private Long userId;

    private LocalDate checkinDate;

    private Integer dayIndex;

    private String checkinContent;

    private Integer checkinDuration;

    private static final long serialVersionUID = 1L;
}
