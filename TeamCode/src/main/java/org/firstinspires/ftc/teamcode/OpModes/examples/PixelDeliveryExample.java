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
    public void runOpMode(){
        // единожды выполняемые действия до инициализации
        PixelDelivery pd = new PixelDelivery(this);

        // здесь следует создавать экземпляры модулей
        while(opModeInInit()){
            // единожды выполняемые действия во время инициализации

        }
        // единожды выполняемые действия после инициализации, но до запуска сценария

        waitForStart();
        // единожды выполняемые действия после запуска сценария


        while(opModeIsActive()){
            // единожды выполняемые действия после запуска сценария
            if (gamepad1.a){
                pd.fullOpenDoor();
            }
            if (gamepad1.b){
                pd.halfOpenDoor();
            }
            if (gamepad1.x){
                pd.closeDoor();
            }
            if (gamepad1.dpad_left){
                boxPosition += 0.001;
                sleep(5);
            }
            if (gamepad1.dpad_right){
                boxPosition -= 0.001;
                sleep(5);
            }
            if (boxPosition > 1){
                boxPosition = 1;
            }
            if (boxPosition < 0){
                boxPosition = 0;
            }
            pd.setBoxPosition(boxPosition);

            if (gamepad2.dpad_up) {
                servoFlip += 0.01;
                sleep(5);
            }
            if (gamepad2.dpad_down) {
                servoFlip -= 0.01;
                sleep(5);
            }
            if (servoFlip > 1){
                servoFlip = 1;
            }
            if (servoFlip < 0){
                servoFlip = 0;
            }
            pd.setFlipPosition(servoFlip);

            telemetry.addData("box", boxPosition);
            telemetry.addData("flip", servoFlip);
            telemetry.addLine(" A - Открытие двери ");
            telemetry.addLine(" B - Открытие двери на половину ");
            telemetry.addLine(" X - Закрытие двери ");
            telemetry.addLine(" dpad_UP - переворот вперед ");
            telemetry.addLine(" dpad_DOWN - переворот назад ");
            telemetry.addLine(" dpad_LEFT - поворот коробки назад ");
            telemetry.addLine(" dpad_RIGHT - поворот коробки вперед ");
            telemetry.update();

        }

    }
}
