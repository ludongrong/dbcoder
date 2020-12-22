package io.github.ludongrong.dbcoder.entity;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

public class Table4Do implements Serializable {

    private static final long serialVersionUID = 3731566019986479840L;

    @Getter
    @Setter
    private String column1;

    static final public String _COLUMN_1 = "Column_1";

    @Getter
    @Setter
    private Object column2;

    static final public String _COLUMN_2 = "Column_2";

    public Table4Do() {
        super();
    }
}