package io.github.ludongrong.dbcoder.controller.dto;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class IndexRequestDto implements Serializable {

    private static final long serialVersionUID = 4723021637236394970L;

    private String method;

    private String content;

    private String exportDbId;

    private List<String> dblinkFrom;

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getExportDbId() {
        return exportDbId;
    }

    public void setExportDbId(String exportDbId) {
        this.exportDbId = exportDbId;
    }

    public List<String> getDblinkFrom() {
        return dblinkFrom;
    }

    public void setDblinkFrom(List<String> dblinkFrom) {
        this.dblinkFrom = dblinkFrom;
    }
}