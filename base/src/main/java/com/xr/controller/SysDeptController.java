package com.xr.controller;

import com.xr.config.ResponseResult;
import com.xr.service.SysDeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

//import com.xr.util.ResponseResult;

@RestController
@RequestMapping("dept")
public class SysDeptController {
    @Autowired
    private SysDeptService sysDeptService;

    /**
     * 获取分组好的部门信息，供前台部门下拉框使用
     * @return
     */
    @RequestMapping("groupDept")
    public ResponseResult groupDept(){
        ResponseResult result = new ResponseResult();
        result.getData().put("deptList",sysDeptService.getGroupDept());
        return result;
    }

    @RequestMapping("list")
    public ResponseResult list(String take,String dname,Integer state, Integer page, Integer limit, HttpServletRequest request){
        ResponseResult result = new ResponseResult();
        List<Map<String, Object>> list = sysDeptService.list();
        List<Map<String, Object>> listpage = sysDeptService.listPage(take, dname, (page-1)*limit, limit, state);
        System.out.println("URL:"+request.getRequestURI());
        System.out.println("服务器地址:http://"+request.getServerName()+":"+request.getServerPort());
        result.getData().put("items",listpage);
        result.getData().put("total",list.size());
        return result;
    }
}
