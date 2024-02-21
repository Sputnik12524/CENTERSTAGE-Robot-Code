package org.firstinspires.ftc.teamcode.modules;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Servo;
@Config
public class DroneLauncher {
    private final LinearOpMode opMode;
    private final Servo droneServo;

    public DroneLauncher(LinearOpMode opMode) {
        this.opMode = opMode;
        this.droneServo = opMode.hardwareMap.servo.get("droneservo");



    }
    public void launch(){
        droneServo.setPosition(0);
        opMode.sleep(300);
        droneServo.setPosition(1);

    }
}
