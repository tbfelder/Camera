package com.develogical.camera;

import org.junit.Test;
import org.mockito.ArgumentCaptor;

import static org.mockito.Mockito.*;

public class CameraTest {
    @Test
    public void switchingTheCameraOnPowersUpTheSensor() {
        Sensor sensor = mock(Sensor.class);
        new Camera(sensor).powerOn();
        verify(sensor).powerUp();
    }

    @Test
    public void switchingTheCameraOffPowersDownTheSensor() {
        Sensor sensor = mock(Sensor.class);
        new Camera(sensor).powerOff();
        verify(sensor).powerDown();
    }
}
