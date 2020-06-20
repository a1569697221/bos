package com.xr.controller;

import com.xr.config.ResponseResult;
import com.xr.entity.SysLog;
import com.xr.mapper.SysLogMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("log")
@RestController
public class SysLogController {
    @Autowired
    private SysLogMapper sysLogMapper;

    @RequestMapping("list")
    public ResponseResult list(String username,String title,Integer page,Integer limit){
        System.out.println("进入list");
        List<SysLog> logPageList = sysLogMapper.logPageList(username,title, (page-1)*limit, limit);
        List<SysLog> list = sysLogMapper.logList();
        ResponseResult result = new ResponseResult();
        result.getData().put("items",logPageList);
        result.getData().put("total",list.size());
        return result;
    }

    @RequestMapping("byId")
    public ResponseResult byId(Integer id){
        ResponseResult result = new ResponseResult();
        SysLog sysLog = sysLogMapper.byIdLogList(id);
        result.getData().put("items",sysLog);
        return result;
    }
}
