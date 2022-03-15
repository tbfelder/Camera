package com.develogical.camera;

public class Camera {

    private final Sensor sensor;
    private final MemoryCard memoryCard;

    public Camera(Sensor sensor, MemoryCard memoryCard) {
        this.sensor = sensor;
        this.memoryCard = memoryCard;
    }

    public void pressShutter() {
        WriteCompleteListener writeCompleteListener = new WriteCompleteListener() {
            @Override
            public void writeComplete() {

            }
        };

        memoryCard.write(sensor.readData(), writeCompleteListener);
    }

    public void powerOn() {
        sensor.powerUp();
    }

    public void powerOff() {
       sensor.powerDown();
    }

}

