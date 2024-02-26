package org.firstinspires.ftc.teamcode.opmodes.autonomous;

import static org.firstinspires.ftc.teamcode.modules.recognition.Position.MIDDLE;
import static org.firstinspires.ftc.teamcode.modules.recognition.Position.RIGHT;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.modules.Drivetrain;
import org.firstinspires.ftc.teamcode.modules.Intake;

import org.firstinspires.ftc.teamcode.modules.PixelDelivery;

import org.firstinspires.ftc.teamcode.modules.recognition.Recognition;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;

@Config
@Autonomous(group = "Auto", name = "BlueAutoF4")
public class BlueAutoF4 extends LinearOpMode {
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
        FtcDashboard.getInstance().startCameraStream(webcam,0);
        Recognition pipeline = new Recognition(this);
        webcam.setPipeline(pipeline);
        webcam.startStreaming(320, 240, OpenCvCameraRotation.UPRIGHT);

        int path = 1; //1 - центр, 2 - дальняя зона


        while (opModeInInit()) {
            webcam.startStreaming(320, 240, OpenCvCameraRotation.UPRIGHT);

            telemetry.addData("Pos: ", pipeline.getAnalysis());
            telemetry.update();
        }
        // единожды выполняемые действия после инициализации, но до запуска сценария

        waitForStart();
        // единожды выполняемые действия после запуска сценария

        // единожды выполняемые действия после запуска сценария
        dt.driveEncoder(200, -0.4);
        if (pipeline.getAnalysis() == MIDDLE){
            dt.driveEncoder(600,-0.4);
            pd.setForPurple(0);
            sleep(1000);
            dt.driveEncoder(600,0);
        }
        else if(pipeline.getAnalysis() == RIGHT){
            dt.driveEncoder(400,-0.4);
            dt.driveEncoderSide(400,-0.4);
            dt.driveEncoder(100,-0.4);
            dt.driveEncoderSide(200, 0.4);
            pd.setForPurple(0);
            dt.driveEncoder(400,0.4);
        }
        dt.driveEncoderSide(1500,-0.4);
    }

}
