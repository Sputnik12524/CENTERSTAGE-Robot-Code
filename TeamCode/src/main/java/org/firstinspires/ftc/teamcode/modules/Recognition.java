package org.firstinspires.ftc.teamcode.modules;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

public class Recognition {
    public static final int RANGE_TO_ELEMENT = 200;
    private DistanceSensor ds;
    private final Telemetry tm;

    public Recognition(LinearOpMode opMode) {
        HardwareMap hm = opMode.hardwareMap;
        ds = hm.get(DistanceSensor.class, "ds");
        tm = opMode.telemetry;
    }

    public boolean isRecognized() {
        tm.addData("Distance", ds.getDistance(DistanceUnit.CM));
        tm.update();
        boolean isrecognized = ds.getDistance(DistanceUnit.CM) < RANGE_TO_ELEMENT;
        return isrecognized;
    }
}
