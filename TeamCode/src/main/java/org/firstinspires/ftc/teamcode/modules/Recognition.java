package org.firstinspires.ftc.teamcode.modules;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

public class Recognition {
    private DistanceSensor ds;
    private final Telemetry tm;

    public Recognition(LinearOpMode opMode) {
        //telemetry.addData("range", String.format("%.01f cm", ds.getDistance(DistanceUnit.CM)));
        // telemetry.appDate
        HardwareMap hm = opMode.hardwareMap;
        ds = hm.get(DistanceSensor.class, "ds");
        tm = opMode.telemetry;
    }
    public boolean isRecognized () {
        tm.addData("Distance", ds.getDistance(DistanceUnit.CM));
        tm.update();
        boolean isrecognized = ds.getDistance(DistanceUnit.CM) < 200;
        return isrecognized;
    }
}
