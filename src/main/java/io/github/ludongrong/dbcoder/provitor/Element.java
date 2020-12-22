package io.github.ludongrong.dbcoder.provitor;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

public class Element implements Serializable {

    @Getter
    @Setter
    private String id;

    @Getter
    @Setter
    private String name;
}