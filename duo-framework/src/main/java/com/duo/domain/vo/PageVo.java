package com.duo.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * <pre>
 * 分页返回类
 * </pre>
 *
 * @author <a href="https://github.com/HCDUO">HCDUO</a>
 * @date:2023/7/26 21:32
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageVo {
    private List rows;
    private Long total;
}
