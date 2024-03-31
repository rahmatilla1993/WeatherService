package com.company.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class LocationDto {
    private Long id;
    private String name;
    private double latitude;
    private double longitude;
    private List<String> subscribeUserEmails;
}
