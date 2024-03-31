package com.company.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class WeatherDto {

    private String date;
    private double temperature;
    private double humidity;
    private double pressure;
    private double windSpeed;
    private double precipitation;
    private String location;
}
