package com.jcdecaux.kata.service;

import com.jcdecaux.kata.model.Zone;
import org.springframework.util.ResourceUtils;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

public class ServicePOIs {


    public long carbonBasaEs(String nameCsv, Zone zone) throws IOException {

        File file = ResourceUtils.getFile(ResourceUtils.CLASSPATH_URL_PREFIX + nameCsv);

        Zone finalZone = checkDefaultValueZone(zone);

        /*
        -Skip first line
        -read file in stream like this all line in csv don't loaded in memory in same time
         */
        long count = Files.lines(Paths.get(file.getPath()))
                .skip(1)
                .parallel()
                .map(s -> s.split(" "))
                .map(this::checkLine)
                .filter(line -> Float.parseFloat(line[1]) >= finalZone.getMinLat() && Float.parseFloat(line[1]) <= finalZone.getMaxLat() && Float.parseFloat(line[2]) >= finalZone.getMinLon() && Float.parseFloat(line[2]) <= finalZone.getMaxLon())
                .count();

        return count;
    }

    /*
    check size line
     */
    private String[] checkLine(String[] line) {

        if (line.length == 3) {
            return line;
        }
        throw new RuntimeException(String.format("Error line lenght : %s", Arrays.toString(line)));

    }

    /*
    set min max if null like example in kata
     */
    private Zone checkDefaultValueZone(Zone zone) {
        if (  zone.getMinLat() == null || zone.getMinLat().isNaN()) {
            zone.setMinLat(-90f);
        }
        if (  zone.getMinLon() == null || zone.getMinLon().isNaN()) {
            zone.setMinLon(-180f);
        }
        if (   zone.getMaxLat() == null || zone.getMaxLat().isNaN()) {
            zone.setMaxLat(90f);
        }
        if (  zone.getMaxLon() == null || zone.getMaxLon().isNaN()) {
            zone.setMaxLon(180f);
        }
        return zone;
    }
}

