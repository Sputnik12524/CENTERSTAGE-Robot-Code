package org.firstinspires.ftc.teamcode.opmodes.examples;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.modules.PixelDelivery;

@TeleOp(group = "TeleOp", name = "PurpleServoExample")
public class PurpleServoExample extends LinearOpMode {
    @Override
    public void runOpMode() {
        PixelDelivery pd = new PixelDelivery(this);
        while (opModeInInit()) {

            waitForStart();

            while (opModeIsActive()) {
                if (gamepad1.x) {
                    pd.setForPurple(0);
                }
                if (gamepad1.b) {
                    pd.setForPurple(1);
                }
            }
        }
    }
}
