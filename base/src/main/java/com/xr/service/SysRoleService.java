package com.xr.service;

import com.xr.entity.RoleDept;
import com.xr.entity.SysRole;

import java.util.List;
import java.util.Map;

public interface SysRoleService {
    public List<SysRole> findUserByRoless(String username);

    List<Map<String,Object>> list();

    List<Map<String,Object>> findUserNameByRoleList(String roleName,Integer limit,Integer page);

    void addRole(RoleDept roleDept);

    void deleteRole(Integer id);

    void updateRole(RoleDept roleDept,Integer id);
}
