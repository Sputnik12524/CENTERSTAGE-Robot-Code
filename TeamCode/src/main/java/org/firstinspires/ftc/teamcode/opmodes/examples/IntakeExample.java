package org.firstinspires.ftc.teamcode.opmodes.examples;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.modules.Intake;

@TeleOp(group = "Examples", name = "IntakeExample")
public class IntakeExample extends LinearOpMode {
    // единожды выполняемые действия до запуска программы
    // здесь следует создавать переменные и константы для сценария
    private boolean manualState = false;
    private boolean xState = false;
    private boolean yState = false;
    @Override
    public void runOpMode(){
        // единожды выполняемые действия до инициализации
        // здесь следует создавать экземпляры модулей
        Intake intake = new Intake(this);
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
            if (gamepad1.y && !yState){
                intake.changeState();
            }
            xState = gamepad1.x;
            yState = gamepad1.y;
        }

    }

}
