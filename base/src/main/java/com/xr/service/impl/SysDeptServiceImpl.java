package com.xr.service.impl;

import com.xr.entity.SysDept;
import com.xr.entity.SysDeptExample;
import com.xr.mapper.SysDeptMapper;
import com.xr.service.SysDeptService;
import com.xr.util.SysDeptGroupUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;

@Service
public class SysDeptServiceImpl implements SysDeptService {
    @Autowired
    private SysDeptMapper sysDeptMapper;

    @Override
    public List<SysDept> getGroupDept() {
        SysDeptExample example = new SysDeptExample();
        SysDeptExample.Criteria criteria = example.createCriteria();
        criteria.andDelFlagEqualTo(new Byte("0"));
        List<SysDept> deptList = sysDeptMapper.selectByExample(example);
        if(deptList!=null && deptList.size()>0){
            // 进行递归分组
            SysDeptGroupUtil groupUtil = new SysDeptGroupUtil();
            deptList = groupUtil.getFirstDept(deptList);
            return deptList;
        }
        System.out.println(deptList);
        return null;
    }

    @Override
    public List<Map<String, Object>> listPage(String take, String dname, Integer page, Integer limit, Integer state) {
        return sysDeptMapper.listPage(take,dname,page,limit,state);
    }

    @Override
    public List<Map<String, Object>> list() {
        return sysDeptMapper.list();
    }
}
