package com.develogical.camera;

public class Camera {
    private final Sensor sensor;
    private final MemoryCard memoryCard;

    private boolean isOn = false;

    public Camera(Sensor sensor, MemoryCard memoryCard) {
        this.sensor = sensor;
        this.memoryCard = memoryCard;
    }

    public void pressShutter() {
        if (isOn) {
            memoryCard.write(sensor.readData(), null);
        }
    }

    public void powerOn() {
        isOn = true;
        sensor.powerUp();
    }

    public void powerOff() {
        if (isOn) {
            sensor.powerDown();
        }
        isOn = false;
    }
}

