package com.jitpay.locateuser.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.*;
import java.time.LocalDateTime;

@Entity

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    private String userId;
    private LocalDateTime createdOn;
    private String email;
    private String firstName;
    private String secondName;


}
