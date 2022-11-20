package org.kuspakov.springaop.util;

import lombok.Data;

import java.util.Collection;

@Data
public class CustomResponse<T> {

    private int code;

    private String message;

    public CustomResponse(Collection<T> response, CustomStatus status) {
        this.code = status.getCode();
        this.message = status.getMessage();
        this.responseList = response;
    }

    private Collection<T> responseList;


}
