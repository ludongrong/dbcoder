package io.github.ludongrong.dbcoder.controller.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BaseDto implements Serializable {

    static final private long serialVersionUID = 9040648716323037363L;

    public static class Page {

        @Getter
        private int limit;

        @Getter
        private int offset;

        @Getter
        private long total;

        public Page() {
            super();
        }

        public Page setLimit(int limit) {
            this.limit = limit;
            return this;
        }

        public Page setOffset(int offset) {
            this.offset = offset;
            return this;
        }

        public Page setTotal(long total) {
            this.total = total;
            return this;
        }
    }

    @Getter
    @Setter
    private String message;

    @Getter
    @Setter
    private Page page;

    public BaseDto() {
        super();
    }

    public BaseDto(String message) {
        super();
        this.message = message;
    }

    public Page setLimit(int limit) {
        this.page = new Page();
        this.page.setLimit(limit);
        return this.page;
    }

    public Page setOffset(int offset) {
        this.page = new Page();
        this.page.setOffset(offset);
        return this.page;
    }

    public Page setTotal(int total) {
        this.page = new Page();
        this.page.setTotal(total);
        return this.page;
    }
}
