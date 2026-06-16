package com.yupi.yuoj.model.dto.theme;

import com.yupi.yuoj.common.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@EqualsAndHashCode(callSuper = true)
@Data
public class ThemeCategoryQueryRequest extends PageRequest implements Serializable {

    private Long id;

    private String categoryName;

    private static final long serialVersionUID = 1L;
}