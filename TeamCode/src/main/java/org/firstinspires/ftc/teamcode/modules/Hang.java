package org.firstinspires.ftc.teamcode.modules;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import java.net.PortUnreachableException;


@Config
public class Hang {

    public static double HANG_DOWN_POSITION = 0;
    public static double HANG_UP_POSITION = 0;
    public static double COIL_DIAMETER = 0;
    public static double TIK = 28;
    public static double GEAR_RATIO = 0;

    private final LinearOpMode opMode;

    private final DcMotor hangDriveLeft;
    private final DcMotor hangDriveRight;
    // TODO: добавить второй мотор, не называть переменные с большой буквы, методы тоже

    public Hang (LinearOpMode opMode) {
        this.opMode = opMode;
        HardwareMap hg = opMode.hardwareMap;
        hangDriveRight = hg.get(DcMotor.class, "hangDriveRight");
        hangDriveLeft = hg.get(DcMotor.class, "hangDriveLeft");
    }

    public void hangPower(double power) {
        hangDriveLeft.setPower(power);
        hangDriveRight.setPower(power);
    }

    public double getHeightLeft () {
        return Math.PI * COIL_DIAMETER * (hangDriveLeft.getCurrentPosition() / (TIK * GEAR_RATIO));
    }
    public double getHeightRight () {
        return Math.PI * COIL_DIAMETER * (hangDriveRight.getCurrentPosition() / (TIK * GEAR_RATIO));
    }



}
