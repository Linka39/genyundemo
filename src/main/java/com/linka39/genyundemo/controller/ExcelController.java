package com.linka39.genyundemo.controller;


import com.linka39.genyundemo.service.ExcelService;
import lombok.extern.slf4j.Slf4j;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

import static com.linka39.genyundemo.action.MainAction.MainGenInfo;

/**
 * 路径：com.example.demo.controller
 * 类名：
 * 功能：excel导入导出
 * 备注：
 * 创建人：typ
 * 创建时间：2018/10/19 9:58
 * 修改人：
 * 修改备注：
 * 修改时间：
 */
@Slf4j
@RestController
public class ExcelController {

    @Autowired
    private ExcelService excelService;

    /**
     * 方法名：
     * 功能：导出
     * 描述：文件格式为xls或xlsx
     */
    @GetMapping("/export")
    public String exportExcel(HttpServletResponse response, HttpServletRequest request, String fileName, Integer pageNum, Integer pageSize) {
        fileName = "用户信息表.xls";
        if (fileName == null || "".equals(fileName)) {
            return "文件名不能为空！";
        } else {
            if (fileName.endsWith("xls")) {
                Boolean isOk = excelService.exportExcel(response,request, fileName, 1, 10);
                if (isOk) {
                    return "导出成功！";
                } else {
                    return "导出失败！";
                }
            }
            return "文件格式有误！";
        }
    }

    /**
     * 方法名：import
     * 功能：导入
     * 描述：文件格式为xls或xlsx
     */
    @GetMapping("/import/{fileName}")
    public String importExcel(@PathVariable(value = "fileName")String fileName) {
        if (fileName == null && "".equals(fileName)) {
            return "文件名不能为空！";
        } else {
            fileName="D:/"+fileName;
            if (fileName.endsWith("xls") || fileName.endsWith("xlsx")) {
                Boolean isOk = excelService.importExcel(fileName);
                if (isOk) {
                    return "导入成功！";
                } else {
                    return "导入失败！";
                }
            }
            return "文件格式错误！";
        }
    }

    /**
     * 方法名：import
     * 功能：爬取
     * 修改描述：
     * 修改时间：
     */
    @GetMapping("/genInfodb")
    public String GenInfodb() {
        String fileName = "百度云爬取表";
        MainGenInfo();
        if (fileName == null && "".equals(fileName)) {
            return "文件名不能为空！";
        } else {
            fileName="D:/"+fileName;
            if (fileName.endsWith("xls") || fileName.endsWith("xlsx")) {
                Boolean isOk = excelService.importExcel(fileName);
                if (isOk) {
                    return "导入成功！";
                } else {
                    return "导入失败！";
                }
            }
            return "文件格式错误！";
        }
    }

}
