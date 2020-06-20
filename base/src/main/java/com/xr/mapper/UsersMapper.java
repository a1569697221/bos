package com.xr.mapper;

import com.xr.entity.SysUser;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface UsersMapper {


    /**
     * 用户登录
     * @param loginUser
     * @return
     */
    @Select("select * from sys_user where username = #{username} and password = #{password}")
    SysUser login(SysUser loginUser);

    /**
     * 根据用户名查询角色
     * @param username
     * @return
     */
    @Select("SELECT r.name from sys_user u,sys_role r,sys_user_role ur\n" +
            "    where r.id = ur.role_id and u.username=#{username} and ur.user_id=u.id")
    List<String> findUserRoles(String username);

    /**
     * 根据用户名查询用户信息
     * @return
     */
    @Select("select * from sys_user where username = #{username}")
    SysUser findUserByUserName(String username);

    /**
     * 新增用户
     * @param user
     */
    @Insert("insert into sys_user" +
            " values(0,#{u.name},#{u.username},#{u.password},#{u.salt},#{u.email},#{u.mobile}," +
            "1,#{u.deptId},'admin',NOW(),'admin',NOW(),0,#{u.introduction},null)")
    void add (@Param("u") SysUser user);

    /**
     * 根据id删除用户
     * @param id
     */
    @Delete("delete from sys_user where id = #{id}")
    void deleteById(long id);

    /**
     * 修改用户信息
     * @param user
     */
    @Update("update sys_user set name = #{u.name},username = #{u.username},password = #{u.password},salt = #{u.salt},email = #{u.email}," +
            "mobile = #{u.mobile},dept_Id = #{u.deptId},introduction = #{u.introduction},last_Update_Time = NOW() where id = #{u.id}")
    void update(@Param("u") SysUser user);

    /**
     * 查询用户信息
     * @return
     */
    @Select({"<script>",
            "select * from sys_user where 1=1 ",
            "<when test='username!=null'>",
                    "and username like '%${username}%'",
            "</when>",
            "limit #{page},#{limit}",
            "</script>"})
    List<SysUser> listpage(@Param("username") String username,@Param("page") Integer page,@Param("limit") Integer limit);

    @Select("select * from sys_user")
    List<SysUser> user();
}
