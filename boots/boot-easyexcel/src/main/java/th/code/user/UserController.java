package th.code.user;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import th.code.share.util.ExcelExportUtils;

import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@RestController
@RequestMapping("/user")
public class UserController {

    @GetMapping("/export")
    public void export(HttpServletResponse response) throws Exception {
        List<UserExport> users = IntStream.rangeClosed(1, 100)
                .boxed()
                .map(i -> {
                    UserExport user = new UserExport();
                    user.setName("用户_" + i);
                    user.setAge(((int) (Math.random() * 30 + 20)) + "");
                    user.setBirthday(LocalDateTime.now());
                    user.setUsername("yonghu_" + i);
                    user.setXxxHHHH("test");
                    if (i % 17 == 0) {
                        user.setJob(null);
                        user.setIsFired(true);
                    } else {
                        user.setJob("员工");
                        user.setIsFired(false);
                    }
                    if (i == 1 || i == 2) {
                        user.setJob("领\n导");
                    }
                    return user;
                }).collect(Collectors.toList());

        String filename = "用户数据-" + System.currentTimeMillis();
        ExcelExportUtils.export(response, filename, "用户数据", UserExport.class, users, true);
    }

    /*
        分开使用，可以配合 mybatis 流式查询导出大量数据

        service:
        ExcelWriter excelWriter = ExcelExportUtils.getExcelWriter(response, filename, UserExport.class);
        WriteSheet writeSheet = EasyExcel.writerSheet("用户数据").build();
        mapper.selectStream4Export(resultContext -> {
            UserExport export = resultContext.getResultObject();
            excelWriter.write(Collections.singletonList(export), writeSheet)
        })
        excelWriter.finish();


        mapper.java:
        void selectStream4Export(ResultHandler<UserExport> resultHandler);

        mapper.xml
        <select id="selectStream4Export" resultType="xxx" resultSetType="FORWARD_ONLY" fetchSize="-2147483648">
        </select>
     */
}
