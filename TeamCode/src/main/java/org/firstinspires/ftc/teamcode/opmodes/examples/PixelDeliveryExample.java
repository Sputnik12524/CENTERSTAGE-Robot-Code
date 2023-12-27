package org.firstinspires.ftc.teamcode.opmodes.examples;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.modules.PixelDelivery;
import org.opencv.core.Range;

@TeleOp(group = "Example", name = "PixelDeliveryExample")
public class PixelDeliveryExample extends LinearOpMode {

    private double boxPosition = 0.5;
    private double servoFlip = 0.5;

    @Override
    public void runOpMode() {
        // единожды выполняемые действия до инициализации
        PixelDelivery pd = new PixelDelivery(this);

        // здесь следует создавать экземпляры модулей
        while (opModeInInit()) {
            // единожды выполняемые действия во время инициализации

        }
        // единожды выполняемые действия после инициализации, но до запуска сценария

        waitForStart();
        // единожды выполняемые действия после запуска сценария


        while (opModeIsActive()) {
            // единожды выполняемые действия после запуска сценария
            if (gamepad2.y) {
                pd.workTake();
            }
            if (gamepad2.right_bumper) {
                pd.workDrop();
            }

            if (gamepad2.a) {
                pd.fullOpenDoor();
            }
            if (gamepad2.x) {
                pd.halfOpenDoor();
            }
            if (gamepad2.b) {
                pd.closeDoor();
            }
            if (gamepad1.dpad_left && boxPosition < 1) {
                boxPosition += 0.005;
                sleep(5);
                pd.setBoxPosition(boxPosition);

            }
            if (gamepad1.dpad_right && boxPosition > 0) {
                boxPosition -= 0.005;
                sleep(5);
                pd.setBoxPosition(boxPosition);

            }

            if (gamepad1.dpad_up && servoFlip < 1) {
                servoFlip += 0.005;
                sleep(5);
                pd.setFlipPosition(servoFlip);

            }
            if (gamepad1.dpad_down && servoFlip > 0) {
                servoFlip -= 0.005;
                sleep(5);
                pd.setFlipPosition(servoFlip);

            }


            telemetry.addData("box", boxPosition);
            telemetry.addData("flip", servoFlip);
            telemetry.addLine("Gamepad2");
            telemetry.addLine(" A - Открытие двери ");
            telemetry.addLine(" B - Открытие двери на половину ");
            telemetry.addLine(" X - Закрытие двери ");
            telemetry.addLine(" dpad_UP - переворот сброс");
            telemetry.addLine(" dpad_DOWN - переворот принятие ");
            telemetry.addLine(" dpad_LEFT - поворот коробки для сброса ");
            telemetry.addLine(" dpad_RIGHT - поворот коробки для принятия ");
            telemetry.addLine(" right_bumper - многопоточное движение сброс");
            telemetry.addLine(" Y - многопоточное движение принятие");
            telemetry.update();

        }

    }
}
