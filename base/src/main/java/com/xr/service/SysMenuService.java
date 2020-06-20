package com.xr.service;

import com.xr.entity.SysMenu;
import com.xr.entity.SysRole;

import java.util.List;

public interface SysMenuService {
    List<SysMenu> findRoleMenus(String name);

    List<SysRole> findUserNameByRoleList(String roleName, Integer limit, Integer page);

    List<SysRole> roleList();

}
