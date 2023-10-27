package org.firstinspires.ftc.teamcode.opmodes.teleop;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.modules.Drivetrain;
import org.firstinspires.ftc.teamcode.modules.Intake;

@Disabled
public class MainTeleOp extends LinearOpMode {
    // единожды выполняемые действия до запуска программы
    // здесь следует создавать переменные и константы для сценария

    @Override
    public void runOpMode() {
        // единожды выполняемые действия до инициализации
        // здесь следует создавать экземпляры модулей
        Drivetrain dt = new Drivetrain(this);
        Intake it = new Intake(this);
        while (opModeInInit()) {
            // единожды выполняемые действия во время инициализации

        }
        // единожды выполняемые действия после инициализации, но до запуска сценария

        waitForStart();
        // единожды выполняемые действия после запуска сценария


        while (opModeIsActive()) {
            // единожды выполняемые действия после запуска сценария
            dt.driveRawPower(gamepad1.left_stick_x, gamepad1.right_stick_y,
                    gamepad1.left_trigger - gamepad1.right_trigger);
            if (gamepad1.a) {
                it.intake();
            } else if (gamepad1.b) {
                it.outtake();
            } else {
                it.stop();
            }
            telemetry.addLine("КБ - 1й геймпад");
            telemetry.addLine("КБ: влево-вправо - левый стик влево-вправо");
            telemetry.addLine("КБ: вперёд-назад - правый стик вверх-вниз");
            telemetry.addLine("A - захват, B - обр. захват");
            telemetry.update();
        }

    }

}
