package th.code.user;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import th.code.share.excel.LocalDateTimeConverter;

import java.time.LocalDateTime;

@Getter
@Setter
@EqualsAndHashCode
// @ContentStyle(dataFormat = 49)
// @ContentFontStyle(fontHeightInPoints = 24)
public class UserExport {

    @ExcelProperty("姓名")
    private String name;

    @ExcelProperty("年龄")
    private String age;

    @ExcelProperty("用户名")
    private String username;

    @ExcelProperty("职位")
    private String job;

    @ExcelProperty(value = "生日", converter = LocalDateTimeConverter.class)
    private LocalDateTime birthday;

    @ExcelIgnore
    private String ignore;

    private String xxxHHHH;

    @ExcelProperty("是否离职")
    private Boolean isFired;
}
