package org.firstinspires.ftc.teamcode.modules;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

@Config
public class Intake {
    private final LinearOpMode opMode;
    private final DcMotor intakeDrive;
    public static final double INTAKE_POWER = 0.5;
    public static final double OUTTAKE_POWER = 0.5;
    public static boolean intakeState = true;

    public Intake(LinearOpMode opMode) {
        this.opMode = opMode;
        HardwareMap hw = opMode.hardwareMap;
        intakeDrive = hw.get(DcMotor.class, "intakeDrive");
        intakeDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    /**
     * Метод включения захвата
     */
    public void intake() {
        intakeDrive.setPower(INTAKE_POWER);
    }

    /**
     * Метод включения выброса
     */
    public void outtake() {
        intakeDrive.setPower(OUTTAKE_POWER);
    }

    /**
     * Метод остановки вращения захвата
     */
    public void stop() {
        intakeDrive.setPower(0);
    }

    /**
     * Метод установки мощности вращения захвата
     * @param power - мощность
     */
    public void setPower(double power) {
        intakeDrive.setPower(power);
    }

    /**
     * Метод переключения между состояниями захвата и выброса
     */
    public void changeState() {
        if (intakeState) {
            intake();
        } else {
            outtake();
        }
        intakeState = !intakeState;
    }

}
