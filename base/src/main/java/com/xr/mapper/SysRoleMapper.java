package com.xr.mapper;

import com.xr.entity.RoleDept;
import com.xr.entity.SysRole;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Map;

public interface SysRoleMapper {
    /**
     * 根据用户名查询角色
     * @param username
     * @return
     */
    @Select("SELECT r.name from sys_user u,sys_role r,sys_user_role ur where r.id = ur.role_id and u.username=#{username} and ur.user_id=u.id")
    public List<SysRole> findUserByRoless(String username);

    /**
     * 普通查询所有  显示总共多少条记录
     * @return
     */
    @Select("select r.id,r.name,r.remark,d.`name` dname,r.describe,r.create_time from sys_dept d,sys_role r\n" +
            "where d.id = r.dept_id")
    List<Map<String,Object>> list();

    /**
     * 分页查询
     * @return
     */

    /*@Select({"<script>",
            "select * from sys_user where 1=1 ",
            "<when test='username!=null'>",
                    "and username like '%${username}%'",
            "</when>",
            "limit #{page},#{limit}",
            "</script>"})*/
    @Select({"<script>",
            "select r.id,r.name,r.remark,d.`name` dname,r.describe,r.create_time from sys_dept d,sys_role r " ,
            "where d.id = r.dept_id",
            "<when test='roleName!=null'>",
                  "and remark like '%${roleName}%'",
             "</when>",
             "limit #{page},#{limit}",
             "</script>"})
    List<Map<String,Object>> findUserNameByRoleList(@Param("roleName") String roleName,@Param("limit") Integer limit,@Param("page") Integer page);

    /**
     * 新增角色
     * @param roleDept
     */
    @Select({"insert into sys_role(id,name,remark,dept_id,create_by,create_time,last_update_time,`describe`) " ,
            "values(null,#{r.name},#{r.remark},#{r.deptId},#{r.createBy},NOW(),NOW(),#{r.describe})"})
    void addRole(@Param("r") RoleDept roleDept);

    /**
     * 根据id删除角色
     * @param id
     */
    @Delete("delete from sys_role where id = #{id}")
    void deleteRole(Integer id);

    /**
     * 修改角色信息
     * @param roleDept
     */
    @Update({"update sys_role set name = #{r.name},remark = #{r.remark}," ,
            "dept_id = #{r.deptId},create_by = #{r.createBy}," +
            "last_update_time = NOW(),`describe` = #{r.describe} where id = #{id}"})
    void updateRole(@Param("r") RoleDept roleDept,@Param("id") Integer id);
}
