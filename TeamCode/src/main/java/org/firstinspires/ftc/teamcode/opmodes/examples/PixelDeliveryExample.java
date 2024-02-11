package org.firstinspires.ftc.teamcode.opmodes.examples;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.modules.PixelDelivery;
import org.opencv.core.Range;

@TeleOp(group = "Example", name = "PixelDeliveryExample")
public class PixelDeliveryExample extends LinearOpMode {

    private double boxPosition = 0.485;
    private double servoFlip = 0.1;

    @Override
    public void runOpMode() {
        // единожды выполняемые действия до инициализации
        PixelDelivery pd = new PixelDelivery(this);

        // здесь следует создавать экземпляры модулей
        while (opModeInInit()) {
            // единожды выполняемые действия во время инициализации

        }
        waitForStart();
        // единожды выполняемые действия после запуска сценария


        while (opModeIsActive()) {
            // единожды выполняемые действия после запуска сценария
           /* if (gamepad1.left_bumper) {
                pd.workTake();
            }
            if (gamepad1.right_bumper) {
                pd.workDrop();
            }
            */

            if (gamepad1.x) {
                pd.fullOpenDoor();
            }
            if (gamepad1.y) {
                pd.halfOpenDoor();
            }
            if (gamepad1.b) {
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
            telemetry.addLine("Управление Gamepad1");
            telemetry.addLine(" X - Открытие двери ");
            telemetry.addLine(" Y - Открытие двери на половину ");
            telemetry.addLine(" B - Закрытие двери ");
            telemetry.addLine(" dpad_UP - переворот сброс");
            telemetry.addLine(" dpad_DOWN - переворот принятие ");
            telemetry.addLine(" dpad_LEFT - поворот коробки для сброса ");
            telemetry.addLine(" dpad_RIGHT - поворот коробки для принятия ");
            telemetry.addLine(" right_bumper - многопоточное движение сброс");
            telemetry.addLine(" left_bumper - многопоточное движение принятие");
            telemetry.update();

        }

    }
}
