package org.firstinspires.ftc.teamcode.modules;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;
import org.firstinspires.ftc.robotcore.external.Telemetry;

public class Recognition {
    public final DistanceSensor ds;
    public static boolean isrecognized;
    telemetry.addData("range", String.format("%.01f cm", ds.getDistance(DistanceUnit.CM)));
    telemetry.appDate;
    public boolean isRecognized(){
        if (ds.getDistance(DistanceUnit.CM) < 200)
        { //распознал
            telemetry.addData("range", String.format("%.01f cm", ds.getDistance(DistanceUnit.CM));
            telemetry.addLine("Распознал");
            telemetry.appDate;
            isrecognized = TRUE;
        }
        else{
            telemetry.addData("range", String.format("%.01f cm", ds.getDistance(DistanceUnit.CM));
            telemetry.addLine("Не распознал");
            telemetry.appDate;
            isrecognized = False;
        }
        return isrecognized;
    }
}
