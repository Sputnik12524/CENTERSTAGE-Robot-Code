package org.firstinspires.ftc.teamcode.opmodes.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.modules.Drivetrain;
import org.firstinspires.ftc.teamcode.modules.Intake;

@Autonomous (group = "Autonomous", name = "StupidAutoRedF2")
public class StupidAutoRedF2 extends LinearOpMode {
    @Override
    public void runOpMode(){
        Drivetrain dt = new Drivetrain(this);
        Intake it = new Intake(this);
        waitForStart();
        dt.driveEncoder(50,0.3);
        dt.driveEncoderSide(3500,-0.5);
        it.outtakeAuto();
        sleep(10000);
    }
}
