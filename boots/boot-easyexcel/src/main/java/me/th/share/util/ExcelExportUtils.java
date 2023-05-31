package me.th.share.util;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.builder.ExcelWriterBuilder;
import com.alibaba.excel.write.builder.ExcelWriterSheetBuilder;
import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import com.alibaba.excel.write.metadata.style.WriteFont;
import com.alibaba.excel.write.style.HorizontalCellStyleStrategy;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import me.th.share.excel.AutoColumnWidthStyleStrategy;
import me.th.share.excel.AutoRowHeightStyleStrategy;

import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Collection;

public class ExcelExportUtils {

    private ExcelExportUtils() {
    }

    /**
     * 配置 HttpServletResponse
     *
     * @param response -
     * @param filename 文件名
     * @return HttpServletResponse
     */
    private static HttpServletResponse configResponse(HttpServletResponse response, String filename) {
        String encodedFileName = URLEncoder.encode(filename + ".xlsx", StandardCharsets.UTF_8);
        response.setCharacterEncoding("utf8");
        response.setContentType("application/vnd.ms-excel;charset=utf-8");
        response.setHeader("content-disposition", "attachment;filename=" + encodedFileName);
        response.setHeader("cache-control", "no-store");
        response.setHeader("cache-control", "max-age=0");
        return response;
    }

    /**
     * 导出文件，带有默认样式
     *
     * @param response       -
     * @param filename       文件名
     * @param sheetName      Sheet 名
     * @param pojoClass      导出数据类型
     * @param data           导出数据
     * @param useCommonStyle 是否使用通用样式
     */
    public static <T> void export(HttpServletResponse response,
                                  String filename,
                                  String sheetName,
                                  Class<T> pojoClass,
                                  Collection<? extends T> data,
                                  boolean useCommonStyle) throws Exception {
        ExcelWriterSheetBuilder writerSheetBuilder = EasyExcel
                .write(configResponse(response, filename).getOutputStream(), pojoClass)
                .sheet(sheetName)
                .registerWriteHandler(new AutoRowHeightStyleStrategy())
                .registerWriteHandler(new AutoColumnWidthStyleStrategy())
                .useDefaultStyle(false);
        if (useCommonStyle) {
            writerSheetBuilder.registerWriteHandler(new HorizontalCellStyleStrategy(getHeadStyle(), getContentStyle()));
        }
        writerSheetBuilder.doWrite(() -> data);
    }

    /**
     * 获取 excelWriter
     *
     * @param response       -
     * @param filename       文件名
     * @param pojoClass      导出数据类
     * @param useCommonStyle 是否使用通用样式
     * @return ExcelWriter
     */
    public static ExcelWriter getExcelWriter(HttpServletResponse response,
                                             String filename,
                                             Class<?> pojoClass,
                                             boolean useCommonStyle) throws Exception {
        ExcelWriterBuilder writerBuilder = EasyExcel
                .write(configResponse(response, filename).getOutputStream(), pojoClass)
                .registerWriteHandler(new AutoColumnWidthStyleStrategy())
                .registerWriteHandler(new AutoRowHeightStyleStrategy());
        if (useCommonStyle) {
            writerBuilder.registerWriteHandler(new HorizontalCellStyleStrategy(getHeadStyle(), getContentStyle()));
        }
        return writerBuilder.build();
    }

    /**
     * 标题行样式
     *
     * @return WriteCellStyle
     */
    private static WriteCellStyle getHeadStyle() {
        WriteCellStyle head = new WriteCellStyle();
        // 背景颜色
        head.setFillForegroundColor(IndexedColors.WHITE.getIndex());
        // 字体
        WriteFont font = new WriteFont();
        font.setFontName("等线"); // 字体
        font.setFontHeightInPoints((short) 14); // 字号
        font.setBold(true); // 加粗
        head.setWriteFont(font);
        // 样式
        head.setWrapped(true); // 自动换行
        head.setHorizontalAlignment(HorizontalAlignment.CENTER); // 水平居中对齐
        head.setVerticalAlignment(VerticalAlignment.CENTER); // 垂直居中对齐
        head.setShrinkToFit(true); // 文本收缩
        return head;
    }

    /**
     * 内容样式
     *
     * @return WriteCellStyle
     */
    private static WriteCellStyle getContentStyle() {
        // 字体
        WriteCellStyle content = new WriteCellStyle();
        WriteFont font = new WriteFont();
        font.setFontName("等线"); // 字体
        font.setFontHeightInPoints((short) 11); // 字号
        content.setWriteFont(font);
        // 样式
        content.setWrapped(true); // 自动换行
        content.setHorizontalAlignment(HorizontalAlignment.CENTER); // 水平居中对齐
        content.setVerticalAlignment(VerticalAlignment.CENTER); // 垂直居中对齐
        content.setShrinkToFit(true); // 文本收缩
        return content;
    }
}
