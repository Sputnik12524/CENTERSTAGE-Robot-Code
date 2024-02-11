package org.firstinspires.ftc.teamcode.opmodes.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.modules.Drivetrain;
import org.firstinspires.ftc.teamcode.modules.Intake;

@Autonomous(group = "Autonomous", name = "StupidAutoBlue")
public class StupidAutoBlueF4 extends LinearOpMode {
    @Override
    public void runOpMode() {
        Drivetrain dt = new Drivetrain(this);
        Intake it = new Intake(this);
        waitForStart();
        sleep(20000);
        dt.driveEncoder(2000,0.4);
        it.outtakeAuto();
        sleep(10000);
    }
}
