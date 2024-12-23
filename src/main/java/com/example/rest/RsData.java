package com.example.rest;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;

@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
@Getter
public class RsData {
    private String resultCode;
    private String msg;
    private Object data;

    public RsData(String resultCode, String msg) {
        this(resultCode, msg, null);
    }
}