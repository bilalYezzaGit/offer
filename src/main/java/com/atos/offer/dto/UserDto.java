package com.atos.offer.dto;

import com.atos.offer.enums.Gender;
import com.atos.offer.validation.age.AgeConstraint;
import com.atos.offer.validation.country.CountryConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;

/**
 * User data transfer object. A user business object used by the controller.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Valid
public class UserDto {

    @NotBlank(message = "Invalid username: username must be filled in")
    private String userName;

    @NotNull(message = "Invalid birthDate: birthDate must be filled in")
    @AgeConstraint
    private LocalDate birthDate;

    @CountryConstraint
    private String country;

    @Pattern(regexp = "^(?:(?:\\+|00)33|0)\\s*[1-9](?:[\\s.-]*\\d{2}){4}$", message = "Invalid number: must be a valid french number")
    private String phoneNumber;

    private Gender gender;
}
