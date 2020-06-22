package com.xr.service;

import com.xr.entity.SysDept;

import java.util.List;
import java.util.Map;

public interface SysDeptService {
    List<SysDept> getGroupDept();

    List<Map<String,Object>> listPage(String take,
                                       String dname,
                                      Integer page,
                                      Integer limit,
                                      Integer state);

    List<Map<String,Object>> list();
}
