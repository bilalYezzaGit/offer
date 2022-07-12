package com.atos.offer.dto;

import com.atos.offer.enums.Gender;
import com.atos.offer.validation.AgeConstraint;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Valid
public class UserDto {

    private String userName;

    @AgeConstraint
    private LocalDate birthDate;

    private String country;

    @Pattern(regexp = "^(?:(?:\\+|00)33|0)\\s*[1-9](?:[\\s.-]*\\d{2}){4}$")
    private String phoneNumber;

    private Gender gender;
}
