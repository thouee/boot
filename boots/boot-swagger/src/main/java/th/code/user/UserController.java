package th.code.user;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "用户")
@RestController
@RequestMapping("/user")
public class UserController {

    @ApiOperation(value = "查询指定用户信息")
    @GetMapping("/{userId}")
    public UserVo getInfo(@PathVariable Long userId) {
        UserVo userVo = new UserVo();
        if (userId != null) {
            userVo.setUserId(userId);
            userVo.setUsername("admin");
            userVo.setPassword("root");
        }
        return userVo;
    }
}
