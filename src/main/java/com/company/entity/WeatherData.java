package com.company.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Entity
@Table(name = "weather_data")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
@Builder
public class WeatherData extends Auditable {

    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private LocalDate date;

    private double temperature;
    private double humidity;
    private double pressure;
    private double windSpeed;
    private double precipitation;

    @ManyToOne
    @JoinColumn(name = "location_id", nullable = false)
    private Location location;
}
