package me.th.share.excel;

import com.alibaba.excel.write.style.row.AbstractRowHeightStyleStrategy;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;

import java.util.Iterator;

/**
 * 自适应行高样式策略
 */
public class AutoRowHeightStyleStrategy extends AbstractRowHeightStyleStrategy {

    /**
     * 默认行高，这个数字 / 20 就是 excel 中单元格的行高
     */
    private static final Integer DEFAULT_HEIGHT = 500;

    @Override
    protected void setHeadColumnHeight(Row row, int i) {
        Iterator<Cell> cellIterator = row.cellIterator();
        if (!cellIterator.hasNext()) {
            return;
        }

        int maxHeight = 1;
        while (cellIterator.hasNext()) {
            Cell cell = cellIterator.next();
            if (CellType.STRING.equals(cell.getCellType())) {
                String scv = cell.getStringCellValue();
                if (scv.contains("\n")) {
                    int length = scv.split("\n").length;
                    maxHeight = Math.max(maxHeight, length);
                }
            }
        }

        row.setHeight((short) (maxHeight * DEFAULT_HEIGHT));
    }

    @Override
    protected void setContentColumnHeight(Row row, int i) {
        Iterator<Cell> cellIterator = row.cellIterator();
        if (!cellIterator.hasNext()) {
            return;
        }

        int maxHeight = 1;
        while (cellIterator.hasNext()) {
            Cell cell = cellIterator.next();
            if (CellType.STRING.equals(cell.getCellType())) {
                String scv = cell.getStringCellValue();
                if (scv.contains("\n")) {
                    int length = scv.split("\n").length;
                    maxHeight = Math.max(maxHeight, length);
                }
            }
        }

        row.setHeight((short) (maxHeight * DEFAULT_HEIGHT));
    }
}
