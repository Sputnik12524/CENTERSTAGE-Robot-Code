package org.firstinspires.ftc.teamcode.opmodes.autonomous;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.modules.Drivetrain;
import org.firstinspires.ftc.teamcode.modules.Intake;

import org.firstinspires.ftc.teamcode.modules.PixelDelivery;

import org.firstinspires.ftc.teamcode.modules.Recognition;

@Config
@Autonomous(group = "Auto", name = "RedAutoF2")
public class RedAutoF2 extends LinearOpMode {
    // единожды выполняемые действия до запуска программы
    // здесь следует создавать переменные и константы для сценария

    @Override
    public void runOpMode() {
        // единожды выполняемые действия до инициализации
        Drivetrain dt = new Drivetrain(this);
        Intake it = new Intake(this);
        Recognition rc = new Recognition(this);
        PixelDelivery pd = new PixelDelivery(this);
        int path = 1; //1 - центр, 2 - дальняя зона

        while (opModeInInit()) {
            telemetry.addData("Распознал?", rc.isRecognized());
            telemetry.update();

        }
        // единожды выполняемые действия после инициализации, но до запуска сценария

        waitForStart();
        // единожды выполняемые действия после запуска сценария

        // единожды выполняемые действия после запуска сценария
        dt.driveEncoder(200, -0.4);
        if (rc.isRecognized() == true) { //элемент по центру

            dt.driveEncoder(890, -0.4);
            dt.driveEncoderSide(25, 0.3);
            sleep(500);
            pd.setForPurple(0);
            sleep(1000);
            dt.driveEncoder(1225, 0.3); //выравниваемся у борта

        } else {
            dt.driveEncoderSide(475, 0.4);
            sleep(1000);
            telemetry.addData("Распознал?", rc.isRecognized());
            telemetry.update();
            sleep(2000);
            if (rc.isRecognized() == true) { //элемент слева
                dt.driveEncoder(520, -0.4);
                sleep(1000);
                pd.setForPurple(0);
                sleep(1000);
                dt.driveEncoder(100,0.4);
                dt.driveEncoderSide(250,-0.4);
                dt.driveEncoder(840, 0.3); //выравниваемся у борта


            } else { //элемент справа
                dt.driveEncoder(720, -0.4);
                dt.driveEncoderSide(950, -0.4);
                sleep(1000);
                pd.setForPurple(0);
                sleep(1000);
                dt.driveEncoderSide(500,0.4);
                dt.driveEncoder(980,0.4);
            }
        }
        if (path==1) {
            dt.driveEncoder(25, -0.4);
            dt.driveEncoderSide(2500, -0.4);
            dt.driveEncoder(200, 0.3);
            dt.driveEncoderSide(2500, -0.4);
        }
        else{
            dt.driveEncoder(25, -0.4);
            dt.driveEncoderSide(2300, -0.4);
            dt.driveEncoder(400, -0.3);
            dt.driveEncoderSide(2500, -0.4);
        }
        it.outtakeAuto();
        sleep(2000);
        it.stop();
    }

}
