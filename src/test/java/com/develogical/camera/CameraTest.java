package com.develogical.camera;

import org.junit.Test;

import static org.mockito.Mockito.*;

public class CameraTest {
    @Test
    public void switchingTheCameraOnPowersUpTheSensor() {
        Sensor sensor = mock(Sensor.class);
        new Camera(sensor, mock(MemoryCard.class)).powerOn();
        verify(sensor).powerUp();
    }

    @Test
    public void switchingTheCameraOffPowersDownTheSensor() {
        Sensor sensor = mock(Sensor.class);
        new Camera(sensor, mock(MemoryCard.class)).powerOff();
        verify(sensor).powerDown();
    }

    @Test
    public void pressingShutterWithPowerOnCopiesDataToSensor() {
        Sensor sensor = mock(Sensor.class);
        MemoryCard memoryCard = mock(MemoryCard.class);

        when(sensor.readData()).thenReturn(new byte[]{42});

        Camera camera = new Camera(sensor, memoryCard);
        camera.powerOn();
        camera.pressShutter();

        verify(memoryCard).write(eq(new byte[]{42}), any());
    }
}
