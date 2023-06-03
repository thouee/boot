package me.th.serializer.controller;

import lombok.Getter;
import lombok.Setter;
import me.th.serializer.dict.Dict;

@Getter
@Setter
public class User {

    private Integer id;

    @Dict(key = "test")
    private Integer type;
}
