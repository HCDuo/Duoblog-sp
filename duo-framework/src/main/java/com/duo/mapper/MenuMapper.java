package com.duo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.duo.domain.entity.Menu;
import com.duo.domain.vo.MenuTreeVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


/**
 * 菜单权限表(Menu)表数据库访问层
 *
 * @author makejava
 * @since 2023-07-31 19:05:33
 */
@Mapper
public interface MenuMapper extends BaseMapper<Menu> {

    List<String> selectPermsByUserId(Long userId);

    List<Menu> selectAllRouterMenu();

    List<Menu> selectRouterMenuTreeByUserId(Long userId);

    List<MenuTreeVo> selectMenuTree();
}
