package io.github.ludongrong.dbcoder.provitor;

import lombok.Getter;
import lombok.Setter;

public class Key {

    @Getter
    @Setter
    private Column parentKey;

    @Getter
    @Setter
    private Column childKey;
}