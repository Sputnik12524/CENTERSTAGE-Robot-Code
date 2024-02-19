package org.firstinspires.ftc.teamcode.opmodes.examples;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.modules.Drivetrain;
import org.firstinspires.ftc.teamcode.modules.ImuSensor;

@TeleOp(group = "Example", name = "PIDExample")
@Config
public class PIDExample extends LinearOpMode {
    private static double rotate = 90;
    @Override
    public void runOpMode(){
        FtcDashboard dashboard = FtcDashboard.getInstance();
        Telemetry tm = dashboard.getTelemetry();
        ImuSensor imu;
        imu = new ImuSensor(this);
        Drivetrain dt = new Drivetrain(this);
        waitForStart();
        while (opModeIsActive()){
            if (gamepad1.dpad_right){
                dt.rotate(rotate);
            } else if (gamepad1.dpad_left) {
                dt.rotate(-rotate);
            }
            tm.addData("rotatePower: ", dt.getPIDPower());
            tm.addData("P: ", dt.kP);
            tm.addData("I: ", dt.kI);
            tm.addData("D: ", dt.kD);
            tm.addData("diff", dt.getDiff());
            tm.addData("Err: ", dt.getPrevErr());
            tm.update();
        }
    }
}