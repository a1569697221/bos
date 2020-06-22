package com.xr.controller;

import com.xr.config.ResponseResult;
import com.xr.entity.SysUser;
import com.xr.service.UsersService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("user")
@Api("用户管理相关接口")
public class UsersController {

    @Autowired
    private UsersService usersService;
    /**
     * 获取分组好的部门信息，供前台部门下拉框使用
     *
     * @return
     */

    @RequestMapping("list")
    @RequiresRoles("admin")
    @ApiOperation(value = "获得用户列表",notes = "获得用户列表")
    public ResponseResult list(SysUser sysUser, Integer page, Integer limit) {
        // 查询的所有，还未分页
        List<SysUser> list = usersService.list(sysUser);
        String username = sysUser.getUsername();
        List<SysUser> listpage = usersService.listpage(username, (page-1)*limit, limit);
        List<SysUser> user = usersService.user();
        System.out.println(user);
        /*for (int i =0;i<list.size();i++){
            listpage.get(i).setCreateTime(list.get(i).getCreateTime());
        }*/
        ResponseResult result = new ResponseResult();
        result.getData().put("items", listpage);
        result.getData().put("total", list.size());
        return result;
    }

    @RequestMapping("add")
    @RequiresRoles("admin")
    @ApiOperation(value = "添加用户",notes = "添加用户")
    public ResponseResult add(SysUser sysUser) {
        //生成盐（部分，需要存入数据库中）
        String salt=new SecureRandomNumberGenerator().nextBytes().toHex();
        //将原始密码加盐（上面生成的盐），并且用md5算法加密两次，将最后结果存入数据库中
        String password = new Md5Hash(sysUser.getPassword(),salt,2).toString();
        sysUser.setSalt(salt);
        sysUser.setPassword(password);
        System.out.println(sysUser);
        usersService.add(sysUser);
        ResponseResult result = new ResponseResult();
        result.getData().put("message","添加成功");
        return result;
    }

    @RequestMapping("update")
    @RequiresRoles("admin")
    @ApiOperation(value = "修改用户",notes = "修改用户")
    public ResponseResult update(SysUser sysUser) {
        //生成盐（部分，需要存入数据库中）
        String salt=new SecureRandomNumberGenerator().nextBytes().toHex();
        //将原始密码加盐（上面生成的盐），并且用md5算法加密两次，将最后结果存入数据库中
        String password = new Md5Hash(sysUser.getPassword(),salt,2).toString();
        sysUser.setSalt(salt);
        sysUser.setPassword(password);
        usersService.update(sysUser);
        ResponseResult result = new ResponseResult();
        result.getData().put("message","修改成功");
        return result;
    }

    @RequestMapping("delete")
    @RequiresRoles("SuperAdmin")
    @ApiOperation(value = "删除用户",notes = "删除用户")
    public ResponseResult add(Long id) {
        usersService.deleteById(id);
        ResponseResult result = new ResponseResult();
        result.getData().put("message", "删除成功");
        return result;
    }

    @RequestMapping("login")
    @ApiOperation(value = "用户登录",notes = "用户登录")
    public ResponseResult login(SysUser sysUser){
        ResponseResult result = new ResponseResult();
        UsernamePasswordToken token = new UsernamePasswordToken(sysUser.getUsername(), sysUser.getPassword());
        // 获得登录的主题
        Subject subject = SecurityUtils.getSubject();
        // 调用登录方法
        System.out.println(sysUser);
        System.out.println(token);
        subject.login(token);
        // 返回给前台的toke，唯一标识用户
        result.getData().put("token",subject.getSession().getId());
        return result;
    }

    @RequestMapping("info")
    @ApiOperation(value = "获得登录用户信息",notes = "获得登录用户信息")
    public ResponseResult info(String token){
        Subject subject = SecurityUtils.getSubject();
        Session session = subject.getSession();
        ResponseResult result = new ResponseResult();
        if(token.equals(subject.getSession().getId().toString())){
            // 从shiro的session里获得保存的用户信息
            SysUser loginUser = (SysUser) session.getAttribute("USER_SESSION");
            // 获得角色字符串集合
            List<String> roles = (List<String>) session.getAttribute("roles");
            if(loginUser!=null){
                // 根据用户获得角色字符串数组
                roles = usersService.findUserRoles(loginUser.getUsername());
                result.getData().put("roles",roles);
                result.getData().put("introduction",loginUser.getIntroduction());
                result.getData().put("avatar",loginUser.getAvatar());
                result.getData().put("name",loginUser.getUsername());
                result.getData().put("id",loginUser.getId());
                return result;
            }
        }
        return null;
    }

    /**
     * 登出方法
     * @return
     */
    @RequestMapping("logout")
    @ApiOperation(value = "注销用户",notes = "注销用户")
    public ResponseResult logout(){
        ResponseResult result = new ResponseResult();
        Subject subject = SecurityUtils.getSubject();
        // 只需调用shiro的logout方法就可以了
        subject.logout();
        return result;
    }
}
