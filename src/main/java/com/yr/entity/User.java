package com.yr.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
@TableName(value = "fastdfs")
public class User {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private String fileName;
    private String groupName;
    private String filePath;

}
