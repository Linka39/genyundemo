package com.linka39.genyundemo.entity;

import lombok.Data;

/**
 * 路径：com.example.demo.entity
 * 类名：
 * 功能：《用一句描述一下》
 * 备注：
 * 创建人：typ
 * 创建时间：2018/10/19 11:00
 * 修改人：
 * 修改备注：
 * 修改时间：
 */
@Data
public class User {

    private Integer id;
    private String username;
    private String password;
    private Integer enable;

  /*  @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", enable='" + enable +
                '}';
    }*/
}
