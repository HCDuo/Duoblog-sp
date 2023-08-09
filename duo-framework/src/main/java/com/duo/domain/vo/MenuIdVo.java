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
 * @date:2023/8/3 23:19
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MenuIdVo {
    private List<Long> checkedKeys;
    private List<MenuSimpleVo> menus;

}
