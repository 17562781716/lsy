package com.yupi.yuoj.model.dto.checkin;

import com.yupi.yuoj.common.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@EqualsAndHashCode(callSuper = true)
@Data
public class CheckinQueryRequest extends PageRequest implements Serializable {

    private Long id;

    private Long weekId;

    private Long userId;

    private static final long serialVersionUID = 1L;
}