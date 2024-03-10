package org.firstinspires.ftc.teamcode.opmodes.teleop;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.modules.Drivetrain;
import org.firstinspires.ftc.teamcode.modules.DroneLauncher;
import org.firstinspires.ftc.teamcode.modules.Intake;
import org.firstinspires.ftc.teamcode.modules.PixelDelivery;
import org.firstinspires.ftc.teamcode.modules.Recognition;
import org.firstinspires.ftc.teamcode.modules.Suspension;

@TeleOp(name = "teleop", group = "teleop")
public class MainTeleOp extends LinearOpMode {
    // единожды выполняемые действия до запуска программы
    // здесь следует создавать переменные и константы для сценария
    public static double boxPosition = 0.72;
    public static double flipPosition = 1;

    public static double flipDelta = 0.01;
    public static double boxDelta = 0.01;
    public static double slow = 1;

    private int doorMode = 0;

    private int pixelDeliveryMod = 0;
    private boolean rbState = false;
    private boolean yState = false;
    private boolean flipState = false;

    @Override
    public void runOpMode() {
        // единожды выполняемые действия до инициализации
        // здесь следует создавать экземпляры модулей
        Drivetrain dt = new Drivetrain(this);
        Intake it = new Intake(this);
        PixelDelivery pd = new PixelDelivery(this);
        Suspension susp = new Suspension(this);
        DroneLauncher dl = new DroneLauncher(this);
        Recognition rec = new Recognition(this);
        while (opModeInInit()) {
            // единожды выполняемые действия во время инициализации

        }
        // единожды выполняемые действия после инициализации, но до запуска сценария

        waitForStart();
        // единожды выполняемые действия после запуска сценария

        while (opModeIsActive()) {
            telemetry.addData("Distance", rec.getDistance());
            // единожды выполняемые действия после запуска сценария
            //  Управление колёсной базой

            dt.driveRawPower(-gamepad1.right_stick_x * slow, -gamepad1.left_stick_y * slow,
                    (gamepad1.left_trigger - gamepad1.right_trigger) * slow * 1.75);

            /**
             * Управление захватом
             */
            if (gamepad2.right_stick_button) {
                it.intake();
            } else if (gamepad2.left_stick_button) {
                it.outtake();
            } else {
                it.stop();
            }
            if (gamepad2.dpad_up) {
                susp.up();
            } else if (gamepad2.dpad_down) {
                susp.down();
            } else {
                susp.setPower(0);
            }
            if (gamepad1.right_bumper) {
                slow = 0.4;
            }
            if (gamepad1.left_bumper) {
                slow = 1;
            }
            if (gamepad2.b){
                susp.dropServo();
            }
            if (gamepad2.x){
                susp.dropServo1();
            }


            if (gamepad1.dpad_left) dt.strafeLeft();
            if (gamepad1.dpad_right) dt.strafeRight();
            if (gamepad1.dpad_up) {
                dt.driveRawPower(0, 0.3, 0);
            }
            if (gamepad1.dpad_down) {
                dt.driveRawPower(0, -0.3, 0);
            }
            /**
             * Управление дверью
             */

            if (gamepad2.right_bumper && !rbState && flipState) {
                doorMode = (doorMode + 1) % 3;
            }
            rbState = gamepad2.right_bumper;
            switch (doorMode) {
                case (0):
                    pd.closeDoor();
                    break;
                case (1):
                    pd.halfOpenDoor();
                    break;
                case (2):
                    pd.fullOpenDoor();
                    break;
            }


            /**
             *  Управление переворотом и выбросом вместе
             */


            if (gamepad2.a) {
                doorMode = 0;
                pd.closeDoor();
                pd.boxTakePixel();
                sleep(200);
                pd.flipTakePixel();
                flipState = false;
            }
            /*if (gamepad2.b) {
                pd.boxTakePixel1();
            }*/
            if (gamepad2.y) {
                pd.flipDropPixel();
                sleep(200);
                pd.boxDropPixel();
                flipState = true;
            }

            /*if (gamepad2.x) {
                pd.flipDropPixelfirstline();
                //pd.boxTakePixel();
                sleep(200);
                pd.boxDropPixelfirstline();
                flipState = true;
            }*/
            /**
             * Управление самолетом
             */
            if (gamepad2.left_bumper && gamepad1.left_bumper) {
                dl.launch();
            }

            /**
             * Отображение телеметрии
             */
            telemetry.addLine("КБ - 1й геймпад");
            telemetry.addLine("КБ: влево-вправо - левый стик влево-вправо");
            telemetry.addLine("КБ: вперёд-назад - правый стик вверх-вниз");
            telemetry.addLine("КБ: Вправо-влево - правый/левый триггеры");
            telemetry.addLine();
            telemetry.addLine("PD - 2й геймпад");
            telemetry.addLine("X - захват, A - обр. захват");
            telemetry.addLine("Дверь: левый бампер");
            telemetry.addLine("Переворот и вращение принятие - Y");
            telemetry.addLine("Переворот и вращение сброс - B");


               /* telemetry.addLine("Переворот: крестовина вверх-вниз");
                telemetry.addLine("Поворот коробочки: крестовина влево-вправо");
                telemetry.addData("Позиция переворота", flipPosition);
                telemetry.addData("Позиция вращения коробочки", boxPosition);

                */

            telemetry.update();
        }

    }

}
