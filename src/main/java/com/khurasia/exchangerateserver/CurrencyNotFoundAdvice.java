package com.khurasia.exchangerateserver;

import com.khurasia.exchangerateserver.model.CurrencyNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class CurrencyNotFoundAdvice {

    @ResponseBody
    @ExceptionHandler(CurrencyNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String currencyNotFoundHandler(CurrencyNotFoundException ex) {
        return ex.getMessage() + ": " + ex.getIsoCurrencyCode();
    }
}
