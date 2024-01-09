//package org.firstinspires.ftc.teamcode.examples;
//
//
//import com.acmerobotics.dashboard.config.Config;
//import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
//import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
//
//import org.firstinspires.ftc.teamcode.modules.recognition.Recognition;
//
//@TeleOp(name = "RecognitionExample")
//@Config
//public class RecognitionExample extends LinearOpMode {
//    @Override
//    public void runOpMode() throws InterruptedException {
//
//        Recognition rc = new Recognition(this);
//        while (!isStarted() && !isStopRequested()) {
//            telemetry.addData("Распознал? ", rc.isRecognized());
//            telemetry.update();
//        }
//
//    }
//}
