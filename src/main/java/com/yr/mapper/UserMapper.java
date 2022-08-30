package com.yr.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yr.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {

}
