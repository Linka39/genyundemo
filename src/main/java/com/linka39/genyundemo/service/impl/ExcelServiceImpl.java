package com.linka39.genyundemo.service.impl;

import com.linka39.genyundemo.entity.ExcelData;
import com.linka39.genyundemo.entity.User;
import com.linka39.genyundemo.mapper.UserMapper;
import com.linka39.genyundemo.service.ExcelService;
import com.linka39.genyundemo.utils.ExcelUtil;
import lombok.extern.slf4j.Slf4j;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * 路径：com.linka39.genyundemo.service.impl
 * 类名：
 * 功能：《用一句描述一下》
 * 备注：
 * 创建人：typ
 * 创建时间：2018/10/19 16:28
 * 修改人：
 * 修改备注：
 * 修改时间：
 */
@Slf4j
@Service
public class ExcelServiceImpl implements ExcelService {

    @Autowired
    private UserMapper userMapper;

    /**
     * 方法名：
     * 功能：导出
     * 描述：
     * 创建人：typ
     * 创建时间：2018/10/19 16:29
     * 修改人：
     * 修改描述：
     * 修改时间：
     */
    @Override
    public Boolean exportExcel(HttpServletResponse response, HttpServletRequest request, String fileName, Integer pageNum, Integer pageSize) {
        log.info("导出数据开始。。。。。。");
        //查询数据并赋值给ExcelData
        List<User> userList = userMapper.find();
        List<String[]> list = new ArrayList<String[]>();
        for (User user : userList) {
            String[] arrs = new String[8];
            arrs[0] = String.valueOf(user.getId());
            arrs[1] = String.valueOf(user.getUsername());
            arrs[2] = String.valueOf(user.getPassword());
            arrs[3] = String.valueOf(user.getEnable());
            list.add(arrs);
        }
        //表头赋值
        String[] head = {"序列", "用户名", "密码", "状态"};
        ExcelData data = new ExcelData();
        data.setHead(head);
        data.setData(list);
        data.setFileName(fileName);
        //实现导出
        try {
            ExcelUtil.exportExcel(response,request, data);
            log.info("导出数据结束。。。。。。");
            return true;
        } catch (Exception e) {
            log.info("导出数据失败。。。。。。");
            return false;
        }
    }

    /**
     * 方法名：
     * 功能：导入
     * 描述：
     * 创建人：typ
     * 创建时间：2018/10/19 16:29
     * 修改人：
     * 修改描述：
     * 修改时间：
     */
    @Override
    public Boolean importExcel(String fileName) {
        log.info("导入数据开始。。。。。。");
        try {
            List<Object[]> list = ExcelUtil.importExcel(fileName);
            for (int i = 0; i < list.size(); i++) {
                User user = new User();
                user.setId(null);
                user.setUsername((String) list.get(i)[1]);
                user.setPassword((String) list.get(i)[2]);
                user.setEnable((Integer) list.get(i)[3]);
                System.out.println("yes");
                userMapper.add(user);
            }
            log.info("导入数据结束。。。。。。");
            return true;
        } catch (Exception e) {
            log.info("导入数据失败。。。。。。");
            e.printStackTrace();
        }
        return false;
    }
}
