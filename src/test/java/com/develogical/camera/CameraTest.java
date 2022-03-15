package com.develogical.camera;

import org.junit.Test;
import org.mockito.ArgumentCaptor;

import static org.mockito.Mockito.*;

public class CameraTest {

    private final Sensor sensor = mock(Sensor.class);
    private final Camera underTest = new Camera(sensor);

    @Test
    public void switchingTheCameraOnPowersUpTheSensor() {

        underTest.powerOn();

        verify(sensor).powerUp();
    }

    @Test
    public void switchingTheCameraOffPowersDownTheSensor() {
        underTest.powerOff();

        verify(sensor).powerDown();
    }
}
