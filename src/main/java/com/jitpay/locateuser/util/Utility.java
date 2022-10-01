package com.jitpay.locateuser.util;

import com.jitpay.locateuser.entity.Location;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.Map;

public class Utility {
    public static final  String  SAVED_SUCCESSFULLY_MESSAGE = "Record Saved Successfully";

    public static final  String  USER_ID ="userId";
    public static final  String  CREATED_ON = "createdOn";
    public static final  String  EMAIL = "email";
    public static final String FIRST_NAME = "firstName";
    public static final String SECOND_NAME = "secondName";
    public static final String LOCATION = "location";
    public static final String LOCATIONS = "locations";
    public static final String LATITUDE = "latitude";
    public static final String LONGITUDE = "longitude";
    public static final String ERROR_MESSAGE = "errorMessage";
    //Messages
    public static final String INVALID_EMAIL = "Email is invalid or missing";
    public static final String INVALID_FIRST_NAME = "First name can not be null or empty";
    public static final String INVALID_SECOND_NAME = "Second name can not be null or empty";
    public static final String INVALID_USERID = "User Id can not be null or empty";
    public static final String INVALID_DATE = "Invalid date format, valid format is 2022-02-08T11:44:00.521";


    //Date format
    public static final String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS";

    public static Map<String, String> getLocationResponse(Location location){
        Map<String, String> locations = new LinkedHashMap<>();
        locations.put(Utility.LATITUDE, location.getLatitude());
        locations.put(Utility.LONGITUDE, location.getLongitude());

        return locations;
    }

    public static LocalDateTime convertStringToLocalDateTime(String date){
        DateTimeFormatter format = DateTimeFormatter.ofPattern(Utility.DATE_FORMAT);
        LocalDateTime localDateTime = null;
        try{
           localDateTime =  LocalDateTime.parse(date,format);
        }catch (Exception exception){
            System.out.println(exception.getMessage());
        }
        return localDateTime;
    }
}
