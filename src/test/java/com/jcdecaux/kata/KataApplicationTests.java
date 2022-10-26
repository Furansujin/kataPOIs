package com.jcdecaux.kata;

import com.google.common.base.Stopwatch;
import com.jcdecaux.kata.model.Zone;
import com.jcdecaux.kata.service.ServicePOIs;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;


class KataApplicationTests {

    @Test
    void countPOIsInZone() throws IOException {

        ServicePOIs servicePOIs = new ServicePOIs();
        Zone zone = Zone.builder().minLat(6.5f).minLon(-7f).maxLat(90f).maxLon(180f).build();

        Stopwatch stopwatch = Stopwatch.createStarted();

        long i = servicePOIs.carbonBasaEs("example.csv", zone);

        stopwatch.stop();

        Assert.assertEquals(2l, i);

        System.out.println("Time elapsed: "+ stopwatch.elapsed(TimeUnit.MILLISECONDS));
    }

    @Test
    void checkCsvError() throws IOException {
        ServicePOIs servicePOIs = new ServicePOIs();
        Zone zone = Zone.builder().minLat(6.5f).minLon(-7f).maxLat(90f).maxLon(180f).build();

        Exception exception = assertThrows(RuntimeException.class, () ->
                servicePOIs.carbonBasaEs("exampleError.csv", zone));

        assertTrue(exception.getMessage().contains("Error line lenght"));
    }

    @Test
    void bigCsvTest() throws IOException {

        ServicePOIs servicePOIs = new ServicePOIs();
        Zone zone = Zone.builder().minLat(6.5f).minLon(-7f).maxLat(90f).maxLon(180f).build();

        Stopwatch stopwatch = Stopwatch.createStarted();

        long i = servicePOIs.carbonBasaEs("exampleBig.csv", zone);

        stopwatch.stop();

        Assert.assertEquals(25250l, i);


        System.out.println("Time elapsed: "+ stopwatch.elapsed(TimeUnit.MILLISECONDS));
    }

    @Test
    void countPOIsInZoneWithoutMax() throws IOException {

        ServicePOIs servicePOIs = new ServicePOIs();
        Zone zone = Zone.builder().minLat(6.5f).minLon(-7f).build();


        long i = servicePOIs.carbonBasaEs("example.csv", zone);


        Assert.assertEquals(2l, i);
    }

}
