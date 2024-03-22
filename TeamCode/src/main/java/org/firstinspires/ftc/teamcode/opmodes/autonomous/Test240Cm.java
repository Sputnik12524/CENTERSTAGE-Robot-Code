package org.firstinspires.ftc.teamcode.opmodes.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.modules.Drivetrain;
@Autonomous(group = "Autonomous", name = "Test240CM")
public class Test240Cm extends LinearOpMode {
    @Override
    public void runOpMode() {
        Drivetrain dt = new Drivetrain(this);
        waitForStart();
        sleep(1000);
        dt.driveEncoderCM(240, 0.25);
        sleep(10000);
    }

}
