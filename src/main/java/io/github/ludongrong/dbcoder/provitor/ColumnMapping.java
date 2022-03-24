package io.github.ludongrong.dbcoder.provitor;

import lombok.Getter;
import lombok.Setter;

public class ColumnMapping {

    @Getter
    @Setter
    private Column self;

    @Getter
    @Setter
    private Column mapping;
}