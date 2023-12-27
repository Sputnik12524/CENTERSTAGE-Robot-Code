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
@Autonomous(group = "Auto", name = "RedAutoF4")
public class RedAutoF4 extends LinearOpMode {
    // единожды выполняемые действия до запуска программы
    // здесь следует создавать переменные и константы для сценария

    @Override
    public void runOpMode() {
        // единожды выполняемые действия до инициализации
        Drivetrain dt = new Drivetrain(this);
        Intake it = new Intake(this);
        Recognition rc = new Recognition(this);
        PixelDelivery pd = new PixelDelivery(this);


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

            dt.driveEncoder(650, -0.4);
            dt.driveEncoderSide(25, 0.3);
            sleep(1000);
            pd.flipDropPixel();
            pd.boxDropPixel();
            pd.halfOpenDoor();
            sleep(200);
            pd.flipTakePixel();
            pd.boxTakePixel();
            pd.closeDoor();

        } else {
            dt.driveEncoderSide(475, -0.4);
            sleep(500);
            if (rc.isRecognized() == true) { //элемент справа
                dt.driveEncoder(400, -0.4);
                dt.driveEncoderSide(100, 0.4);
                sleep(1000);
                pd.flipDropPixel();
                pd.boxDropPixel();
                pd.halfOpenDoor();
                sleep(200);
                pd.flipTakePixel();
                pd.boxTakePixel();
                pd.closeDoor();

            } else { //элемент слева
                dt.driveEncoder(550, -0.4);
                dt.driveEncoderSide(950, 0.4);
                dt.driveEncoder(400, -0.4);
                sleep(1000);
                pd.flipDropPixel();
                pd.boxDropPixel();
                pd.halfOpenDoor();
                sleep(200);
                pd.flipTakePixel();
                pd.boxTakePixel();
                pd.closeDoor();
                dt.driveEncoderSide(100, -0.4);

            }
        }
        dt.driveEncoder(900, 0.3); //выравниваемся у борта
        dt.driveEncoder(200, -0.4);
        dt.driveEncoderSide(2000, -0.4);


    }

}
