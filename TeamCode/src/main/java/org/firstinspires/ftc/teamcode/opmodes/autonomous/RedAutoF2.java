package org.firstinspires.ftc.teamcode.opmodes.autonomous;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.modules.Drivetrain;
import org.firstinspires.ftc.teamcode.modules.Intake;
import org.firstinspires.ftc.teamcode.modules.PixelDelivery;
import org.firstinspires.ftc.teamcode.modules.Recognition;

@Config
@Autonomous(group="Auto", name = "RedAutoF2")
public class RedAutoF2 extends LinearOpMode {
    // единожды выполняемые действия до запуска программы
    // здесь следует создавать переменные и константы для сценария

    @Override
    public void runOpMode(){
        // единожды выполняемые действия до инициализации
        Drivetrain dt = new Drivetrain(this);
        Intake it = new Intake(this);
        Recognition rc = new Recognition(this);
        PixelDelivery pd = new PixelDelivery(this);

        while(opModeInInit()){
            // единожды выполняемые действия во время инициализации

        }
        // единожды выполняемые действия после инициализации, но до запуска сценария

        waitForStart();
        // единожды выполняемые действия после запуска сценария

            // единожды выполняемые действия после запуска сценария
            dt.driveEncoder(500,0.3);
            if (rc.isRecognized() == true){ //элемент по центру
                dt.driveEncoder(1700,0.3);
                pd.fullOpenDoor();
            }
            else {
                dt.rotate(90);
                dt.driveEncoder(200, 0.3);
                dt.rotate(-90);
                if (rc.isRecognized() == true){ //элемент справа
                    dt.driveEncoder(1200,0.3);
                    pd.fullOpenDoor();
                }
                else { //элемент слева
                    dt.rotate(-90);
                    dt.driveEncoder(700,0.3);
                    dt.rotate(90);
                    dt.driveEncoder(1200,0.3);
                    pd.fullOpenDoor();
                }

            dt.driveEncoder(1750,-0.3); //выравниваемся у борта
            dt.driveEncoder(100,0.3);
            dt.rotate(90);
            dt.driveEncoder(5000,0.5);



        }

    }

}
