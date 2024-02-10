package org.firstinspires.ftc.teamcode.examples;

import com.acmerobotics.dashboard.FtcDashboard;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.modules.recognition.Recognition;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;

@TeleOp
public class RecognitionWebcam extends LinearOpMode {
    OpenCvCamera webcam;
    Recognition pipeline;

    public void runOpMode() {
        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        webcam = OpenCvCameraFactory.getInstance().createWebcam(hardwareMap.get(WebcamName.class, "webcam"), cameraMonitorViewId);
        FtcDashboard.getInstance().startCameraStream(webcam,0);
        pipeline = new Recognition();
        webcam.setPipeline(pipeline);
        webcam.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener() {
            @Override
            public void onOpened() {
                webcam.startStreaming(320, 240, OpenCvCameraRotation.UPRIGHT);
            }

            @Override
            public void onError(int errorCode) {
                /*
                 * This will be called if the camera could not be opened
                 */
            }
        });
        //webcam.setPipeline(pipeline);
        //webcam.startStreaming(320,240,OpenCvCameraRotation.UPRIGHT);
        boolean gamepad1DpadLeftFlag = false;
        boolean gamepad1DpadRightFlag = false;
        boolean gamepad1DpadUpFlag = false;
        boolean gamepad1DpadDownFlag = false;
        boolean gamepad1AFlag = false;
        boolean gamepad1BFlag = false;
        boolean gamepad1XFlag = false;
        boolean gamepad1YFlag = false;
        while (!isStopRequested()) {
            sleep(50);
            if (!gamepad1DpadLeftFlag && gamepad1.dpad_left) {
                pipeline.MIDDLE_REGION_X += 5;
            }
            gamepad1DpadLeftFlag = gamepad1.dpad_left;
            if (gamepad1DpadRightFlag && gamepad1.dpad_right) {
                pipeline.MIDDLE_REGION_X -= 5;
            }
            gamepad1DpadRightFlag = gamepad1.dpad_right;
            if (!gamepad1DpadUpFlag && gamepad1.dpad_up) {
                pipeline.MIDDLE_REGION_Y += 5;
                pipeline.LEFT_REGION_Y += 5;
            }
            gamepad1DpadUpFlag = gamepad1.dpad_up;
            if (!gamepad1DpadDownFlag && gamepad1.dpad_down) {
                pipeline.MIDDLE_REGION_Y -= 5;
                pipeline.LEFT_REGION_X -= 5;
            }
            gamepad1DpadDownFlag = gamepad1.dpad_down;
            if (!gamepad1AFlag && gamepad1.a) {
                pipeline.MAXVAL_CB += 2;
            }
            gamepad1AFlag = gamepad1.a;
            if (!gamepad1BFlag && gamepad1.b) {
                pipeline.MAXVAL_CB -= 2;
            }
            gamepad1BFlag = gamepad1.b;
            if (!gamepad1XFlag && gamepad1.x) {
                pipeline.THRESH_CR += 2;
            }
            gamepad1XFlag = gamepad1.x;
            if (!gamepad1YFlag && gamepad1.y) {
                pipeline.THRESH_CR -= 2;
            }
            gamepad1YFlag = gamepad1.y;
            telemetry.addData("position is ", pipeline.getAnalysis());
            telemetry.addData("avgLeft is ", pipeline.getAvgLeft());
            telemetry.addData("avgMiddle is", pipeline.getAvgMiddle());
            telemetry.addData("avgRight is", pipeline.getAvgRight());
            telemetry.addData("shiftRightLeft", pipeline.MIDDLE_REGION_X);
            telemetry.addData("shiftUpDowm", pipeline.MIDDLE_REGION_Y);
            telemetry.addData("thresh", pipeline.THRESH_CB);
            telemetry.addData("maxval", pipeline.MAXVAL_CB);
            telemetry.update();
        }


    }
}
