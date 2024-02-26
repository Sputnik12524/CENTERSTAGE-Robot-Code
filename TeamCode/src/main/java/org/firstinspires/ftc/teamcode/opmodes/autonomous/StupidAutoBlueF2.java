package org.firstinspires.ftc.teamcode.opmodes.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.modules.Drivetrain;
import org.firstinspires.ftc.teamcode.modules.Intake;

@Autonomous (group = "Autonomous", name = "StupidAutoBlueF2")
public class StupidAutoBlueF2 extends LinearOpMode {
    @Override
    public void runOpMode(){
        Drivetrain dt = new Drivetrain(this);
        Intake it = new Intake(this);
        waitForStart();
        sleep(10000);
        dt.driveEncoder(100,0.25);
        dt.driveEncoderSide(4000,0.5);
        it.outtakeAuto();
        sleep(10000);
        it.stop();
    }
}
