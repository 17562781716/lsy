package com.yupi.yuoj.model.dto.theme;

import com.yupi.yuoj.common.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@EqualsAndHashCode(callSuper = true)
@Data
public class UserThemeWeekQueryRequest extends PageRequest implements Serializable {

    private Long id;

    private Long userId;

    private Long templateId;

    private String weekName;

    private String weekStatus;

    private static final long serialVersionUID = 1L;
}