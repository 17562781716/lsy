package com.yupi.yuoj.model.dto.theme;

import com.yupi.yuoj.common.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@EqualsAndHashCode(callSuper = true)
@Data
public class WeekTaskQueryRequest extends PageRequest implements Serializable {

    private Long id;

    private Long weekId;

    private String taskName;

    private Integer isCompleted;

    private static final long serialVersionUID = 1L;
}