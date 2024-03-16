package org.firstinspires.ftc.teamcode.modules;


import static java.lang.Math.sin;
import static java.lang.Math.cos;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotor.RunMode;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.YawPitchRollAngles;

@Config
public class Drivetrain {
    public static final double GYRO_COURSE_TOLERANCE = 2;
    private double sumErr;

    private IMU imu = null;
    private double prevErr = 0;
    private final LinearOpMode opMode;

    private final DcMotor leftFrontDrive;
    private final DcMotor rightFrontDrive;
    private final DcMotor leftBackDrive;
    private final DcMotor rightBackDrive;

    private final ImuSensor imuu;// Гироскоп
    private final Telemetry tm;

    public static double kX = 1;// Мощность по оси вперёд-назад
    public static double kY = 1;// Мощность по оси влево-вправо
    public static double kR = 1;// Мощность по оси вращения
    public static double rotatePower = 0.6;// Мощность вращения метода rotate

    public static double slow = 0.9; /*отвечает за замедление скорости езды робота. Если хотим ускорить робота, повышаем её.*/
    public static double D_TOLERANCE = 8;
    public static double COURSEPID_MAX_TIME = 5;

    public static double kP = 0.0225;
    public static double kD = 0;
    public static double kI = 0;


    private final static double ROTATE_ACCURACY = 1;
    private ElapsedTime calcTime = new ElapsedTime();


    /**
     * Конструктор: инициализирует моторы робота и OpMode:
     * lf - левый передний
     * lb - левый задний
     * rf - правый передний
     * rb - правый задний
     *
     * @param opMode ссылка на opMode, вызвавший конструктор
     */
    public Drivetrain(LinearOpMode opMode) {
        this.opMode = opMode;
        HardwareMap hm = opMode.hardwareMap;
        leftFrontDrive = hm.get(DcMotor.class, "lf");
        leftBackDrive = hm.get(DcMotor.class, "lb");
        rightFrontDrive = hm.get(DcMotor.class, "rb");
        rightBackDrive = hm.get(DcMotor.class, "rf");
        imu = hm.get(IMU.class, "imu");
        rightBackDrive.setDirection(DcMotor.Direction.REVERSE);
        RevHubOrientationOnRobot.LogoFacingDirection logoFacingDirection = RevHubOrientationOnRobot.LogoFacingDirection.BACKWARD;
        RevHubOrientationOnRobot.UsbFacingDirection usbFacingDirection = RevHubOrientationOnRobot.UsbFacingDirection.UP;
        RevHubOrientationOnRobot orientationOnRobot = new RevHubOrientationOnRobot(logoFacingDirection, usbFacingDirection);
        imuu = new ImuSensor(opMode);
        imu.resetYaw();
        tm = opMode.telemetry;
    }

    /**
     * Метод установления режима мотора
     *
     * @param mode - режим мотора
     */
    public void setMode(RunMode mode) {
        leftFrontDrive.setMode(mode);
        rightFrontDrive.setMode(mode);
        leftBackDrive.setMode(mode);
        rightBackDrive.setMode(mode);
    }

    /**
     * Метод установки режима на моторы в отдельности
     *
     * @param leftFrontMode  - режим левого переднего мотора
     * @param rightFrontMode - режим правого переднего мотора
     * @param leftBackMode   - режим левого заднего мотора
     * @param rightBackMode  - режим правого заднего мотора
     */
    public void setMode(RunMode leftFrontMode, RunMode rightFrontMode,
                        RunMode leftBackMode, RunMode rightBackMode) {
        leftFrontDrive.setMode(leftFrontMode);
        rightFrontDrive.setMode(rightFrontMode);
        leftBackDrive.setMode(leftBackMode);
        rightBackDrive.setMode(rightBackMode);
    }
    private void setPower(double[] powers) {
        leftFrontDrive.setPower(powers[0]);
        rightFrontDrive.setPower(powers[1]);
        leftBackDrive.setPower(powers[2]);
        rightBackDrive.setPower(powers[3]);
        tm.addData("lF", leftFrontDrive.getPower());
        tm.addData("LB", leftBackDrive.getPower());
        tm.addData("RF", rightFrontDrive.getPower());
        tm.addData("RB", rightBackDrive.getPower());

    }

