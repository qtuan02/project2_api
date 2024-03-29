package com.project4.helper;

import java.util.Map;

public class CafeCheckDataJSON {
    public CafeCheckDataJSON(){}

    public static boolean checkDataRegister(Map<String, String> requestMap){
        if(requestMap.containsKey("firstName") && requestMap.containsKey("lastName")
                && requestMap.containsKey("phone") && requestMap.containsKey("email")
                && requestMap.containsKey("password")){
            return true;
        }
        return false;
    }

    public static boolean checkDataProduct(Map<String, String> requestMap){
        if(requestMap.containsKey("quantity") && requestMap.containsKey("name")
                && requestMap.containsKey("price") && requestMap.containsKey("description")
                && requestMap.containsKey("category_id")){
            return true;
        }
        return false;
    }
}
