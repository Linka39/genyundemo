package com.linka39.genyundemo.mapper;


import com.linka39.genyundemo.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 路径：com.example.demo.mapper
 * 类名：
 * 功能：《用一句描述一下》
 * 备注：
 * 创建人：typ
 * 创建时间：2018/10/19 11:08
 * 修改人：
 * 修改备注：
 * 修改时间：
 */
@Mapper
public interface UserMapper {

    @Select("select * from user")
    public List<User> find();

    @Insert("insert into user (id, username, password, enable) values (#{id}, #{username}, #{password}, #{enable})")
    public int add(User user);
}
