package com.jitpay.locateuser.service;

import com.jitpay.locateuser.exception.UserNotFoundException;
import com.jitpay.locateuser.dto.UserDto;
import com.jitpay.locateuser.dto.UserLocationDto;
import com.jitpay.locateuser.entity.Location;
import com.jitpay.locateuser.entity.User;
import com.jitpay.locateuser.repository.LocationRepository;
import com.jitpay.locateuser.repository.UserRepository;
import com.jitpay.locateuser.util.Utility;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.apache.commons.validator.routines.EmailValidator;

import java.time.LocalDateTime;
import java.util.*;

/**
 * The Service for User and Location resouces.
 * It updates user location, create or update user data,
 * provide latest location of user, return all locations of user for given dates
 *
 * @author Pawan Kumar
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    LocationRepository locationRepository;


    /**
     * Method to save given user location
     *
     * @param userLocationDto it contains info for userId and location that need to be saved
     * @return Record Saved Successfully message in case of success, throw UserNotFound Exception if user is not found.
     */
    @Override
    public String saveUserLocation(UserLocationDto userLocationDto) {
        Optional<User> user = userRepository.findById(userLocationDto.getUserId());
        if(!user.isPresent()){
            throw new UserNotFoundException("User with id " + userLocationDto.getUserId()+ " does not exists");
        }

        Location location = new Location(userLocationDto.getLocation().getLatitude(), userLocationDto.getLocation().getLongitude(), userLocationDto.getCreatedOn(),user.get());
        locationRepository.save(location);
        return Utility.SAVED_SUCCESSFULLY_MESSAGE;
    }

    /**
     * Method to take user info (id, createdOn, email, firstName, lastName)
     * save new user if it does not, else update user. all params need to be given for update as well(not partial)
     *
     * @param userDto a JSON representation of UserDta.
     * @return saved or updated User Object
     */
    @Override
    public User saveUser(UserDto userDto) {
        User user = new User(userDto.getUserId(), userDto.getCreatedOn(), userDto.getEmail(), userDto.getFirstName(), userDto.getSecondName());
        return userRepository.save(user);
    }

    /**
     * Method to partially update user data. It update info that is given in UserData param.
     *
     * @param userId to fetch user for which we need to update data
     * @param userDto userData to be updated for given userId
     * @return Updated user Object in case of success, throw UserNotFound Exception if user is not found.
     */
    @Override
    public User updateUser(String userId, Map<String, Object> userDto) {
       Optional<User> userExists = userRepository.findById(userId);
        if(!userExists.isPresent()){
            throw new UserNotFoundException("User with id " + userId+ " does not exists");
        }

        User user = userExists.get();
        if(userDto!=null){

            if(userDto.containsKey(Utility.CREATED_ON)){
                LocalDateTime localDateTime = Utility.convertStringToLocalDateTime(String.valueOf(userDto.get(Utility.CREATED_ON)));
                if(localDateTime == null){
                    throw new IllegalArgumentException(Utility.INVALID_DATE);
                }
                user.setCreatedOn(localDateTime);

            }if(userDto.containsKey(Utility.EMAIL)){
                if(!EmailValidator.getInstance().isValid(String.valueOf(userDto.get(Utility.EMAIL)))){
                    throw new IllegalArgumentException(Utility.INVALID_EMAIL);
                }
                user.setEmail(String.valueOf(userDto.get(Utility.EMAIL)));
            }if(userDto.containsKey(Utility.FIRST_NAME) && userDto.get(Utility.FIRST_NAME).toString().trim().length()>0){
                user.setFirstName(String.valueOf(userDto.get(Utility.FIRST_NAME)));
            }if(userDto.containsKey(Utility.SECOND_NAME)  && userDto.get(Utility.SECOND_NAME).toString().trim().length()>0){
                user.setSecondName(String.valueOf(userDto.get(Utility.SECOND_NAME)));
            }


            user = userRepository.save(user);
        }

        return user;

    }


    /**
     * Method to fetch user with latest location that user was on.
     *
     *
     * @param userId userId for user for which info need to be fetched.
     * @return user info i.e userId, createdOn, email, firstName ,
     * lastName and location with latitude and longitude as param.
     * return all these info in form of Map.
     * in case user does not exists for given id it throws userNotFound Exception
     */
    @Override
    public Map<String, Object> fetchUserByIdWithLocation(String userId) {
        Map<String, Object> result = null;
        Optional<User> userExists = userRepository.findById(userId);
        if(!userExists.isPresent()){
            throw new UserNotFoundException("User with id " + userId+ " does not exists");
        }
        User user = userExists.get();
        result = new LinkedHashMap<>();

        result.put(Utility.USER_ID, user.getUserId());
        result.put(Utility.CREATED_ON, user.getCreatedOn());
        result.put(Utility.EMAIL, user.getEmail());
        result.put(Utility.FIRST_NAME, user.getFirstName());
        result.put(Utility.SECOND_NAME, user.getSecondName());

        Location location = locationRepository.findTop1ByUserOrderByCreatedOnDesc(user);
        if(location!=null){
            Map<String, String> locations = Utility.getLocationResponse(location);
            result.put(Utility.LOCATION, locations);
        }

        return result;
    }

    /**
     * Method to fetch user location from given data range
     *
     * @param userId userId of user for which data need be fetched
     * @param from from which date data need to be fetched.
     * @param to to which date data need to be fetched
     * @return Map with userId and list of location for given date range for given user.
     * in case user not found for given userId it throws UserNotFound Exception
     *
     */
    @Override
    public Map<String, Object> fetchUserByIdWithLocationBetween(String userId, LocalDateTime from, LocalDateTime to) {
        Map<String, Object> result = null;
        Optional<User> userExists = userRepository.findById(userId);
        if(!userExists.isPresent()){
            throw new UserNotFoundException("User with id " + userId+ " does not exists");
        }
        User user = userExists.get();
        result = new LinkedHashMap<>();
        result.put(Utility.USER_ID, user.getUserId());
        //Finding Location List between date range for given user
        List<Location> locationList = locationRepository.findByUserAndCreatedOnBetween(user, from, to);
        List<Map<String, Object>> locations = new ArrayList<>();

        //convert each location into format described
        for(Location location: locationList){
            Map<String, Object> wholeLocation = new LinkedHashMap<>();
            wholeLocation.put(Utility.CREATED_ON, location.getCreatedOn());
            Map<String, String> locationObject = Utility.getLocationResponse(location);
            wholeLocation.put(Utility.LOCATION, locationObject);

            locations.add(wholeLocation);
        }

        result.put(Utility.LOCATIONS, locations);
        return result;





    }


}
