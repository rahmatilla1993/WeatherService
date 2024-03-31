package com.company.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserDetailsDto {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private List<String> citiesNames;
    private String status;
}
