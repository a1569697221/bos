package com.xr.service.impl;

import com.xr.entity.SysUser;
import com.xr.entity.SysUserExample;
import com.xr.mapper.SysUserMapper;
import com.xr.mapper.UsersMapper;
import com.xr.service.UsersService;
import com.xr.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServersImpl implements UsersService {
    @Autowired
    private UsersMapper usersMapper;

    @Autowired
    private SysUserMapper sysUserMapper;


    @Override
    public SysUser login(SysUser users) {
        //密码的加密次数必须与shiro的配置类里的加密次数一致，不然密码校验不能通过
        //String md5Password = CommonUtil.getMD5Password(users.getPassword(),users.getSalt(),2);
        String md5Password = CommonUtil.getMD5Password(users.getPassword(),users.getSalt(),2);
        return usersMapper.login(users);
    }

    @Override
    public List<String> findUserRoles(String username) {
        return usersMapper.findUserRoles(username);
    }

    @Override
    public SysUser findUserByUserName(String username) {
        return usersMapper.findUserByUserName(username);
    }

    @Override
    public void add(SysUser user) {
        usersMapper.add(user);
    }

    @Override
    public void deleteById(long id) {
        usersMapper.deleteById(id);
    }

    @Override
    public void update(SysUser user) {
        usersMapper.update(user);
    }

    @Override
    public List<SysUser> list(SysUser sysUser) {
        SysUserExample example = new SysUserExample();
        SysUserExample.Criteria criteria = example.createCriteria();
        if(sysUser!=null){
            if(sysUser.getUsername()!=null){
                criteria.andUsernameLike("%"+sysUser.getUsername()+"%");
            }
            // 还可以添加其他属性的条件
        }
        List<SysUser> list =  sysUserMapper.selectByExample(example);
        return list;
    }

    @Override
    public List<SysUser> listpage(String username,Integer page,Integer limit) {
        return usersMapper.listpage(username,page,limit);
    }

    @Override
    public List<SysUser> user() {
        return usersMapper.user();
    }
}
