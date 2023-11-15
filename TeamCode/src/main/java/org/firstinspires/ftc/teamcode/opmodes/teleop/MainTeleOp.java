package org.firstinspires.ftc.teamcode.opmodes.teleop;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.modules.Drivetrain;
import org.firstinspires.ftc.teamcode.modules.Intake;
import org.firstinspires.ftc.teamcode.modules.PixelDelivery;

@TeleOp(name = "teleop", group = "teleop")
public class MainTeleOp extends LinearOpMode {
    // единожды выполняемые действия до запуска программы
    // здесь следует создавать переменные и константы для сценария
    public static double boxPosition = 0.5;
    public static double flipPosition = 0.5;

    public static double flipDelta = 0.01;
    public static double boxDelta = 0.01;

    private int doorMode = 0;
    private boolean rbState = false;
    @Override
    public void runOpMode() {
        // единожды выполняемые действия до инициализации
        // здесь следует создавать экземпляры модулей
        Drivetrain dt = new Drivetrain(this);
        Intake it = new Intake(this);
        //PixelDelivery pd = new PixelDelivery(this);
        while (opModeInInit()) {
            // единожды выполняемые действия во время инициализации

        }
        // единожды выполняемые действия после инициализации, но до запуска сценария

        waitForStart();
        // единожды выполняемые действия после запуска сценария


        while (opModeIsActive()) {
            // единожды выполняемые действия после запуска сценария
            //  Управление колёсной базой
            dt.driveRawPower(gamepad1.left_stick_x, gamepad1.right_stick_y,
                    gamepad1.left_trigger - gamepad1.right_trigger);

            // Управление захватом
            if (gamepad2.x) {
                it.intake();
            } else if (gamepad2.a) {
                it.outtake();
            } else {
                it.stop();
            }

            // Управление вращением коробочки
            /*if (gamepad2.dpad_left) {
                boxPosition += boxDelta;
                sleep(5);
            }
            if (gamepad2.dpad_right) {
                boxPosition -= boxDelta;
                sleep(5);
            }
            if (boxPosition > 1) {
                boxPosition = 1;
            }
            if (boxPosition < 0) {
                boxPosition = 0;
            }
            pd.setBoxPosition(boxPosition);

            // Управление переворотом
            if (gamepad2.dpad_up) {
                flipPosition += flipDelta;
                sleep(5);
            }
            if (gamepad2.dpad_down) {
                flipPosition -= flipDelta;
                sleep(5);
            }
            if (flipPosition > 1) {
                flipPosition = 1;
            }
            if (flipPosition < 0) {
                flipPosition = 0;
            }
            pd.setFlipPosition(flipPosition);

            //Управление дверью
            if (gamepad2.right_bumper && !rbState){
                doorMode = (doorMode + 1) % 3;
            }
            rbState = gamepad2.right_bumper;
            switch(doorMode){
                case(0):
                    pd.fullOpenDoor();
                    break;
                case(1):
                    pd.halfOpenDoor();
                    break;
                case(2):
                    pd.closeDoor();
                    break;
            }*/

            //  Отображение телеметрии
            telemetry.addLine("КБ - 1й геймпад");
            telemetry.addLine("КБ: влево-вправо - левый стик влево-вправо");
            telemetry.addLine("КБ: вперёд-назад - правый стик вверх-вниз");
            telemetry.addLine();
            telemetry.addLine("Коробочка, переворот - 2й геймпад");
            telemetry.addLine("A - захват, B - обр. захват");
            telemetry.addLine("Дверь: левый бампер");
            telemetry.addLine("Переворот: крестовина вверх-вниз");
            telemetry.addLine("Поворот коробочки: крестовина влево-вправо");

            telemetry.update();
        }

    }

}
