package com.xr.service.impl;

import com.xr.entity.SysRole;
import com.xr.mapper.SysRoleMapper;
import com.xr.service.SysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class SysRoleServiceImpl implements SysRoleService {

    @Autowired
    private SysRoleMapper sysRoleMapper;

    @Override
    public List<SysRole> findUserByRoless(String username) {
        return sysRoleMapper.findUserByRoless(username);
    }

    @Override
    public List<Map<String, Object>> list() {
        return sysRoleMapper.list();
    }

    @Override
    public List<Map<String,Object>> findUserNameByRoleList(String roleName, Integer limit, Integer page) {
        return sysRoleMapper.findUserNameByRoleList(roleName,limit,page);
    }
}
