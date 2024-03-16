package org.firstinspires.ftc.teamcode.opmodes.autonomous;

import static org.firstinspires.ftc.teamcode.modules.recognition.Position.LEFT;
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
    public static int sec = 1300;
    // единожды выполняемые действия до запуска программы
    // здесь следует создавать переменные и константы для сценария

    @Override
    public void runOpMode() {

        // единожды выполняемые действия до инициализации
        Drivetrain dt = new Drivetrain(this);
        Intake it = new Intake(this);
        PixelDelivery pd = new PixelDelivery(this);
        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        OpenCvCamera webcam = OpenCvCameraFactory.getInstance().createWebcam(hardwareMap.get(WebcamName.class, "webcam"), cameraMonitorViewId);
        FtcDashboard.getInstance().startCameraStream(webcam, 0);
        Recognition pipeline = new Recognition(this);
        pipeline.setAllianceColor(0);
        webcam.setPipeline(pipeline);
        webcam.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener() {
            @Override
            public void onOpened() {
                webcam.startStreaming(320, 240, OpenCvCameraRotation.UPRIGHT);
                FtcDashboard.getInstance().startCameraStream(webcam, 60);
            }

            @Override
            public void onError(int errorCode) {
                /*
                 * This will be called if the camera could not be opened
                 */
            }
        });
        int path = 1; //1 - центр, 2 - дальняя зона


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

        if (pipeline.getAnalysis() == RIGHT) { //элемент справа
//            dt.driveEncoder(1180,-0.3);
//            sleep(100);
//            pd.setForPurple(0);
//            sleep(1000);
//            dt.driveEncoder(1100, 0.3);
//            dt.driveRawPower(0,0,-0.5);
//            sleep(1000);
//            dt.stop();
//            dt.driveEncoderSide(250,1);
//            sleep(1000);
//            dt.driveEncoderSide(200,-0.3);
            dt.driveEncoder(915, -0.3);
            dt.driveEncoderSide(450, 0.5);
            sleep(400);
            pd.setForPurple(0);
            sleep(1000);
            dt.driveEncoder(30, 0.3);
            dt.driveEncoderSide(550, -0.6);
            dt.driveEncoder(780, 0.4);
            dt.driveRawPower(0, 0, 0.5);
            sleep(1000);
            dt.driveEncoderSide(220, 1);
            dt.driveEncoder(70, -0.3);
        } else if (pipeline.getAnalysis() == MIDDLE) { //Элемент посередине
            dt.driveEncoder(1180, -0.3);
            sleep(100);
            pd.setForPurple(0);
            sleep(1000);
            dt.driveEncoder(1100, 0.3);
            dt.driveRawPower(0, 0, -0.5);
            sleep(1000);
            dt.stop();
            dt.driveEncoderSide(225, 1);
            sleep(1000);
            dt.driveEncoderSide(200, -0.3);
        } else if (pipeline.getAnalysis() == LEFT) { //Элемент слева
//            dt.driveEncoder(1180,-0.3);
//            sleep(100);
//            pd.setForPurple(0);
//            sleep(1000);
//            dt.driveEncoder(1100, 0.3);
//            dt.driveRawPower(0,0,-0.5);
//            sleep(1000);
//            dt.stop();
//            dt.driveEncoderSide(225,0.8);
//            sleep(1000);
//            dt.driveEncoderSide(200,-0.3);
            dt.driveEncoder(800, -0.3);
            dt.driveEncoderSide(710, -0.6);
            sleep(400);
            pd.setForPurple(0);
            sleep(1000);
            dt.driveEncoder(100, 0.3);
            dt.driveEncoderSide(600, 0.6);
            dt.driveEncoder(770, 0.5);
            dt.driveRawPower(0, 0, 0.5);
            sleep(1000);
            dt.driveEncoderSide(220, 1);
            dt.driveEncoder(70, -0.3);

        }
        dt.driveEncoder(4500, -0.3);
        //dt.driveEncoderSide(3900,-0.5);
    }

}
