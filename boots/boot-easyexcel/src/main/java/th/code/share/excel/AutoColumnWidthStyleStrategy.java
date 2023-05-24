package th.code.share.excel;

import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.Head;
import com.alibaba.excel.metadata.data.WriteCellData;
import com.alibaba.excel.util.MapUtils;
import com.alibaba.excel.write.metadata.holder.WriteSheetHolder;
import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import com.alibaba.excel.write.metadata.style.WriteFont;
import com.alibaba.excel.write.style.column.AbstractColumnWidthStyleStrategy;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.poi.ss.usermodel.Cell;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 自适应列宽样式策略
 */
public class AutoColumnWidthStyleStrategy extends AbstractColumnWidthStyleStrategy {

    private final static String PATTERN = "[a-zA-Z\\s\\d~!@#$%^&*()\\-_=+\\\\|{}\\[\\]:;\"',.<>/?`]";
    private final static Short DEFAULT_HEAD_FONT_SIZE = 14;
    private final static Short DEFAULT_CONTENT_FONT_SIZE = 11;
    private final Map<Integer, Map<Integer, Integer>> cache = MapUtils.newHashMapWithExpectedSize(8);

    protected void setColumnWidth(WriteSheetHolder writeSheetHolder, List<WriteCellData<?>> cellDataList, Cell cell, Head head, Integer relativeRowIndex, Boolean isHead) {
        boolean needSetWidth = isHead || !CollectionUtils.isEmpty(cellDataList);
        if (needSetWidth) {
            Map<Integer, Integer> maxColumnWidthMap = this.cache.computeIfAbsent(writeSheetHolder.getSheetNo(), (key) -> new HashMap<>(16));
            Integer columnWidth = this.dataLength(cellDataList, cell, isHead);
            if (columnWidth >= 0) {
                if (columnWidth > 255) {
                    columnWidth = 255;
                }

                Integer maxColumnWidth = maxColumnWidthMap.get(cell.getColumnIndex());
                if (maxColumnWidth == null || columnWidth > maxColumnWidth) {
                    maxColumnWidthMap.put(cell.getColumnIndex(), columnWidth);
                    writeSheetHolder.getSheet().setColumnWidth(cell.getColumnIndex(), (columnWidth + 1) * 256);
                }
            }
        }
    }

    private Integer dataLength(List<WriteCellData<?>> cellDataList, Cell cell, Boolean isHead) {
        WriteCellData<?> cellData = cellDataList.get(0);
        if (isHead) {
            String s = cell.getStringCellValue();
            String ss = s.replaceAll(PATTERN, "");
            int length = s.length() * 2 + ss.length();
            WriteCellStyle writeCellStyle = cellData.getWriteCellStyle();
            if (writeCellStyle != null) {
                WriteFont writeFont = writeCellStyle.getWriteFont();
                if (writeFont != null) {
                    Short fontSize = writeFont.getFontHeightInPoints();
                    double factor = Math.ceil(fontSize.doubleValue() / DEFAULT_HEAD_FONT_SIZE.doubleValue());
                    return (int) Math.ceil(factor * length);
                }
            }
            return length;
        } else {
            CellDataTypeEnum type = cellData.getType();
            if (type == null) {
                return -1;
            } else {
                Double factor = null;
                WriteCellStyle writeCellStyle = cellData.getWriteCellStyle();
                if (writeCellStyle != null) {
                    WriteFont writeFont = writeCellStyle.getWriteFont();
                    if (writeFont != null) {
                        Short fontSize = writeFont.getFontHeightInPoints();
                        factor = Math.ceil(fontSize.doubleValue() / DEFAULT_CONTENT_FONT_SIZE.doubleValue());
                    }
                }
                switch (type) {
                    case STRING:
                        String sv = cellData.getStringValue();
                        // 换行符，数据需要提前解析好
                        int index = sv.indexOf("\n");
                        int stringLength = index != -1 ? sv.substring(0, index).getBytes().length + 1 : sv.getBytes().length + 1;
                        return factor != null ? (int) Math.ceil(factor * stringLength) : stringLength;
                    case BOOLEAN:
                        int booleanLength = cellData.getBooleanValue().toString().getBytes().length;
                        return factor != null ? (int) Math.ceil(factor * booleanLength) : booleanLength;
                    case NUMBER:
                        int numberLength = cellData.getNumberValue().toString().getBytes().length;
                        return factor != null ? (int) Math.ceil(factor * numberLength) : numberLength;
                    default:
                        return -1;
                }
            }
        }
    }
}
