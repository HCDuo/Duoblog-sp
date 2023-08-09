package com.duo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.duo.domain.ResponseResult;
import com.duo.domain.entity.Menu;
import com.duo.domain.vo.MenuSimpleVo;
import com.duo.domain.vo.MenuTreeVo;

import java.util.List;


/**
 * 菜单权限表(Menu)表服务接口
 *
 * @author makejava
 * @since 2023-07-31 19:05:34
 */
public interface MenuService extends IService<Menu> {

    List<String> selectPermsByUserId(Long id);

    List<Menu> selectRouterMenuTreeByUserId(Long userId);

    ResponseResult<List<Menu>> adminMenuList(String status, String menuName);

    ResponseResult adminAddMenu(Menu menu);

    ResponseResult getMenuDetail(Long id);

    ResponseResult updateMenu(Menu menu);

    ResponseResult deleteMenu(Long id);

    ResponseResult<List<MenuTreeVo>> listMenuTree();

    List<Long> selectMenuIdsByRoleId(Long id);

    ResponseResult<List<MenuSimpleVo>> selectTreeAll();
}
