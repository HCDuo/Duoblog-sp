package com.duo.domain.vo;

import com.duo.domain.entity.Menu;
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
 * @date:2023/8/1 14:21
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoutersVo {

    private List<Menu> menus;
}
