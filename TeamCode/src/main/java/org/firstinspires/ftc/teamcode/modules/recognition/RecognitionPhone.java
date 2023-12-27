package org.firstinspires.ftc.teamcode.modules.recognition;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvInternalCamera;
@TeleOp (name = "RecognitionPhone" )
public class RecognitionPhone extends LinearOpMode {
    OpenCvInternalCamera phoneCam;
    Recognition pipeline;
    public void runOpMode(){
        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        phoneCam = OpenCvCameraFactory.getInstance().createInternalCamera(OpenCvInternalCamera.CameraDirection.BACK, cameraMonitorViewId);
        pipeline = new Recognition();
        phoneCam.setPipeline(pipeline);
        phoneCam.setViewportRenderingPolicy(OpenCvCamera.ViewportRenderingPolicy.OPTIMIZE_VIEW);

        phoneCam.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener() {
            @Override
            public void onOpened() {
                phoneCam.startStreaming(320,240, OpenCvCameraRotation.SIDEWAYS_LEFT);
            }

            @Override
            public void onError(int errorCode) {

            }
        });

        while(!isStopRequested()){
            sleep(50);
            telemetry.addData("position is ", pipeline.getAnalysis());
            telemetry.addData("avgLeft is ", pipeline.getAvgLeft());
            telemetry.addData("avgMiddle is", pipeline.getAvgMiddle());
            telemetry.addData("avgRight is", pipeline.getAvgRight());
            telemetry.addData("avgCalibration is", pipeline.getAvgCalibration());
            telemetry.update();
        }
    }
}
