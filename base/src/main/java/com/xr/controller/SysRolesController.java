package com.xr.controller;

import com.xr.config.ResponseResult;
import com.xr.entity.test;
import com.xr.service.SysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("role")
public class SysRolesController {
    @Autowired
    private SysRoleService sysRoleService;

    @RequestMapping("list")
    public ResponseResult roleList(Integer limit, Integer page, String username){
        //System.out.println(username);
        ResponseResult result = new ResponseResult();
        //两表联查所得结果
        List<Map<String, Object>> list = sysRoleService.list();
        /*for (Map<String, Object> item : list) {
            for (String s : item.keySet()){
                System.out.println("key:"+s+",value:"+item.get(s));
            }
            System.out.println();
        }*/
        //条件查询分页
        List<Map<String, Object>> listpage = sysRoleService.findUserNameByRoleList(username, limit, (page-1)*limit);
        result.getData().put("items", listpage);
        result.getData().put("total", list.size());
        return result;
    }

    @RequestMapping("add")
    public void add(test test){
        System.out.println(test);
    }
}
