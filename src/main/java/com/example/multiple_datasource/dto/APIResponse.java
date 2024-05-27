package com.example.multiple_datasource.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;



@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class APIResponse {

    private String status;
    private String message;
    private Object data;

}
