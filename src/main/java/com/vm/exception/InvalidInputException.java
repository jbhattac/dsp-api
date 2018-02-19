package com.vm.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "The argument is invalid.")
public class InvalidInputException extends Exception
{

    static final long serialVersionUID = -3387516993334229948L;


    public InvalidInputException(String message)
    {
        super(message);
    }

}
