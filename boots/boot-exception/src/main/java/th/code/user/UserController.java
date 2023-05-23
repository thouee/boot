package th.code.user;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import th.code.share.common.v2.R;
import th.code.share.common.v2.ResponseMode;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@RestController
@RequestMapping("/user")
public class UserController {

    private static final List<User> USERS;

    static {
        USERS = IntStream.range(1, 10).boxed().map(i -> {
            User user = new User();
            user.setUid(i);
            user.setName("user_" + i);
            return user;
        }).collect(Collectors.toList());
    }

    @GetMapping
    public R<List<User>> getAll() {
        return new R<>(USERS);
    }

    @GetMapping("/{id}")
    public R<User> getById(@PathVariable Integer id) {
        Map<Integer, User> map = USERS.stream().collect(Collectors.toMap(User::getUid, u -> u));
        User user = map.get(id);
        ResponseMode.USER_NOT_FOUNT.notNull(user);
        return new R<>(user);
    }
}
