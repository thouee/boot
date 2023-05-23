package th.code.user;

import lombok.Data;

import java.util.Date;

@Data
public class User {

    private Integer id;
    private String username;
    private Integer age;
    private String remark;
    private Date createTime;
    private Date updateTime;
}
