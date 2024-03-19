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

import org.firstinspires.ftc.teamcode.modules.Suspension;
import org.firstinspires.ftc.teamcode.modules.recognition.Position;
import org.firstinspires.ftc.teamcode.modules.recognition.Recognition;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;

@Config
@Autonomous(group = "Auto", name = "RedAutoF4")
public class RedAutoF4 extends LinearOpMode {
    public static int sec = 1300;
    // единожды выполняемые действия до запуска программы
    // здесь следует создавать переменные и константы для сценария

    @Override
    public void runOpMode() {

        // единожды выполняемые действия до инициализации
        Drivetrain dt = new Drivetrain(this);
        Intake it = new Intake(this);
        PixelDelivery pd = new PixelDelivery(this);
        Suspension sus = new Suspension(this);
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
            sus.dropServo();
            telemetry.addData("Pos: ", pipeline.getAnalysis());
            telemetry.update();
        }
        // единожды выполняемые действия после инициализации, но до запуска сценария

        waitForStart();

        Position position = pipeline.getAnalysis();

        // проезд вперёд
        dt.driveEncoderCM(60, -0.25);
        sleep(1000);

        if (position == RIGHT) { //элемент справа
            dt.rotate(-90, -0.4);
        } else if (position == LEFT) { //Элемент слева
            dt.rotate(80, 0.4);
            dt.driveEncoderCM(8, -0.25);

        }
        sleep(2000);

        dt.driveEncoderCM(6,-0.25);
        pd.setForPurple(0);
        sleep(1000);

        dt.driveEncoderCM(6, 0.25);

        if (position == RIGHT) {
            dt.rotate(0, 0.4);
        } else if (position == LEFT) {
            dt.driveEncoderCM(8, 0.25);
            dt.rotate(0, -0.4);
        }
        dt.driveEncoderCM(42, 0.25);
        sleep(1000);
        dt.rotate(-90, -0.4);
        sleep(1000);
        dt.driveEncoderCM(90, -0.25);
    }

}
