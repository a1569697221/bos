package com.xr.controller;

import com.xr.config.ResponseResult;
import com.xr.entity.SysRole;
import com.xr.service.SysMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("menu")
public class SysMenuController {

    @Autowired
    private SysMenuService sysMenuService;

    @RequestMapping("list")
    public ResponseResult roleList(String name,Integer page,Integer limit){
        System.out.println("进入list");
        ResponseResult result = new ResponseResult();
        List<SysRole> roles = sysMenuService.roleList();
        List<SysRole> listpage = sysMenuService.findUserNameByRoleList(name, limit, (page-1)*limit);
        result.getData().put("items", listpage);
        result.getData().put("total", roles.size());
        return result;
    }
}
