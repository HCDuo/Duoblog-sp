package com.duo.controller;

import com.duo.annotation.SystemLog;
import com.duo.domain.ResponseResult;
import com.duo.domain.entity.Menu;
import com.duo.domain.vo.MenuIdVo;
import com.duo.domain.vo.MenuSimpleVo;
import com.duo.domain.vo.MenuTreeVo;
import com.duo.service.MenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <pre>
 *
 * </pre>
 *
 * @author <a href="https://github.com/HCDUO">HCDUO</a>
 * @date:2023/8/3 0:53
 */
@RestController
@RequestMapping("/system/menu")
@Api(tags = "菜单",description = "菜单相关接口")
public class MenuController {
    @Autowired
    private MenuService menuService;
    @GetMapping("/list")
    @SystemLog(BusinessName = "展示菜单信息")
    @ApiOperation(value = "展示菜单信息",notes = "展示菜单信息")
    public ResponseResult<List<Menu>> adminMenuList(String status ,String menuName){
        return menuService.adminMenuList(status,menuName);
    }

    @PostMapping()
    @SystemLog(BusinessName = "增加菜单")
    @ApiOperation(value = "增加菜单",notes = "增加菜单")
    public ResponseResult adminAddMenu(@RequestBody Menu menu){
        return menuService.adminAddMenu(menu);
    }

    @GetMapping("/{id}")
    @SystemLog(BusinessName = "显示具体菜单信息")
    @ApiOperation(value = "显示具体菜单信息",notes = "显示具体菜单信息")
    public ResponseResult getMenuDetail(@PathVariable("id") Long id){
        return menuService.getMenuDetail(id);
    }

    @PutMapping()
    @SystemLog(BusinessName = "修改具体菜单信息")
    @ApiOperation(value = "修改具体菜单信息",notes = "修改具体菜单信息")
    public ResponseResult updateMenu(@RequestBody Menu menu){
        return menuService.updateMenu(menu);
    }

    @DeleteMapping("/{id}")
    @SystemLog(BusinessName = "删除菜单")
    @ApiOperation(value = "删除菜单",notes = "删除菜单")
    public ResponseResult deleteMenu(@PathVariable("id") Long id) {
        return menuService.deleteMenu(id);
    }
    @GetMapping("/treeselect")
    @SystemLog(BusinessName = "生成菜单树")
    @ApiOperation(value = "生成菜单树",notes = "生成菜单树")
    public ResponseResult<List<MenuTreeVo>> listMenuTree() {
        return menuService.listMenuTree();
    }

    @GetMapping("/roleMenuTreeselect/{id}")
    @SystemLog(BusinessName = "根据id生成菜单树")
    @ApiOperation(value = "根据id生成菜单树",notes = "根据id生成菜单树")
    public ResponseResult<List<MenuTreeVo>> roleMenuTreeselect(@PathVariable("id") Long id) {
        List<Long> longs = menuService.selectMenuIdsByRoleId(id);
        List<MenuSimpleVo> simpleMenuVos = menuService.selectTreeAll().getData();
        MenuIdVo menuIdVo = new MenuIdVo(longs, simpleMenuVos);
        return ResponseResult.okResult(menuIdVo);
    }
}
