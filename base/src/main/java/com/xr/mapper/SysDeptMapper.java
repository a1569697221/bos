package com.xr.mapper;

import com.xr.entity.SysDept;
import com.xr.entity.SysDeptExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

public interface SysDeptMapper {
    long countByExample(SysDeptExample example);

    int deleteByExample(SysDeptExample example);

    int deleteByPrimaryKey(Long id);

    int insert(SysDept record);

    int insertSelective(SysDept record);

    List<SysDept> selectByExample(SysDeptExample example);

    SysDept selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") SysDept record, @Param("example") SysDeptExample example);

    int updateByExample(@Param("record") SysDept record, @Param("example") SysDeptExample example);

    int updateByPrimaryKeySelective(SysDept record);

    int updateByPrimaryKey(SysDept record);

    List<SysDept> getGroupDept();

    /**
     * 查询所有部门信息
     * @return
     */
    @Select({"select d.`name`,u.`name`,(select `name` from sys_user where sys_user.id=d.leader) leader,d.id,d.state from" ,
            "sys_user u,sys_dept d" ,
            "where u.id = d.take_charge"})
    List<Map<String,Object>> list();

    /**
     * 分页查询
     * @param name
     * @param page
     * @param limit
     * @return
     */
    @Select({"<script>select d.`name` dname,u.`name` take,(select `name` from sys_user where sys_user.id=d.leader) leader,d.id,d.state from" ,
            "sys_user u,sys_dept d" ,
            "where u.id = d.take_charge",
            "<when test='dname!=null'>",
            "and d.`name` like '%${dname}%'",
            "</when>",
            "<when test='take!=null'>",
            "and u.`name` like '%${take}%'",
            "</when>",
            "<when test='state!=null'>",
            "and state = '${state}'",
            "</when>",
            "limit #{page},#{limit}</script>"})
    List<Map<String,Object>> listPage(@Param("take") String take,
                                      @Param("dname") String dname,
                                      @Param("page") Integer page,
                                      @Param("limit") Integer limit,
                                      @Param("state") Integer state);
}
