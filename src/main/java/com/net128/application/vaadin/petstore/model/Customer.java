package com.net128.application.vaadin.petstore.model;

import lombok.*;

import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Entity
@ToString
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class Customer extends Identifiable {
    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
    @NotBlank
    private String address;
    @NotBlank
    private String zipCode;
    @NotBlank
    private String city;
    @NotBlank
    private String country;
    @NotBlank
    @Pattern(regexp = "^\\+\\d{1,3}\\s\\d{1,14}(\\s\\d{1,13})?",
        message = "Required E164 format!\nValid examples:\n+1 1234 567890\n+44 1234567890")
    private String phone;
}
