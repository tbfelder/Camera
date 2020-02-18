package com.develogical.camera;

import org.junit.Test;
import org.mockito.ArgumentCaptor;

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
        Camera camera = new Camera(sensor, mock(MemoryCard.class));
        camera.powerOn();
        camera.powerOff();
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

    @Test
    public void pressingShutterWithPowerOffDoesNothing() {
        Sensor sensor = mock(Sensor.class);
        MemoryCard memoryCard = mock(MemoryCard.class);

        Camera camera = new Camera(sensor, memoryCard);
        camera.powerOff();
        camera.pressShutter();

        verifyNoMoreInteractions(memoryCard);
        verifyNoMoreInteractions(sensor);
    }

    @Test
    public void ifDataIsBeingWrittenSwitchingCameraOffDoesNotPowerDownSensor() {
        Sensor sensor = mock(Sensor.class);
        MemoryCard memoryCard = mock(MemoryCard.class);

        when(sensor.readData()).thenReturn(new byte[]{42});

        Camera camera = new Camera(sensor, memoryCard);
        camera.powerOn();
        camera.pressShutter();
        camera.powerOff();

        verify(sensor, never()).powerDown();
    }

    @Test
    public void sensorIsNotSwitchedOffAfterDataFinishedWritingIfCameraOn() {
        Sensor sensor = mock(Sensor.class);
        MemoryCard memoryCard = mock(MemoryCard.class);

        when(sensor.readData()).thenReturn(new byte[]{42});

        Camera camera = new Camera(sensor, memoryCard);
        camera.powerOn();
        camera.pressShutter();

        ArgumentCaptor<WriteCompleteListener> captor = ArgumentCaptor.forClass(WriteCompleteListener.class);

        verify(memoryCard).write(eq(new byte[]{42}), captor.capture());
        verify(sensor, never()).powerDown();

        captor.getValue().writeComplete();
        verify(sensor, never()).powerDown();
    }

    @Test
    public void sensorIsSwitchedOffAfterDataFinishedWritingIfCameraOff() {
        Sensor sensor = mock(Sensor.class);
        MemoryCard memoryCard = mock(MemoryCard.class);

        when(sensor.readData()).thenReturn(new byte[]{42});

        Camera camera = new Camera(sensor, memoryCard);
        camera.powerOn();
        camera.pressShutter();
        camera.powerOff();

        ArgumentCaptor<WriteCompleteListener> captor = ArgumentCaptor.forClass(WriteCompleteListener.class);

        verify(memoryCard).write(eq(new byte[]{42}), captor.capture());
        verify(sensor, never()).powerDown();

        captor.getValue().writeComplete();
        verify(sensor).powerDown();
    }
}
