package io.github.ludongrong.dbcoder.provitor;

import lombok.Getter;
import lombok.Setter;

public class Reference {

    @Getter
    @Setter
    private Table referenceTable;

    @Getter
    @Setter
    private Column[] joinColumns;
}