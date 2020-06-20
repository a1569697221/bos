package com.xr.service;

import com.xr.entity.SysLog;

import java.util.List;

public interface SysLogService {
    List<SysLog> logList();

    List<SysLog> logPageList(String logName,String title,Integer page,Integer limit);

    SysLog byIdLogList(Integer id);
}
