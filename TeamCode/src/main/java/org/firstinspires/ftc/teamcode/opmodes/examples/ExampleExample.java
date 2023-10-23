package org.firstinspires.ftc.teamcode.opmodes.examples;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

public class ExampleExample extends LinearOpMode {
    // единожды выполняемые действия до запуска программы

    @Override
    public void runOpMode(){
        // единожды выполняемые действия до инициализации

        while(opModeInInit()){
            // единожды выполняемые действия во время инициализации

        }
        // единожды выполняемые действия после инициализации, но до запуска сценария

        waitForStart();
        // единожды выполняемые действия после запуска сценария


        while(opModeIsActive()){
            // единожды выполняемые действия после запуска сценария

        }

    }

}
