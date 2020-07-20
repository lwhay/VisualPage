package com.example.visualpage.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResultVo<T> {

    private Integer code;
    private String status;
    private String message;
    private T data;

}