    /**
     * Метод высчитывания мощности моторов на меканум базе
     *
     * @param x - перемещение вперёд-назад [0..1]
     * @param y - перемещение влево-вправо [0..1]
     * @param r - вращение по часовой-против часовой [0..1]
     * @return массив мощностей для моторов
     */
    private double[] calculatePower(double x, double y, double r) {
        return new double[]{
                (-y*slow - x*slow + r*slow), (y*slow + x*slow + r*slow),
                (-y*slow + x*slow + r*slow), (y*slow - x*slow + r*slow)};
    }

    /**
     * Метод управления колёсной базой через мощности без коэффициентов
     * Метод управления колёсной базой через мощности с коэффициентами
     *
     * @param x - перемещение вперёд-назад [0..1]
     * @param y - перемещение влево-вправо [0..1]
     * @param r - вращение по часовой-против часовой [0..1]
     */
    public void driveRawPower(double x, double y, double r) {
        setPower(calculatePower(x, y, r));
    }


    public void driveCoeffPower(double x, double y, double r) {
        setPower(calculatePower(x * kX, y * kY, r * kR));
    }

    /**
     * Состояние работы мотора
     *
     * @return Истина если моторы заняты и подается достаточная минимальная сила
     */
    public boolean isBusy() {
        boolean busy = leftFrontDrive.isBusy() &&
                rightFrontDrive.isBusy() &&
                leftBackDrive.isBusy() &&
                rightBackDrive.isBusy();

        boolean pows = (Math.abs(leftFrontDrive.getPower()) +
                Math.abs(rightFrontDrive.getPower()) +
                Math.abs(leftBackDrive.getPower()) +
                Math.abs(rightBackDrive.getPower())) > 0.05;

        return busy && pows;
    }

    /**
     * Устанавливает нулевую силу на моторы, останавливая их
     */
    public void stop() {
        setPower(new double[]{0, 0, 0, 0});
    }

    public void dataTelePositions() {
        tm.addLine("Motors: Port, Position, Powers");
        tm.addData("LeftFront", leftFrontDrive.getCurrentPosition());
        tm.addData("LeftFront", rightFrontDrive.getCurrentPosition());
        tm.addData("LeftFront", leftBackDrive.getCurrentPosition());
        tm.addData("LeftFront", rightBackDrive.getCurrentPosition());
        tm.update();

        opMode.telemetry.update();
    }

    public void strafeRight() {
        driveRawPower(-0.33, 0, 0.05);
    }

    public void strafeLeft() {
        driveRawPower(0.33, 0, -0.05);
    }

    public void driveEncoderSide(double tick1, double powerX) {
        driveEncoder(tick1, powerX, 0);
    }

    public void driveEncoder(double tick1, double powerY) {
        driveEncoder(tick1, 0, powerY);
    }

