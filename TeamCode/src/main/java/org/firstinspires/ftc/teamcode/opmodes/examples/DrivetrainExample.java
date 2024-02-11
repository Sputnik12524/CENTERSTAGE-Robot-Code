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
        telemetry.addLine("Запущен пример колёсной базы: управление с 1-ого геймпада."+
                "4 режима управления: независимое вращение, с коэффициентами осей, без коэффициентов осей, настройка моторов.");
        telemetry.update();

        waitForStart();
        // единожды выполняемые действия после запуска сценария


        while (opModeIsActive()) {
            // единожды выполняемые действия после запуска сценария
            switch (mode) {
                case (0):
                    telemetry.addLine("Вы управляете базой с независимым направлением.");
                    dt.driveFlawless(-gamepad1.right_stick_x,-gamepad1.left_stick_y, gamepad1.left_trigger - gamepad1.right_trigger);
                    if (gamepad1.right_bumper){
                        dt.initIMU();
                    }
                    break;

                case (1):
                    telemetry.addLine("Вы управляете базой c коэффициентами осей.");
                    dt.driveCoeffPower(gamepad1.right_stick_x, gamepad1.left_stick_y, gamepad1.left_trigger - gamepad1.right_trigger);
                    break;

                case (2):
                    telemetry.addLine("Вы управляете базой без коэффициентов осей.");
                    dt.driveRawPower(gamepad1.right_stick_x, gamepad1.left_stick_y, gamepad1.left_trigger - gamepad1.right_trigger);
                    break;

                case (3):
                    telemetry.addLine("Вы управляете моторами, расположенные по схеме при взгляде сверху-вниз" +
                            "   спереди" +
                            " A        B " +
                            "лево  право" +
                            " X        Y " +
                            "   сзади");
                    dt.checkMotors(gamepad1.a, gamepad1.b, gamepad1.x, gamepad1.y);
                    break;
            }
            if (gamepad1.left_bumper && !lbState) {
                mode = (mode + 1)% 4;}
            lbState = gamepad1.left_bumper;
            telemetry.update();
        }

    }

}
