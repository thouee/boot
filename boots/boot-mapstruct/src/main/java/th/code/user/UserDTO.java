package th.code.user;

import lombok.Data;

@Data
public class UserDTO {

    private Integer id;
    private String name;
    private String remark;
    private String createTime;
    private String updateTime;
}
