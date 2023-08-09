package com.duo.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * <pre>
 *
 * </pre>
 *
 * @author <a href="https://github.com/HCDUO">HCDUO</a>
 * @date:2023/8/3 23:11
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MenuSimpleVo {
    private Long id;
    private String label;
    private Long parentId;
    private List<? extends MenuSimpleVo> children;
}
