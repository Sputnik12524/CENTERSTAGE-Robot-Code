package org.firstinspires.ftc.teamcode.modules;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;


@Config
public class PixelDelivery {
//Уменьшить переменную-привести к закрытому положению
//Увеличение переменной-привести к открытому положению
    public static double DOOR_CLOSED_POSITION = 0.25;
    public static double DOOR_FULL_OPEN_POSITION = 0.6;
    public static double DOOR_HALF_OPENED_POSITION = 0.51;
    public static double BOX_ROTATION_DROP_POSITION = 0.45;
    public static double BOX_ROTATION_TAKE_POSITION = 0.79;
    public static double FLIP_TAKE_POSITION = 0.805;
    public static double FLIP_DROP_POSITION = 0.15;

    public static double FLIP_TIME = 100;

    private final Servo servoDoor;
    private final Servo boxRotation;
    private final Servo servoFlipLeft;
    private final Servo servoFlipRight;
    private final LinearOpMode opMode;
    private final TakeDropHelper takeDropHelper;

    public PixelDelivery(LinearOpMode opMode) {
        this.opMode = opMode;
        this.servoDoor = opMode.hardwareMap.servo.get("servoDoor");
        this.boxRotation = opMode.hardwareMap.servo.get("boxRotation");
        this.servoFlipLeft = opMode.hardwareMap.servo.get("servoFlipLeft");
        this.servoFlipRight = opMode.hardwareMap.servo.get("servoFlipRight");
        this.servoFlipRight.setDirection(Servo.Direction.REVERSE);
        this.boxRotation.setDirection(Servo.Direction.REVERSE);
        this.takeDropHelper = new TakeDropHelper();
        takeDropHelper.start();
    }

    public void fullOpenDoor() {
        servoDoor.setPosition(DOOR_FULL_OPEN_POSITION);
    }

    public void halfOpenDoor() {
        servoDoor.setPosition(DOOR_HALF_OPENED_POSITION);
    }

    public void closeDoor() {
        servoDoor.setPosition(DOOR_CLOSED_POSITION);
    }

    public void boxTakePixel() {
        boxRotation.setPosition(BOX_ROTATION_TAKE_POSITION);
    }

    public void boxDropPixel() {
        boxRotation.setPosition(BOX_ROTATION_DROP_POSITION);
    }

    public void workTake() {
        takeDropHelper.needWorkTake = true;
    }
    public void workDrop(){
        takeDropHelper.needWorkDrop = true;
    }

    public void flipTakePixel() {
        servoFlipLeft.setPosition(FLIP_TAKE_POSITION);
        servoFlipRight.setPosition(FLIP_TAKE_POSITION);
    }

    public void flipDropPixel() {
        servoFlipLeft.setPosition(FLIP_DROP_POSITION);
        servoFlipRight.setPosition(FLIP_DROP_POSITION);
    }

    public double flipGetPosition() {
        return servoFlipLeft.getPosition();
    }
    public double boxGetPosition() {
        return boxRotation.getPosition();
    }

    public void setBoxPosition(double positionBox) {
        boxRotation.setPosition(positionBox);

    }

    public void setFlipPosition(double positionFlip) {
        servoFlipLeft.setPosition(positionFlip);
        servoFlipRight.setPosition(positionFlip);
    }

    class TakeDropHelper extends Thread {
        boolean needWorkTake = false;
        boolean needWorkDrop = false;

        private ElapsedTime timer = new ElapsedTime();
        public void run () {
            while (!isInterrupted()) {
               if (needWorkTake) {
                   flipTakePixel();
                   timer.reset();
                   while (timer.milliseconds() < FLIP_TIME );
                   boxTakePixel();
                   needWorkTake = false;
               } else if (needWorkDrop) {
                   flipDropPixel();
                   timer.reset();
                   while (timer.milliseconds() < FLIP_TIME );
                   boxDropPixel();
                   needWorkDrop = false;
               }
            }
        }

    }


}
