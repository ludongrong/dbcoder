package io.github.ludongrong.dbcoder.entity;

import lombok.Getter;
import lombok.Setter;

public class Table4Bo extends Table4Do {

    /**
     * 
     */
    private static final long serialVersionUID = -712163438184391866L;

    @Getter
    @Setter
    private Table3Bo table3Bo;

    public Table4Bo() {
        super();
    }
}