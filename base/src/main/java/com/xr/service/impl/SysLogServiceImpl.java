package com.xr.service.impl;

import com.xr.entity.SysLog;
import com.xr.mapper.SysLogMapper;
import com.xr.service.SysLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysLogServiceImpl implements SysLogService {
    @Autowired
    private SysLogMapper sysLogMapper;

    @Override
    public List<SysLog> logList() {
        return sysLogMapper.logList();
    }

    @Override
    public List<SysLog> logPageList(String logName,String title, Integer page, Integer limit) {
        return sysLogMapper.logPageList(logName,title,page,limit);
    }

    @Override
    public SysLog byIdLogList(Integer id) {
        return sysLogMapper.byIdLogList(id);
    }
}
