package com.yupi.yuoj.model.dto.checkin;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;

@Data
public class CheckinAddRequest implements Serializable {

    private Long weekId;

    private Integer dayIndex;

    private String checkinContent;

    private Integer checkinDuration;

    private LocalDate checkinDate;

    private static final long serialVersionUID = 1L;
}