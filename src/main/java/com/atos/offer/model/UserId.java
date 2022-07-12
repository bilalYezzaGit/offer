package com.atos.offer.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.time.LocalDate;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserId implements Serializable {

    @Column
    @NotEmpty
    private String userName;

    @Column
    private LocalDate birthDate;

    @Column
    private String country;
}
