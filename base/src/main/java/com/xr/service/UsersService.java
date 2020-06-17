package com.xr.service;

import com.xr.entity.SysDept;
import com.xr.entity.SysUser;
import com.xr.entity.SysUserExample;

import java.util.List;

public interface UsersService {
    SysUser login(SysUser loginUser);

    List<String> findUserRoles(String username);

    public SysUser findUserByUserName(String username);

    void add (SysUser user);

    void deleteById(long id);

    void update(SysUser user);

    List<SysUser> list(SysUser sysUser);

    List<SysUser> listpage(String username,Integer page,Integer limit);
}
