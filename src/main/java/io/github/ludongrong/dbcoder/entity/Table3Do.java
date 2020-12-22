package io.github.ludongrong.dbcoder.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

public class Table3Do implements Serializable {
    
    private static final long serialVersionUID = -804298328559807113L;

    @Getter
    @Setter
    private BigDecimal column1;
    
    static final public String _COLUMN_1 = "Column_1";
    
    @Getter
    @Setter
    private String column2;
    
    static final public String _COLUMN_2 = "Column_2";
    
    @Getter
    @Setter
    private String column3;
    
    static final public String _COLUMN_3 = "Column_3";
    
    @Getter
    @Setter
    private Integer column4;
    
    static final public String _COLUMN_4 = "Column_4";
	
	public Table3Do() {
		super();
	}
}