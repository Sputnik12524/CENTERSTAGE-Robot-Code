package org.firstinspires.ftc.teamcode.modules;

import com.acmerobotics.dashboard.FtcDashboard;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class MetryHandler {
    Telemetry tm;
    Telemetry dm;
    String mode;
    Boolean D = false;
    Boolean T = false;

    public MetryHandler(LinearOpMode opMode) {
        tm = opMode.telemetry;
    }

    public MetryHandler(LinearOpMode opMode, String _mode) {
        mode = _mode;
        if (mode.contains("T")) {
            tm = opMode.telemetry;
            T = true;
        }
        if (mode.contains("D")) {
            dm = FtcDashboard.getInstance().getTelemetry();
            D = true;
        }
    }

    public void addData(String caption, Number value) {
        if (T) {
            dm.addData(caption, value);
        }
        if (D) {
            tm.addData(caption, value);
        }
    }

    public void addLine(String line) {
        if (T) {
            tm.addLine(line);
        }
        if (D) {
            dm.addLine(line);
        }
    }

    public void update() {
        if (T) {
            tm.update();
        }
        if (D) {
            dm.update();
        }
    }
}
