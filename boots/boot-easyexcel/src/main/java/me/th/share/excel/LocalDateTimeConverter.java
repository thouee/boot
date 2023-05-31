package me.th.share.excel;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.data.ReadCellData;
import com.alibaba.excel.metadata.data.WriteCellData;
import com.alibaba.excel.metadata.property.DateTimeFormatProperty;
import com.alibaba.excel.metadata.property.ExcelContentProperty;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LocalDateTimeConverter implements Converter<LocalDateTime> {

    private static final String DEFAULT_DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    @Override
    public Class<?> supportJavaTypeKey() {
        return LocalDateTime.class;
    }

    @Override
    public CellDataTypeEnum supportExcelTypeKey() {
        return CellDataTypeEnum.STRING;
    }

    @Override
    public LocalDateTime convertToJavaData(ReadCellData<?> cellData, ExcelContentProperty contentProperty,
                                           GlobalConfiguration globalConfiguration) throws Exception {
        DateTimeFormatProperty dateTimeFormatProperty = contentProperty.getDateTimeFormatProperty();
        if (dateTimeFormatProperty != null) {
            return LocalDateTime.parse(cellData.getStringValue(), DateTimeFormatter.ofPattern(dateTimeFormatProperty.getFormat()));
        }
        return LocalDateTime.parse(cellData.getStringValue(), DateTimeFormatter.ofPattern(DEFAULT_DATETIME_FORMAT));
    }

    @Override
    public WriteCellData<?> convertToExcelData(LocalDateTime value, ExcelContentProperty contentProperty,
                                               GlobalConfiguration globalConfiguration) throws Exception {
        DateTimeFormatProperty dateTimeFormatProperty = contentProperty.getDateTimeFormatProperty();
        if (dateTimeFormatProperty != null) {
            return new WriteCellData<>(value.format(DateTimeFormatter.ofPattern(dateTimeFormatProperty.getFormat())));
        }
        return new WriteCellData<>(value.format(DateTimeFormatter.ofPattern(DEFAULT_DATETIME_FORMAT)));
    }
}
