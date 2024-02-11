package org.firstinspires.ftc.teamcode.modules;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Servo;

@Config
public class Suspension {
    public static double RIGHT_POSITION_OPEN = 1;
    public static double LEFT_POSITION_OPEN = 1;
    public static double RIGHT_POSITION_CLOSE = 0;
    public static double LEFT_POSITION_CLOSE = 0;
    private final Servo leftSuspension;
    private final Servo rightSuspension;
    private final LinearOpMode opMode;

    public Suspension(LinearOpMode opMode){
        this.opMode = opMode;
        this.leftSuspension = opMode.hardwareMap.servo.get("leftSuspension");
        this.rightSuspension = opMode.hardwareMap.servo.get("rightSuspension");
    }
    public void openSuspension(){
        leftSuspension.setPosition(LEFT_POSITION_OPEN);
        rightSuspension.setPosition(RIGHT_POSITION_OPEN);
    }
    public void closeSuspension(){
        leftSuspension.setPosition(LEFT_POSITION_CLOSE);
        rightSuspension.setPosition(RIGHT_POSITION_CLOSE);
    }
}

