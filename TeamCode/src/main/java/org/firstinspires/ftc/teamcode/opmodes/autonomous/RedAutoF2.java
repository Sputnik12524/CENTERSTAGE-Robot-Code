package org.firstinspires.ftc.teamcode.opmodes.autonomous;

import static org.firstinspires.ftc.teamcode.modules.recognition.Position.MIDDLE;
import static org.firstinspires.ftc.teamcode.modules.recognition.Position.RIGHT;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.modules.Drivetrain;
import org.firstinspires.ftc.teamcode.modules.Intake;

import org.firstinspires.ftc.teamcode.modules.PixelDelivery;

import org.firstinspires.ftc.teamcode.modules.recognition.Recognition;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;

@Config
@Autonomous(group = "Auto", name = "RedAutoF2")
public class RedAutoF2 extends LinearOpMode {
    // единожды выполняемые действия до запуска программы
    // здесь следует создавать переменные и константы для сценария
    public static int distToE3 = 700;
    public static int distToSpike = 500;

    @Override
    public void runOpMode() {
        // единожды выполняемые действия до инициализации
        Drivetrain dt = new Drivetrain(this);
        Intake it = new Intake(this);
        PixelDelivery pd = new PixelDelivery(this);
        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        OpenCvCamera webcam = OpenCvCameraFactory.getInstance().createWebcam(hardwareMap.get(WebcamName.class, "webcam"), cameraMonitorViewId);
        FtcDashboard.getInstance().startCameraStream(webcam,0);
        Recognition pipeline = new Recognition(this);
        pipeline.setAllianceColor(0);
        webcam.setPipeline(pipeline);
        webcam.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener() {
            @Override
            public void onOpened() {
                webcam.startStreaming(320, 240, OpenCvCameraRotation.UPRIGHT);
                FtcDashboard.getInstance().startCameraStream(webcam,60);
            }

            @Override
            public void onError(int errorCode) {
                /*
                 * This will be called if the camera could not be opened
                 */
            }
        });


        while (opModeInInit()) {
            sleep(150);
            pipeline.editRec(gamepad1);
            telemetry.addData("Pos: ", pipeline.getAnalysis());
            telemetry.update();
        }
        // единожды выполняемые действия после инициализации, но до запуска сценария

        waitForStart();
        // единожды выполняемые действия после запуска сценария

        // единожды выполняемые действия после запуска сценария
        dt.driveEncoder(200, -0.4);
        dt.driveEncoder(distToE3,-0.4);

        if (pipeline.getAnalysis() == MIDDLE){
            dt.driveEncoder(distToSpike,-0.4);
            pd.setForPurple(0);// освобождение пикселя
            sleep(1000);
            dt.driveEncoder(distToSpike,0.4);
        }
        else if(pipeline.getAnalysis() == RIGHT){
            dt.driveEncoderSide(distToSpike,-0.4);
            pd.setForPurple(0); // освобождение пикселя
            sleep(1000);
            dt.driveEncoderSide(distToSpike,0.4);

        } else {
            dt.driveEncoderSide(distToSpike,0.4);
            pd.setForPurple(0); // освобождение пикселя
            sleep(1000);
            dt.driveEncoderSide(distToSpike,-0.4);
        }
        dt.driveEncoder(920, 0.3); //выравниваемся у борта
        dt.driveEncoderSide(500, 0.4);
        dt.driveEncoderSide(2500, 0.4);

    }

}
