package com.yr.service;

import com.yr.entity.User;
import com.yr.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    public void add(User user) {
        userMapper.insert(user);
    }

    public void delete(Integer id) {
        userMapper.deleteById(id);
    }

    public void update(User user) {
        userMapper.updateById(user);
    }

    public User getUpdate(Integer id) {
        return userMapper.selectById(id);
    }

    public List<User> select() {
        return userMapper.selectList(null);
    }

}
