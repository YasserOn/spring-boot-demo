package com.xkcoding.email.util;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.xkcoding.email.exception.ExcelException;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.List;

@Slf4j
public class ExcelUtil {
    private final static ExcelTypeEnum EXCEL_OUTPUT_TYPE = ExcelTypeEnum.XLS;

    /**
     * 导出 Excel 至输出流
     * <p>
     * 输出格式包含带表头
     *
     * @param os          输出流
     * @param sheetName   sheet名
     * @param list        数据 list，每个元素为一个 BaseRowModel
     * @param targetClass 映射实体类，Excel 模型
     * @param <T>         映射实体类类型
     */
    public static <T> void writeExcel(OutputStream os, String sheetName, List<T> list, Class<T> targetClass) {
        EasyExcel.write(os, targetClass)
                .excelType(EXCEL_OUTPUT_TYPE)
                .sheet(1, sheetName)
                .doWrite(list);
    }

    /**
     * 导出 Excel 至 http 响应对象
     * <p>
     * 输出格式包含带表头
     *
     * @param response    响应对象
     * @param fileName    文件名
     * @param sheetName   sheet名
     * @param list        数据 list，每个元素为一个 BaseRowModel
     * @param targetClass 映射实体类，Excel 模型
     * @param <T>         映射实体类类型
     */
    public static <T> void writeExcelToResp(HttpServletResponse response, String fileName, String sheetName, List<T> list, Class<T> targetClass) {
        OutputStream os = getOutputStreamFromResp(fileName, response);
        writeExcel(os, sheetName, list, targetClass);
    }

    /**
     * 导出 Excel 至 bytes
     * <p>
     * 输出格式包含带表头
     *
     * @param sheetName   sheet名
     * @param list        数据 list，每个元素为一个 BaseRowModel
     * @param targetClass 映射实体类，Excel 模型
     * @param <T>         映射实体类类型
     */
    public static <T> byte[] writeExcelToBytes(String sheetName, List<T> list, Class<T> targetClass) {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        writeExcel(os, sheetName, list, targetClass);
        return os.toByteArray();
    }

    /**
     * 获取用于返回 excel 的 http 输出流
     */
    private static OutputStream getOutputStreamFromResp(String fileName, HttpServletResponse response) {
        try {
            fileName += EXCEL_OUTPUT_TYPE.getValue();
            response.setContentType("application/vnd.ms-excel");
            String fileNameEncode = URLEncoder.encode(fileName, "UTF-8").replaceAll("\\+", "%20");
            response.setHeader("Content-disposition", "attachment;filename=" + fileNameEncode);
            return response.getOutputStream();
        } catch (IOException e) {
            throw new ExcelException("excel输出流获取失败");
        }
    }
}
