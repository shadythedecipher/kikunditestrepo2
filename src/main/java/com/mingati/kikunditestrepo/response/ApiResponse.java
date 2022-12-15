package com.mingati.kikunditestrepo.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class ApiResponse<T> {

    private boolean hasError;
    private T responseObject;
    private String successMessage;
    private List<String> errors;

}
