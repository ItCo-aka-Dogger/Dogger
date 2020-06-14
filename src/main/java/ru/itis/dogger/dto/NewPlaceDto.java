package ru.itis.dogger.dto;

import lombok.Data;

import java.util.List;

@Data
public class NewPlaceDto {
    private String name;
    private String photoPath;
    private String description;

    private Double coordinateX;
    private Double coordinateY;
    private String placeType;

    private List<String> amenities;
}
