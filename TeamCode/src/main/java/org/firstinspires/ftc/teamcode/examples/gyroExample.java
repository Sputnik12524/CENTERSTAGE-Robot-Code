package org.firstinspires.ftc.teamcode.examples;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.modules.ImuSensor;

@TeleOp(name = "imuExample")
public class gyroExample extends LinearOpMode {
    @Override
    public void runOpMode(){
        ImuSensor imu;
        imu = new ImuSensor(this);
        while (!isStarted() && !isStopRequested()) {
            telemetry.addData("gyro ", imu.getAngles());
            telemetry.update();
        }
    }
}
