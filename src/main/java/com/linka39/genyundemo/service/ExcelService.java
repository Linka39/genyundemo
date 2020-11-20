package com.linka39.genyundemo.service;

import javax.servlet.http.HttpServletRequest;

import javax.servlet.http.HttpServletResponse;

/**
 * 路径：com.example.demo.service
 * 类名：
 * 功能：导入导出
 * 备注：
 * 创建人：typ
 * 创建时间：2018/10/19 16:26
 * 修改人：
 * 修改备注：
 * 修改时间：
 */
public interface ExcelService {

    public Boolean exportExcel(HttpServletResponse response, HttpServletRequest request, String fileName, Integer pageNum, Integer pageSize);

    public Boolean importExcel(String fileName);

}
