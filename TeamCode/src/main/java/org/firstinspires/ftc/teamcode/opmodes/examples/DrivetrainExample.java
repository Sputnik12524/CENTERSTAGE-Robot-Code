package org.firstinspires.ftc.teamcode.opmodes.examples;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.modules.Drivetrain;

@Config
@TeleOp(group = "Examples", name = "DrivetrainExample")
public class DrivetrainExample extends LinearOpMode {
    // единожды выполняемые действия до запуска программы
    // здесь следует создавать переменные и константы для сценария
    private int mode = 0;
    private boolean lbState;

    @Override
    public void runOpMode() {
        // единожды выполняемые действия до инициализации
        // здесь следует создавать экземпляры модулей
        Drivetrain dt = new Drivetrain(this);
        while (opModeInInit()) {
            // единожды выполняемые действия во время инициализации

        }
        // единожды выполняемые действия после инициализации, но до запуска сценария

        waitForStart();
        // единожды выполняемые действия после запуска сценария


        while (opModeIsActive()) {
            // единожды выполняемые действия после запуска сценария
            switch (mode) {
                case (1):
                    dt.driveFlawless(gamepad1.right_stick_x, gamepad1.left_stick_y, gamepad1.left_trigger - gamepad1.right_trigger);
                    break;

                case (2):
                    dt.driveCoeffPower(gamepad1.right_stick_x, gamepad1.left_stick_y, gamepad1.left_trigger - gamepad1.right_trigger);
                    break;

                case (3):
                    dt.driveRawPower(gamepad1.right_stick_x, gamepad1.left_stick_y, gamepad1.left_trigger - gamepad1.right_trigger);
                    break;

                case (4):
                    dt.checkMotors(gamepad1.a, gamepad1.b, gamepad1.x, gamepad1.y);
                    break;
            }
            if (gamepad1.left_bumper && !lbState) {
                mode += 1;
                mode = mode % 5;
            }
            lbState = gamepad1.left_bumper;
        }

    }

}
