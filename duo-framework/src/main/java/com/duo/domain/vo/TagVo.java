package com.duo.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <pre>
 *
 * </pre>
 *
 * @author <a href="https://github.com/HCDUO">HCDUO</a>
 * @date:2023/8/2 15:44
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TagVo {

    private Long id;

    private String name;

    private String remark;

}
