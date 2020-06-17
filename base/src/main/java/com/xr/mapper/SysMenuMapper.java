package com.xr.mapper;

import com.xr.entity.SysMenu;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface SysMenuMapper {
    @Select("SELECT m.id,m.Name,m.parent_id,m.url,m.perms,m.type,m.icon,m.order_num from sys_role r,sys_role_menu rm,sys_menu m WHERE r.id=rm.role_id AND r.`name` =#{name} and rm.menu_id=m.id")
    public List<SysMenu> findRoleMenus(String name);
}
