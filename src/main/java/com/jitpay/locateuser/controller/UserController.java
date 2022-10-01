package com.jitpay.locateuser.controller;

import com.jitpay.locateuser.dto.UserDto;
import com.jitpay.locateuser.dto.UserLocationDto;
import com.jitpay.locateuser.entity.User;
import com.jitpay.locateuser.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * The controller for User and Location resouces.
 * It updates user location, create or update user data,
 * provide latest location of user, return all locations of user for given dates
 *
 * @author Pawan Kumar
 */

@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    UserService userService;

    /**
     * Endpoint to take user info (id, createdOn, email, firstName, lastName)
     * save new user if it does not, else update user. all params need to be given for update as well(not partial)
     *
     * Returns following status code
     * 201: user created or updated successfully
     * 400: if it is bad request i.e some param is not provided in correct way.
     * 500: any other exception occurs.
     *
     *
     * @param userDto a JSON representation of UserDta.
     * @return ResponseEntity with saved or updated User and status code 201 in case of success, other wise errorMessage with reason of failure.
     */
    @PostMapping("")
    public ResponseEntity<?> saveUser(@RequestBody @Valid UserDto userDto){
        return new ResponseEntity<User>(userService.saveUser(userDto), HttpStatus.CREATED);
    }

    /**
     * Endpoint to partially update user data. It update info that is given in UserData param.
     *
     * Returns following status code
     * 200: user is updated successfully
     * 400: if it is bad request i.e some param is not provided in correct way.
     * 404: if user with given userId is not found
     * 500: any other exception occurs.
     *
     * @param userId to fetch user for which we need to update data
     * @param userDto userData to be updated for given userId
     * @return Response Entity with updated User in case of success, other wise errorMessage with reason of failure
     */
    @PatchMapping("")
    public ResponseEntity<?> updateUser(@RequestParam(value = "userId", required = true) String userId, @RequestBody Map<String,Object> userDto){
        return new ResponseEntity<User>(userService.updateUser(userId,userDto), HttpStatus.OK);
    }


    /**
     * Endpoint to save given user location
     *
     * Returns following status code
     * 201: Location is saved successfully
     * 400: if it is bad request i.e some param is not provided in correct way.
     * 404: if user with given userId is not found
     * 500: any other exception occurs.
     *
     * @param userLocationDto it contains info for userId and location that need to be saved
     * @return Response Entity with message of record saved successfully.
     */
    @PostMapping("/location")
    public ResponseEntity<?> saveLocation(@RequestBody @Valid UserLocationDto userLocationDto){
        return new ResponseEntity<String>(userService.saveUserLocation(userLocationDto), HttpStatus.CREATED);

    }


    /**
     * Endpoint to fetch user with latest location that user was on.
     *
     * Returns following status code
     * 200: if data is fetched successfully.
     * 404: if user with given userId is not found
     * 500: any other exception occurs.
     *
     * @param userId userId for user for which info need to be fetched.
     * @return user info i.e userId, createdOn, email, firstName ,
     * lastName and location with latitude and longitude as param.
     * return all these info in form of Map.
     */
    @GetMapping("{userId}/location")
    public ResponseEntity<?> findUserByIdWithLocation(@PathVariable  String userId){
        return new ResponseEntity<Map<String, Object>>( userService.fetchUserByIdWithLocation(userId), HttpStatus.OK);
    }


    /**
     * Endpoint to fetch user location from given data range
     *
     * Returns following status code
     * 200: if data is fetched successfully.
     * 404: if user with given userId is not found
     * 500: any other exception occurs.
     *
     * @param userId userId of user for which data need be fetched
     * @param fromDate from which date data need to be fetched.
     * @param toDate to which date data need to be fetched
     * @return Response Entity with Map containing userId and location list.
     *
     */
    @GetMapping("{userId}/location/between")
    public ResponseEntity<?> findUserByIdWithLocationBetween(@PathVariable  String userId, @RequestParam("fromDate")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fromDate, @RequestParam("toDate") @DateTimeFormat(iso =  DateTimeFormat.ISO.DATE_TIME) LocalDateTime toDate){
        return new ResponseEntity<Map<String, Object>>( userService.fetchUserByIdWithLocationBetween(userId, fromDate, toDate), HttpStatus.OK);
    }


}
