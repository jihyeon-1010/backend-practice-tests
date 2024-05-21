package com.example.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Weather {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDate baseDate;

    @Column(nullable = false)
    private LocalTime baseTime;

    @Column(nullable = false)
    private int nx;

    @Column(nullable = false)
    private int ny;

    @Column(nullable = false)
    private LocalDate fcstDate;

    @Column(nullable = false)
    private LocalTime fcstTime;

    @Column(nullable = false, length = 10)
    private String category;

    @Column(nullable = false, length = 10)
    private String fcstValue;

    public Weather(LocalDate baseDate, LocalTime baseTime, int nx, int ny, LocalDate fcstDate, LocalTime fcstTime, String category, String fcstValue) {
        this.baseDate = baseDate;
        this.baseTime = baseTime;
        this.nx = nx;
        this.ny = ny;
        this.fcstDate = fcstDate;
        this.fcstTime = fcstTime;
        this.category = category;
        this.fcstValue = fcstValue;
    }

}
