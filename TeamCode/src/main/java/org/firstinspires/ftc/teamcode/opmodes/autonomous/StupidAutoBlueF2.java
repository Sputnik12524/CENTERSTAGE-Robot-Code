package org.firstinspires.ftc.teamcode.opmodes.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.modules.Drivetrain;
import org.firstinspires.ftc.teamcode.modules.Intake;

@Autonomous(group = "Autonomous", name = "StupidAutoBlueF2")
public class StupidAutoBlueF2 extends LinearOpMode {
    @Override
    public void runOpMode() {
        Drivetrain dt = new Drivetrain(this);
        Intake it = new Intake(this);
        waitForStart();
        sleep(1000);
        dt.driveEncoderSide(152, -0.25);
        dt.driveEncoder(1500, 0.3);
        it.outtakeAuto();
        sleep(10000);
        it.stop();
    }
}
