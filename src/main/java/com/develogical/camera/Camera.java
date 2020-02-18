package com.develogical.camera;

public class Camera {
    private final Sensor sensor;
    private final MemoryCard memoryCard;

    private boolean isOn = false;
    private boolean dataBeingWritten = false;

    public Camera(Sensor sensor, MemoryCard memoryCard) {
        this.sensor = sensor;
        this.memoryCard = memoryCard;
    }

    public void pressShutter() {
        if (isOn) {
            dataBeingWritten = true;
            memoryCard.write(sensor.readData(), null);
        }
    }

    public void powerOn() {
        isOn = true;
        sensor.powerUp();
    }

    public void powerOff() {
        if (isOn && !dataBeingWritten) {
            sensor.powerDown();
        }
        isOn = false;
    }
}

