package com.develogical.camera;

import org.junit.Test;
import org.mockito.ArgumentCaptor;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

public class CameraTest {

    private final Sensor sensor = mock(Sensor.class);
    private final MemoryCard memoryCard = mock(MemoryCard.class);
    private final Camera underTest = new Camera(sensor, memoryCard);

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

    @Test
    public void shutterUnderPowerCopiesDataFromSensorToMemory() {

        byte [] expectedData = new byte[]{123};
        when(sensor.readData()).thenReturn(expectedData);
        underTest.powerOn();

        underTest.pressShutter();

        verify(memoryCard).write(eq(expectedData), any());

    }

    @Test
    public void shutterUnderPowerOffDoesNothing() {

        underTest.powerOff();
        underTest.pressShutter();

        verify(sensor).powerDown();
        verifyNoMoreInteractions(memoryCard, sensor);

    }

    @Test
    public void PowerOffDoesNothingIfDataIsBeingWritten(){

        ArgumentCaptor<WriteCompleteListener> writeCompleteListenerCaptor = ArgumentCaptor.forClass(WriteCompleteListener.class);

        underTest.powerOn();
        underTest.pressShutter();

        underTest.powerOff();

        verify(memoryCard).write(any(), writeCompleteListenerCaptor.capture() );
        verify(sensor, times(0)).powerDown();
        // still writing

        WriteCompleteListener writeCompleteListener = writeCompleteListenerCaptor.getValue();
        writeCompleteListener.writeComplete();

        // finished writing
        verify(sensor).powerDown();

    }
}
