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
 * @date:2023/8/4 16:46
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LinkUpdateVo {
    private Long id;

    private String name;

    private String logo;

    private String description;

    private String address;

    private String status;
}
