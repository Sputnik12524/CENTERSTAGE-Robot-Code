package org.firstinspires.ftc.teamcode.opmodes.examples;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.modules.Intake;

@TeleOp(group = "Examples", name = "IntakeExample")
public class IntakeExample extends LinearOpMode {
    // единожды выполняемые действия до запуска программы
    // здесь следует создавать переменные и константы для сценария
    private int manualState = 1;
    private boolean xState = false;
    private boolean yState = false;

    @Override
    public void runOpMode() {
        // единожды выполняемые действия до инициализации
        // здесь следует создавать экземпляры модулей
        Intake intake = new Intake(this);
        while (opModeInInit()) {
            // единожды выполняемые действия во время инициализации

        }
        // единожды выполняемые действия после инициализации, но до запуска сценария

        waitForStart();
        // единожды выполняемые действия после запуска сценария

        while (opModeIsActive()) {
            // многократно выполняемые действия после запуска сценария
            if (manualState == 1) {
                if (gamepad1.y && !yState) {
                    intake.changeState();
                }
            } else if (manualState == 0) {
                if (gamepad1.a) {
                    intake.intake();
                } else if (gamepad1.b) {
                    intake.outtake();
                } else {
                    intake.stop();
                }
            } else if (manualState == 2) {
                intake.setPower(gamepad1.left_stick_x);
            }
            if (gamepad1.x && !xState) {
                manualState += 1;
                manualState = manualState % 3;
                intake.stop();
            }

            xState = gamepad1.x;
            yState = gamepad1.y;
            telemetry.addData("Mode", manualState);
            telemetry.update();
        }
    }

}
