package com.javaProject.shortlink.admin.dao.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.javaProject.shortlink.admin.common.database.BaseDO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@TableName("t_group")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GroupDO extends BaseDO {

    private Long id;
    private String gid;
    private String name;
    private String username;
}