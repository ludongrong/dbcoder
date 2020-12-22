package io.github.ludongrong.dbcoder.provitor;

import lombok.Getter;
import lombok.Setter;

public class Column extends Element {

    @Getter
    @Setter
    private String dataType;

    @Getter
    @Setter
    private int length;

    @Getter
    @Setter
    private int precision;

    @Getter
    @Setter
    private boolean mandatory;

    @Getter
    @Setter
    private boolean primaryKey;

    @Getter
    @Setter
    private String javaType;
    
    @Getter
    @Setter
    private String javaName;

    @Getter
    @Setter
    private Table table;
}