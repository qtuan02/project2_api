package com.project4.helper;

import com.project4.entity.UserEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CafeUtil {

    public CafeUtil(){}

    public static ResponseEntity<String> getResponseEntity(String responseMessage, HttpStatus httpStatus){
        return new ResponseEntity<String>("{\"message\":\"" + responseMessage + "\"}", httpStatus);
    }

    public static <T> ResponseEntity<List<T>> getResponseEntityList(ArrayList<T> list, HttpStatus httpStatus){
        return new ResponseEntity<>(list, httpStatus);
    }

    public static <T> ResponseEntity<T> getResponseEntityObject(T object, HttpStatus httpStatus){
        return new ResponseEntity<>(object, httpStatus);
    }
}
