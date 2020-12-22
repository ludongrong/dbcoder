package io.github.ludongrong.dbcoder.entity;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

public class Table3Bo extends Table3Do {

    /**
     * 
     */
    private static final long serialVersionUID = 9090301285273983608L;

    @Getter
    @Setter
    private List<Table4Bo> table4BoList;

    public Table3Bo() {
        super();
    }
}