package org.firstinspires.ftc.teamcode.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.modules.Drivetrain;
import org.firstinspires.ftc.teamcode.modules.Recognition;

@Disabled
@Autonomous(group = "Autonomous", name = "AutonomousRedLeft")
public class AutonomousRedLeft extends LinearOpMode {
    @Override
    public void runOpMode(){
        Recognition rc = new Recognition(this);
        Drivetrain dt = new Drivetrain(this);
        waitForStart();
        dt.drive(0,500,0, 0.3);
        if (rc.isRecognized() == true){ //элемент по центру
            dt.drive(100,1500,0,0.3);
        }
        else {
            dt.drive(0,0,90,0.5);
            dt.drive(0, 200, 0, 0.3);
            dt.drive(0,0,-90,0.5);
            if (rc.isRecognized() == true){ //элемент справа
                dt.drive(0,1200,0,0.3);
            }
            else { //элемент слева
                dt.drive(0,0,-90,0.5);
                dt.drive(0, 500, 0, 0.3);
                dt.drive(0,0,90,0.5);
                dt.drive(0,1200,0,0.3);
            }
        }
        dt.drive(0,1500, 0, -0.3); //выравниваемся у борта
        dt.drive(0,200,0,0.3);

    }


}