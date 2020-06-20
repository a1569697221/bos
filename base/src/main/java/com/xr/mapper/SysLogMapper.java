package com.xr.mapper;

import com.xr.entity.SysLog;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface SysLogMapper {
    /**
     * 查询所有日志数据
     * @return
     */
    @Select("select * from sys_log")
    List<SysLog> logList();

    /**
     * 分页数据
     * @param logName
     * @param page
     * @param limit
     * @return
     */
    @Select({"<script>select \n" +
            "\tl.id,\n" +
            "\tl.title,\n" +
            "\tu.name,\n" +
            "\tl.url,\n" +
            "\tl.method,\n" +
            "\tl.ip,\n" +
            "\tl.`data`,\n" +
            "\tl.terminal,\n" +
            "\tl.type,\n" +
            "\tl.time\n" +
            "from sys_log l,sys_user u\n" +
            "where l.sid = u.id",
            "<when test='logName!=null'>",
            "and name like '%${logName}%'",
            "</when>",
            "<when test='title!=null'>",
            "and title like '%${title}%'",
            "</when>",
            "limit #{page},#{limit}</script>"})
    List<SysLog> logPageList(@Param("logName") String logName,@Param("title") String title,@Param("page") Integer page,@Param("limit") Integer limit);

    @Select("select \n" +
            "\tl.id,\n" +
            "\tl.title,\n" +
            "\tu.name,\n" +
            "\tl.url,\n" +
            "\tl.method,\n" +
            "\tl.ip,\n" +
            "\tl.`data`,\n" +
            "\tl.terminal,\n" +
            "\tl.type,\n" +
            "\tl.time\n" +
            "from sys_log l,sys_user u\n" +
            "where l.sid = u.id and l.id = #{id}")
    SysLog byIdLogList(@Param("id") Integer id);
}
