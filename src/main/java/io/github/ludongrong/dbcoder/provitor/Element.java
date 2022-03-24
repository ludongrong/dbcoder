package io.github.ludongrong.dbcoder.provitor;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

public class Element implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = -4708191193480564231L;

	@Getter
    @Setter
    private String id;

    @Getter
    @Setter
    private String name;
}