package org.firstinspires.ftc.teamcode.modules;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

@Config
public class Suspension {
    private final LinearOpMode opMode;
    private final DcMotor suspensionDrive;
    public static final double SUSPENSION_POWER = -1;
    public static final double SUSPENSION_POWER1 = 1;


    public Suspension(LinearOpMode opMode) {
        this.opMode = opMode;
        HardwareMap hw = opMode.hardwareMap;
        suspensionDrive = hw.get(DcMotor.class, "SuspensionDrive");
        suspensionDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    /**
     * Метод включения захвата
     */
    public void up() {
        suspensionDrive.setPower(SUSPENSION_POWER);
    }

    /**
     * Метод включения выброса
     */
    public void down() {
        suspensionDrive.setPower(SUSPENSION_POWER1);
    }

    /**
     * Метод остановки вращения захвата
     */
    public void stop() {
        suspensionDrive.setPower(0);
    }

    /**
     * Метод установки мощности вращения подвес
     * @param power - мощность
     */
    public void setPower(double power) {
        suspensionDrive.setPower(power);
    }


}