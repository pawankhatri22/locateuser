package com.jitpay.locateuser.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jitpay.locateuser.util.Utility;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserDto {
    @NotNull(message = Utility.INVALID_USERID)
    @NotEmpty(message = Utility.INVALID_USERID)
    private String userId;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @JsonFormat(pattern = Utility.DATE_FORMAT)
    private LocalDateTime createdOn;

    @Email(message = Utility.INVALID_EMAIL)
    private String email;
    @NotNull(message = Utility.INVALID_FIRST_NAME)
    @NotEmpty(message = Utility.INVALID_FIRST_NAME)
    private String firstName;

    @NotNull(message = Utility.INVALID_SECOND_NAME)
    @NotEmpty(message = Utility.INVALID_SECOND_NAME)
    private String secondName;
}
