package org.firstinspires.ftc.teamcode.modules;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.HardwareMap;

@Config
public class Suspension {
    private final LinearOpMode opMode;
    private final DcMotor suspensionDrive;
    private final Servo suspensionServo;
    public static final double SUSPENSION_POWER = 1;



    public Suspension(LinearOpMode opMode) {
        this.opMode = opMode;
        HardwareMap hw = opMode.hardwareMap;
        this.suspensionServo = opMode.hardwareMap.servo.get("suspensionServo");
        suspensionDrive = hw.get(DcMotor.class, "SuspensionDrive");
        suspensionDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    /**
     * Метод поднимания подвеса
     */
    public void up() {
        suspensionDrive.setPower(SUSPENSION_POWER);
    }

    /**
     * Метод опускания подвеса
     */
    public void down() {
        suspensionDrive.setPower(-SUSPENSION_POWER);
    }

    /**
     * Метод остановки вращения захвата
     */
    public void stop() {
        suspensionDrive.setPower(0);
    }
    public void dropServo(){
        suspensionServo.setPosition(0.4);
        opMode.sleep(300);
    }
    public void dropServo1(){
        suspensionServo.setPosition(0);
        opMode.sleep(300);
    }


    /**
     * Метод установки мощности вращения подвес
     * @param power - мощность
     */
    public void setPower(double power) {
        suspensionDrive.setPower(power);
    }


}