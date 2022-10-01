package com.jitpay.locateuser.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jitpay.locateuser.util.Utility;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@ToString
public class UserLocationDto {

    @NotNull(message = Utility.INVALID_USERID)
    @NotEmpty(message = Utility.INVALID_USERID)
    private String userId;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @JsonFormat(pattern = Utility.DATE_FORMAT)
    private LocalDateTime createdOn;
    private LocationDto location;
}
