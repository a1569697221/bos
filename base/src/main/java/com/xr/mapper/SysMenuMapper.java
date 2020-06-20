package com.xr.mapper;

import com.xr.entity.SysMenu;
import com.xr.entity.SysRole;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface SysMenuMapper {
    /**
     * 根据用户名查询其菜单权限
     * @param name
     * @return
     */
    @Select("SELECT m.id,m.Name,m.parent_id,m.url,m.perms,m.type,m.icon,m.order_num from sys_role r,sys_role_menu rm,sys_menu m WHERE r.id=rm.role_id AND r.`name` =#{name} and rm.menu_id=m.id")
    public List<SysMenu> findRoleMenus(String name);

    /**
     * 普通查询所有  显示总共多少条记录
     * @return
     */
    @Select("select id,remark from sys_role")
    List<SysRole> roleList();

    /**
     * 分页查询
     * @return
     */
    @Select({"<script>",
            "select id,remark from sys_role" ,
            "where 1=1",
            "<when test='roleName!=null'>",
            "and remark like '%${roleName}%'",
            "</when>",
            "limit #{page},#{limit}",
            "</script>"})
    List<SysRole> findUserNameByRoleList(@Param("roleName") String roleName, @Param("limit") Integer limit, @Param("page") Integer page);
}
