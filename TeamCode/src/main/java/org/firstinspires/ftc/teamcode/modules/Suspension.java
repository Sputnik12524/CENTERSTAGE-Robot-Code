package org.firstinspires.ftc.teamcode.modules;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

@Config
public class Suspension {
    private final LinearOpMode opMode;
    private final DcMotor SuspensionDrive;
    public static final double Suspension_POWER = -1;
    public static final double Suspension_POWER1 = 1;
    private boolean SuspensionState = true;

    public Suspension(LinearOpMode opMode) {
        this.opMode = opMode;
        HardwareMap hw = opMode.hardwareMap;
        SuspensionDrive = hw.get(DcMotor.class, "SuspensionDrive");
        SuspensionDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    /**
     * Метод включения захвата
     */
    public void Up() {
        SuspensionDrive.setPower(Suspension_POWER);
    }

    /**
     * Метод включения выброса
     */
    public void Down() {
        SuspensionDrive.setPower(Suspension_POWER1);
    }
    public void outtakeAuto() {
        SuspensionDrive.setPower(Suspension_POWER);
    }

    /**
     * Метод остановки вращения захвата
     */
    public void stop() {
        SuspensionDrive.setPower(0);
    }

    /**
     * Метод установки мощности вращения захвата
     * @param power - мощность
     */
    public void setPower(double power) {
        SuspensionDrive.setPower(power);
    }

    /**
     * Метод переключения между состояниями захвата и выброса
     */
    public void changeState() {
        if (SuspensionState) {
            Up();
        } else {
            stop();
        }
        SuspensionState = !SuspensionState;
    }

}