package com.xr.entity;

import lombok.Data;

import java.util.Date;

@Data
public class RoleDept {

    private String remark;

    private String name;

    private Long deptId;

    private String createBy;

    private Date createTime;

    private String describe;


}
