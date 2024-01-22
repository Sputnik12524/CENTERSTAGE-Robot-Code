package org.firstinspires.ftc.teamcode.modules.recognition;

import com.acmerobotics.dashboard.FtcDashboard;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;

@TeleOp
public class RecognitionWebcam extends LinearOpMode {
    OpenCvCamera webcam;
    Recognition pipeline;

    public void runOpMode() {
        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        webcam = OpenCvCameraFactory.getInstance().createWebcam(hardwareMap.get(WebcamName.class, "Webcam 1"), cameraMonitorViewId);
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
                pipeline.middleRegionX += 5;
            }
            gamepad1DpadLeftFlag = gamepad1.dpad_left;
            if (gamepad1DpadRightFlag && gamepad1.dpad_right) {
                pipeline.middleRegionX -= 5;
            }
            gamepad1DpadRightFlag = gamepad1.dpad_right;
            if (!gamepad1DpadUpFlag && gamepad1.dpad_up) {
                pipeline.middleRegionY += 5;
                pipeline.leftRegionX += 5;
            }
            gamepad1DpadUpFlag = gamepad1.dpad_up;
            if (!gamepad1DpadDownFlag && gamepad1.dpad_down) {
                pipeline.middleRegionY -= 5;
                pipeline.leftRegionX -= 5;
            }
            gamepad1DpadDownFlag = gamepad1.dpad_down;
            if (!gamepad1AFlag && gamepad1.a) {
                pipeline.maxvalCb += 2;
            }
            gamepad1AFlag = gamepad1.a;
            if (!gamepad1BFlag && gamepad1.b) {
                pipeline.maxvalCb -= 2;
            }
            gamepad1BFlag = gamepad1.b;
            if (!gamepad1XFlag && gamepad1.x) {
                pipeline.threshCr += 2;
            }
            gamepad1XFlag = gamepad1.x;
            if (!gamepad1YFlag && gamepad1.y) {
                pipeline.threshCr -= 2;
            }
            gamepad1YFlag = gamepad1.y;
            telemetry.addData("position is ", pipeline.getAnalysis());
            telemetry.addData("avgLeft is ", pipeline.getAvgLeft());
            telemetry.addData("avgMiddle is", pipeline.getAvgMiddle());
            telemetry.addData("avgRight is", pipeline.getAvgRight());
            telemetry.addData("shiftRightLeft", pipeline.middleRegionX);
            telemetry.addData("shiftUpDowm", pipeline.middleRegionY);
            telemetry.addData("thresh", pipeline.threshCb);
            telemetry.addData("maxval", pipeline.maxvalCb);
            telemetry.update();
        }


    }
}
