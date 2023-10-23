package org.firstinspires.ftc.teamcode.modules;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Servo;


@Config
public class PixelDelivery {

    public static double DOOR_CLOSED_POSITION = 0.01;
    public static double DOOR_FULL_OPEN_POSITION = 0.26;
    public static double DOOR_HALF_OPENED_POSITION = 0.228;
    private final Servo servoDoor;
    private final Servo boxRotation;
    private final Servo servoFlipLeft;
    private final Servo servoFlipRight;
    private final LinearOpMode opMode;

    public PixelDelivery(LinearOpMode opMode) {
        this.opMode = opMode;
        this.servoDoor = opMode.hardwareMap.servo.get("servoDoor");
        this.boxRotation = opMode.hardwareMap.servo.get("boxRotation");
        this.servoFlipLeft = opMode.hardwareMap.servo.get("servoFlipLeft");
        this.servoFlipRight = opMode.hardwareMap.servo.get("servoFlipRight");
    }

    public void fullOpenDoor() {
        servoDoor.setPosition(DOOR_FULL_OPEN_POSITION);
    }

    public void halfOpenDoor() {
        servoDoor.setPosition(DOOR_HALF_OPENED_POSITION);
    }

    public void closeDoor() {
        servoDoor.setPosition(DOOR_CLOSED_POSITION);
    }

}
