package me.th.user;

import lombok.Data;

import java.io.Serializable;

@Data
public class User implements Serializable {

    private static final long serialVersionUID = 1744298913673480169L;

    private Integer uid;
    private String name;
}
