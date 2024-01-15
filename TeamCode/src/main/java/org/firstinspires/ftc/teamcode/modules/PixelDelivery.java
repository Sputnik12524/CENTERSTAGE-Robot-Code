package org.firstinspires.ftc.teamcode.modules;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;


@Config
public class PixelDelivery {
//Уменьшить переменную-привести к закрытому положению
//Увеличение переменной-привести к открытому положению
    public static double DOOR_CLOSED_POSITION = 0.475;
    public static double DOOR_FULL_OPEN_POSITION = 1;
    public static double DOOR_HALF_OPENED_POSITION = 0.7;
    public static double BOX_ROTATION_DROP_POSITION = 0.955;
    public static double BOX_ROTATION_TAKE_POSITION = 0.46;
    public static double FLIP_TAKE_POSITION = 0.065;
    public static double FLIP_DROP_POSITION = 0.92;

    public static double FLIP_TIME = 100;

    private final Servo servoDoor;
    private final Servo boxRotationLeft;
    private final Servo boxRotationRight;
    private final Servo servoFlipLeft;
    private final Servo servoFlipRight;

    private final Servo forPurple;

    private final LinearOpMode opMode;
    private final TakeDropHelper takeDropHelper;

    public PixelDelivery(LinearOpMode opMode) {
        this.opMode = opMode;
        this.servoDoor = opMode.hardwareMap.servo.get("servoDoor");
        this.boxRotationLeft = opMode.hardwareMap.servo.get("boxRotationLeft");
        this.boxRotationRight = opMode.hardwareMap.servo.get("boxRotationRight");
        this.servoFlipLeft = opMode.hardwareMap.servo.get("servoFlipLeft");
        this.servoFlipRight = opMode.hardwareMap.servo.get("servoFlipRight");

        this.forPurple = opMode.hardwareMap.servo.get("forPurple");

        this.servoFlipRight.setDirection(Servo.Direction.REVERSE);
        this.boxRotationRight.setDirection(Servo.Direction.REVERSE);
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
        boxRotationLeft.setPosition(BOX_ROTATION_TAKE_POSITION);
        boxRotationRight.setPosition(BOX_ROTATION_TAKE_POSITION);
    }

    public void boxDropPixel() {
        boxRotationLeft.setPosition(BOX_ROTATION_DROP_POSITION);
        boxRotationRight.setPosition(BOX_ROTATION_DROP_POSITION);
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
        return boxRotationLeft.getPosition();
    }

    public void setBoxPosition(double positionBox) {
        boxRotationLeft.setPosition(positionBox);
        boxRotationRight.setPosition(positionBox);

    }

    public void setFlipPosition(double positionFlip) {
        servoFlipLeft.setPosition(positionFlip);
        servoFlipRight.setPosition(positionFlip);
    }
    public void setForPurple(double position){
        forPurple.setPosition(position);
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
