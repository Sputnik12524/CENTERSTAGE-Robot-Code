package org.firstinspires.ftc.teamcode.modules;


import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotor.RunMode;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

import static java.lang.Math.PI;

import static java.lang.Math.acos;
import static java.lang.Math.sin;
import static java.lang.Math.sqrt;

import org.firstinspires.ftc.robotcore.external.Telemetry;

@Config
public class Drivetrain {
    private double sumErr;
    private double prevErr = 0;
    private final LinearOpMode opMode;

    private final DcMotor leftFrontDrive;
    private final DcMotor rightFrontDrive;
    private final DcMotor leftBackDrive;
    private final DcMotor rightBackDrive;

    private final ImuSensor imu;// Гироскоп
    private final Telemetry tm;

    public static double kX = 1;// Мощность по оси вперёд-назад
    public static double kY = 1;// Мощность по оси влево-вправо
    public static double kR = 1;// Мощность по оси вращения
    public static double rotatePower = 0.6;// Мощность вращения метода rotate

    public static final double GYRO_COURSE_TOLERANCE = 2;
    public static double slow = 0.65; /*отвечает за замедление скорости езды робота. Если хотим ускорить робота, повышаем её.*/
    public static double R_SLOW = 1;
    public static double ANGULAR_VELOCITY_TOLERANCE = 1;
    public static double P_ANGLE_TOLERANCE = 0;
    public static double ANGLES_TOLERANCE = 0.5;
    public static double D_TOLERANCE = 8;
    public static double COURSEPID_MAX_TIME = 5;
    private static double kP = 0.0225;
    private static double kD = 0.012;
    private static double kI = 0.017;
    private final static double ROTATE_ACCURACY = 1;
    ElapsedTime calcTime = new ElapsedTime();
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
        imu = new ImuSensor(opMode);
        //mh = new MetryHandler(opMode);
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

    /**
     * Метод установления напряжения на моторы
     *
     * @param power - коэффициент мощности мотора [0..1]
     */
    private void setPower(double power) {
        leftFrontDrive.setPower(power);
        rightFrontDrive.setPower(power);
        leftBackDrive.setPower(power);
        leftFrontDrive.setPower(power);
    }

    /**
     * Метод установления мощности на все моторы через массив (для выссчитанной мощности)
     *
     * @param powers - мощности моторов
     */
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
                (-y - x + r), (y + x + r),
                (-y + x + r), (y - x + r)};
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
        double angle = imu.getAngles();
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

    private double calculatePIDPower(double d){
            double power;
            double err = 0;
            if (Math.abs(d- imu.getAngles()) <= 180)
                err = d - imu.getAngles();
            else
                err = d - imu.getAngles() - Math.signum(d- imu.getAngles())*360;
            sumErr = sumErr + calcTime.milliseconds() * err;
            power = kP * err +sumErr * kI + kD * (err - prevErr)/calcTime.seconds();
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
        while (angleDiff(imu.getAngles(), d) < ROTATE_ACCURACY && opMode.opModeIsActive()){
            driveRawPower(0, 0, calculatePIDPower(d));
            opMode.telemetry.addData("Angle:", imu.getAngles());
            opMode.telemetry.addData("d:", d);
            opMode.telemetry.update();
        }
        stop();
    }
    /**
     * Поворот робота на курс(с) от его положения инициализации с помощью PID-регулятора
     * course - [0, 360] - заданное значение
     * kP, kI, kD - коэффициенты PID
     * d0 - ошибка
     * d1 - ошибка спустя время обновления показаний датчиков
     * dDerivative - производная ошибки
     * dIntegral - интеграл ошибки
     * u - управляющее воздействие
     * calcTime - период про
     * setTime - время установки на курс
     * deltaTime - время обновления показаний
     *
     * param course - курс в градусах, заданное значение
     */
    /*public void coursePID(double course) { // прошлогодний
        course = (course + 180) % 360;
        double courseSign = 1;//Math.signum(course - imu.getAngles());
        double d0 = angleDiff(course, imu.getAngles());
        double d1 = angleDiff(course, imu.getAngles());

        ElapsedTime calcTime = new ElapsedTime();
        ElapsedTime setTime = new ElapsedTime();
        ElapsedTime deltaTime = new ElapsedTime();

        double dIntegral = 0;
        double dDerivative = (d1 - d0) / (calcTime.nanoseconds() * 10e-9);
        double u = kP * (d1 - P_ANGLE_TOLERANCE) + kI * dIntegral + kD * dDerivative;

        setTime.reset();
        calcTime.reset();
        deltaTime.reset();

        while (opMode.opModeIsActive() &&
                ((Math.abs(d0) > ANGLES_TOLERANCE) ||
                        (Math.abs(imu.getAngularVelocity()) > ANGULAR_VELOCITY_TOLERANCE))) {
            deltaTime.reset();
            d1 = angleDiff(course, imu.getAngles());
            dDerivative = (d1 - d0) / (calcTime.nanoseconds() * 10e-9);
            dIntegral += dFrontier(d1) * calcTime.nanoseconds() * 10e-9;

            u = kP * (d1 - P_ANGLE_TOLERANCE) + kI * dIntegral + kD * dDerivative;
            driveRawPower(0, 0, u * courseSign);
            d0 = course - imu.getAngles();

            calcTime.reset();


            if (setTime.seconds() > COURSEPID_MAX_TIME) {
                break;
            }

        }
        stop();
        setTime.reset();
    }

    public double angleDiff(double a, double b) {
        double d = a - b;
        if (d < -180) {
            d += 360;
        } else if (d > 180) {
            d -= 360;
        }
        return d;
    }*/

    public double dFrontier(double d) {
        return (Math.abs(d) < D_TOLERANCE) ? d : 0;
    }

    /**
     * Метод проверки моторов.
     * каждая переменная отвечает за свой мотор.
     */
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

    public void driveFlawless(double x, double y, double r) {
        double angle = acos(x) * (y < 0 ? -1 : 1) + imu.getRadians();
        double capacity = sqrt(x * x + y * y);
        double p = PI / 4;
        double u1 = capacity * sin(angle - p) + r;
        double u2 = capacity * sin(angle + p) + r;
        double[] powers = {u1, u2, u1, u2};
        setPower(powers);
    }
}