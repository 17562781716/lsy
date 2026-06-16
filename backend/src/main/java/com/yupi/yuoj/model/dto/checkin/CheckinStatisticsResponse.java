package com.yupi.yuoj.model.dto.checkin;

import lombok.Data;

import java.io.Serializable;

@Data
public class CheckinStatisticsResponse implements Serializable {

    private Integer totalDays;

    private Integer checkinDays;

    private Double completionRate;

    private Integer continuousDays;

    private static final long serialVersionUID = 1L;
}