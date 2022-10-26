package com.jcdecaux.kata.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Zone {

    private Float minLat;

    private Float minLon;

    private Float maxLat;

    private Float maxLon;
}
