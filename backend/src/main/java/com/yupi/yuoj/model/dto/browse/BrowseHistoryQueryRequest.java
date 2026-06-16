package com.yupi.yuoj.model.dto.browse;

import com.yupi.yuoj.common.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@EqualsAndHashCode(callSuper = true)
@Data
public class BrowseHistoryQueryRequest extends PageRequest implements Serializable {

    private Long id;

    private Long userId;

    private Long templateId;

    private static final long serialVersionUID = 1L;
}
