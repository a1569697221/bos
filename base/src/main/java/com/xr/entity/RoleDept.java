package com.xr.entity;

import lombok.Data;

import java.util.Date;

@Data
public class RoleDept {
    private Integer id;

    private String name;

    private String dname;

    private Date createTime;

    private String remark;

    private String describe;
}
