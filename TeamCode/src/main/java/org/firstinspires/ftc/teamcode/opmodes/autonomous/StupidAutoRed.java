package org.firstinspires.ftc.teamcode.opmodes.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.modules.Drivetrain;

@Autonomous (group = "Autonomous", name = "StupidAutoRed")
public class StupidAutoRed extends LinearOpMode {
    @Override
    public void runOpMode(){
        Drivetrain dt = new Drivetrain(this);
        waitForStart();
        dt.driveEncoder(150,0.3);
        dt.driveEncoderSide(3500,0.3);

    }
}
