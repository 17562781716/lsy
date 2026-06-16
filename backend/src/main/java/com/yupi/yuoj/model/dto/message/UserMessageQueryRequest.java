package com.yupi.yuoj.model.dto.message;

import com.yupi.yuoj.common.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@EqualsAndHashCode(callSuper = true)
@Data
public class UserMessageQueryRequest extends PageRequest implements Serializable {

    private Long id;

    private Long userId;

    private String messageType;

    private Integer isRead;

    private static final long serialVersionUID = 1L;
}
