package com.jitpay.locateuser.dto;

import com.jitpay.locateuser.util.Utility;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@ToString
public class LocationDto {

    private String latitude;

    private String longitude;
}
