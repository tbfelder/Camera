package com.develogical.camera;

public class Camera {
    private final Sensor sensor;
    private final MemoryCard memoryCard;
    private boolean isPowerOn = false;
    private boolean writing = false;
    private boolean powerOffSafelyRequested = false;
    private WriteCompleteListener writeCompleteListener;

    public Camera(Sensor sensor, MemoryCard memoryCard) {
        this.sensor = sensor;
        this.memoryCard = memoryCard;
    }

    public void pressShutter() {
        if (isPowerOn) {
            this.writing = true;
            this.writeCompleteListener = () -> {
                Camera.this.writing = false;
                if (Camera.this.powerOffSafelyRequested){
                    Camera.this.powerOffSafelyRequested = false;
                    Camera.this.powerOff();
                }
            };

            memoryCard.write(sensor.readData(), writeCompleteListener);
        }
    }

    public void powerOn() {
        sensor.powerUp();
        this.isPowerOn = true;
    }

    public void powerOff() {
        if (writeCompleteListener != null && !this.writing) {
            sensor.powerDown();
            this.isPowerOn = false;
        }
        else {
            this.powerOffSafelyRequested = true;
        }
    }
}

