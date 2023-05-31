package me.th.user;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.Mappings;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserConvert {

    @Mappings({
            @Mapping(target = "username", source = "name"),
            @Mapping(target = "createTime", dateFormat = "yyyy-MM-dd"),
            @Mapping(target = "updateTime", dateFormat = "yyyy-MM-dd"),
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "age", ignore = true),
            // userDTO#remark == null 时使用 defaultValue
            @Mapping(target = "remark", defaultValue = "默认值")
    })
    User toUser(UserDTO userDTO);

    @Mappings({
            @Mapping(target = "name", source = "username"),
            @Mapping(target = "createTime", dateFormat = "yyyy-MM-dd"),
            @Mapping(target = "updateTime", dateFormat = "yyyy-MM-dd"),
            @Mapping(target = "id", ignore = true)
    })
    UserDTO toUserDto(User user);

    // https://blog.csdn.net/qq_44732146/article/details/119968376
}
