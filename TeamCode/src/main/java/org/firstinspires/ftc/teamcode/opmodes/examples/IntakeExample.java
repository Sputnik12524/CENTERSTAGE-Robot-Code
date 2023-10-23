package org.firstinspires.ftc.teamcode.opmodes.examples;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.modules.Intake;

public class IntakeExample extends LinearOpMode {
    // единожды выполняемые действия до запуска программы
    public Intake intake = new Intake(this);
    public boolean manualState = false;
    public boolean xState = false;
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
            // многократно выполняемые действия после запуска сценария
            if (gamepad1.a){
                intake.intake();
            } else if (gamepad1.b){
                intake.outtake();
            } else if (manualState){
                intake.setPower(gamepad1.left_stick_x);
            } else {
                intake.stop();
            }
            if (gamepad1.x && !xState){
                manualState = !manualState;
            }
            xState = gamepad1.x;
        }

    }

}