    public void driveEncoder(double tick1, double powerX, double powerY) {
        int position1 = leftFrontDrive.getCurrentPosition();
        int position2 = rightFrontDrive.getCurrentPosition();
        int position3 = leftBackDrive.getCurrentPosition();
        int position4 = rightBackDrive.getCurrentPosition();
        Telemetry telemetry = FtcDashboard.getInstance().getTelemetry();
        double angle = imuu.getAngles();
        driveRawPower(powerX, powerY, 0);
        while (!(leftFrontDrive.getPower() == 0 &&
                rightFrontDrive.getPower() == 0 &&
                leftBackDrive.getPower() == 0 &&
                rightBackDrive.getPower() == 0) &&
                opMode.opModeIsActive()) {
            int leftFrontDifference = Math.abs(position1 - leftFrontDrive.getCurrentPosition());
            int rightFrontDifference = Math.abs(position2 - rightFrontDrive.getCurrentPosition());
            int leftBackDifference = Math.abs(position3 - leftBackDrive.getCurrentPosition());
            int rightBackDifference = Math.abs(position4 - rightBackDrive.getCurrentPosition());
            driveRawPower(powerX, powerY, calculatePIDPower(angle));
            if (leftFrontDifference >= tick1) {
                leftFrontDrive.setPower(0);
            }
            if (rightFrontDifference >= tick1) {
                rightFrontDrive.setPower(0);
            }
            if (leftBackDifference >= tick1) {
                leftBackDrive.setPower(0);
            }
            if (rightBackDifference >= tick1) {
                rightBackDrive.setPower(0);
            }

            tm.addData("Правый передний:", leftFrontDifference);
            tm.addData("Левый передний:", rightFrontDifference);
            tm.addData("Левый задний:", leftBackDifference);
            tm.addData("Правый задний:", rightBackDifference);
            tm.addData("tick1:", tick1);
            tm.addData("tick1:", tick1);
            tm.addData("tick1:", tick1);
            tm.addData("tick1:", tick1);
            tm.update();
        }
        rightBackDrive.setPower(0);
        rightFrontDrive.setPower(0);
    }

    private double angleDiff(double a, double b) {
        double d = a - b;
        if (d < -180) {
            d += 360;
        } else if (d > 180) {
            d -= 360;
        }
        return d;
    }

    private double calculatePIDPower(double d) {
        double power;
        double err = 0;
        if (Math.abs(d - imuu.getAngles()) <= 180)
            err = d - imuu.getAngles();
        else
            err = d - imuu.getAngles() - Math.signum(d - imuu.getAngles()) * 360;
        sumErr = sumErr + calcTime.milliseconds() * err;
        power = kP * err + sumErr * kI + kD * (err - prevErr) / calcTime.seconds();
        calcTime.reset();
        prevErr = err;
        return power;
    }

    /**
     * Поворот робота на градус(d) от его положения в момент
     *
     * @param d - градус
     */
    public void rotate(double d) {
        while (Math.abs(angleDiff(imuu.getAngles(), d)) < ROTATE_ACCURACY && opMode.opModeIsActive()) {
            driveRawPower(0, 0, calculatePIDPower(d));
            opMode.telemetry.addData("Angle:", imuu.getAngles());
            opMode.telemetry.addData("d:", d);
            opMode.telemetry.update();
        }
        stop();
    }

    public void driveFlawless(double x, double y, double r) {
        double angle = imuu.getRadians();
        double _x= x * cos(angle) - y * sin(angle);
        double _y =x * sin(angle) + y * cos(angle);
        setPower(calculatePower(_x, _y, r));
    }
    public void checkMotors(boolean lf, boolean rf, boolean lb, boolean rb) {
        if (lf) {
            leftFrontDrive.setPower(slow);
        }
        if (rf) {
            rightFrontDrive.setPower(slow);
        }
        if (lb) {
            leftBackDrive.setPower(slow);
        }
        if (rb) {
            rightBackDrive.setPower(slow);
        } else {
            stop();
        }

    }
    public double getHeading(){
        YawPitchRollAngles orientation = imu.getRobotYawPitchRollAngles();
        return orientation.getYaw(AngleUnit.DEGREES);
    }
    public void turnDrive(double angle, double power){
        rightFrontDrive.setPower(power);
        leftFrontDrive.setPower(-power);
        rightBackDrive.setPower(power);
        leftBackDrive.setPower(-power);
        imu.resetYaw();
        if(opMode.opModeIsActive() && Math.abs(getHeading()) >= angle){
            stop();
            opMode.sleep(100);
        }

    }
    public double getAngles(){
        return imuu.getAngles();

    }

}