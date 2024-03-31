package com.company.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserReadDto {
    private Long id;

    private String firstName;

    private String lastName;

    private String email;
}
