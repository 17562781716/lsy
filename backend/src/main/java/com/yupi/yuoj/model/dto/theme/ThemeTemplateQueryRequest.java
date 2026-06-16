package com.yupi.yuoj.model.dto.theme;

import com.yupi.yuoj.common.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@EqualsAndHashCode(callSuper = true)
@Data
public class ThemeTemplateQueryRequest extends PageRequest implements Serializable {

    private Long id;

    private Long categoryId;

    private String templateName;

    private String difficulty;

    private static final long serialVersionUID = 1L;
}