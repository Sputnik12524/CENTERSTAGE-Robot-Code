package org.firstinspires.ftc.teamcode.modules;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.modules.PID.Parameters;
import org.firstinspires.ftc.teamcode.modules.PID.Regulator;

@Config
public class Drivetrain {
    public static final double GYRO_COURSE_TOLERANCE = 2;
    public static double slow = 0.65; /*отвечает за замедление скорости езды робота. Если хотим ускорить робота, повышаем её.*/
    public static double R_SLOW = 1;
    public static double ANGULAR_VELOCITY_TOLERANCE = 1;
    public static double P_ANGLE_TOLERANCE = 0;
    public static double ANGLES_TOLERANCE = 0.5;
    public static double D_TOLERANCE = 8;
    public static double COURSEPID_MAX_TIME = 5;
    public static double kP = 0.0225;
    public static double kD = 0.012;
    public static double kI = 0.017;
    private final DcMotor leftFrontDrive;
    private final DcMotor rightFrontDrive;
    private final DcMotor leftBackDrive;
    private final DcMotor rightBackDrive;
    private final LinearOpMode opMode;
    private final MetryHandler mh;
    private final ImuSensor imu;
    private final Parameters imuPIDparam;
    private Regulator imuPID;

    /**
     * Конструктор: инициализирует моторы робота и OpMode:
     * lf - левый передний
     * lb - левый задний
     * rf - правый передний
     * rb - правый задний
     *
     * @param _opMode ссылка на opMode, вызвавший конструктор
     */
    public Drivetrain(LinearOpMode _opMode) {
        opMode = _opMode;

        HardwareMap hm = opMode.hardwareMap;


        leftFrontDrive = hm.get(DcMotor.class, "lf");
        leftBackDrive = hm.get(DcMotor.class, "lb");
        rightFrontDrive = hm.get(DcMotor.class, "rb");
        rightBackDrive = hm.get(DcMotor.class, "rf");
        imu = new ImuSensor(opMode);
        imuPIDparam = new Parameters(0.225, 0.012, 0.017,
                1, 1,
                5, 8,
                this::driveR,
                () -> imu.getAngles(), () -> imu.getAngularVelocity(), true);
        Regulator imuPID = new Regulator(opMode, imuPIDparam);
        mh = new MetryHandler(_opMode, "D");
    }

    /**
     * Метод подачи напряжения на моторы
     *
     * @param x перемещение слева-направо
     * @param y перемещение назад-вперёд
     * @param r перемещение по часовой стрелки
     */
    public void drive(double x, double y, double r) {
        leftFrontDrive.setPower((-x + y - r * R_SLOW) * slow);
        rightFrontDrive.setPower((x - y - r * R_SLOW) * slow);
        leftBackDrive.setPower((x + y - r * R_SLOW) * slow);
        rightBackDrive.setPower((-x - y - r * R_SLOW) * slow);

    }

    public void drive(double x, double y, double r, double slow) {
        leftFrontDrive.setPower((-x + y + r) * slow);
        rightFrontDrive.setPower((x - y + r) * slow);
        leftBackDrive.setPower((x + y + r) * slow);
        rightBackDrive.setPower((-x - y + r) * slow);
    }

    /**
     * Метод вращения колёсной базы
     *
     * @param r - мощность вращения
     */
    public void driveR(double r) {
        leftFrontDrive.setPower(r);
        rightFrontDrive.setPower(r);
        leftBackDrive.setPower(r);
        rightBackDrive.setPower(r);
    }

    /**
     * Устанавливает режим mode на каждый мотор соотвественно
     *
     * @param mode Режим для установки
     */
    public void setMode(DcMotor.RunMode mode) {
        leftFrontDrive.setMode(mode);
        rightFrontDrive.setMode(mode);
        leftBackDrive.setMode(mode);
        rightBackDrive.setMode(mode);
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
        drive(0, 0, 0);
    }

    /**
     * Метод движения по времени вперед
     *
     * @param ms время движения
     */
    public void driveFront(double y, long ms) {
        drive(0, y, 0);
        opMode.sleep(ms);
        stop();
    }

    /**
     * Метод движения по времени назад
     *
     * @param ms время движения
     */
    public void driveBack(double y, long ms) {
        drive(0, -y, 0);
        opMode.sleep(ms);
        stop();
    }

    /**
     * Метод движения по времени вправо
     *
     * @param ms время движения
     */
    public void driveRight(double x, long ms) {
        drive(x, 0, 0);
        opMode.sleep(ms);
        stop();
    }

    /**
     * Метод движения по времени налево
     *
     * @param ms время движения
     */
    public void driveLeft(double x, long ms) {
        drive(-x, 0, 0);
        opMode.sleep(ms);
        stop();
    }

    public void addDashAndTelePositions() {
        mh.addLine("Motors: Port, Position, Powers");
        mh.addData("LeftFront", leftFrontDrive.getCurrentPosition());
        mh.addData("LeftFront", rightFrontDrive.getCurrentPosition());
        mh.addData("LeftFront", leftBackDrive.getCurrentPosition());
        mh.addData("LeftFront", rightBackDrive.getCurrentPosition());
        mh.update();

        opMode.telemetry.update();
    }

    public void encoder(double tick1, double power) {
        int position1 = leftFrontDrive.getCurrentPosition();
        int position2 = rightFrontDrive.getCurrentPosition();
        int position3 = leftBackDrive.getCurrentPosition();
        int position4 = rightBackDrive.getCurrentPosition();
        drive(0, power, 0);
        Telemetry telemetry = FtcDashboard.getInstance().getTelemetry();
        while (!(leftFrontDrive.getPower() == 0 &&
                rightFrontDrive.getPower() == 0 &&
                leftBackDrive.getPower() == 0 &&
                rightBackDrive.getPower() == 0) &&
                opMode.opModeIsActive()) {
            int leftFrontDifference = Math.abs(position1 - leftFrontDrive.getCurrentPosition());
            int rightFrontDifference = Math.abs(position2 - rightFrontDrive.getCurrentPosition());
            int leftBackDifference = Math.abs(position3 - leftBackDrive.getCurrentPosition());
            int rightBackDifference = Math.abs(position4 - rightBackDrive.getCurrentPosition());
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

            mh.addData("Правый передний:", leftFrontDifference);
            mh.addData("Левый передний:", rightFrontDifference);
            mh.addData("Левый задний:", leftBackDifference);
            mh.addData("Правый задний:", rightBackDifference);
            mh.update();
        }
        rightBackDrive.setPower(0);
        rightFrontDrive.setPower(0);
    }

    /**
     * Поворот робота на курс(с) от его положения инициализации
     *
     * @param c - курс в градусах
     */
    public void course(double c) {
        while ((((imu.getAngles() < c - GYRO_COURSE_TOLERANCE) || (imu.getAngles() > c + GYRO_COURSE_TOLERANCE)) && opMode.opModeIsActive())) {
            drive(0, 0, 0.5);
            opMode.telemetry.addData("Angle:", imu.getAngles());
            opMode.telemetry.addData("Course:", c);
            opMode.telemetry.update();
        }
        stop();
    }

    /**
     * Поворот робота на градус(d) от его положения в момент
     *
     * @param d - градус
     */
    public void rotate(double d) {
        d = -d; //меняем знак из-за
        double dSign = -Math.signum(d); // берём направление поворота: налево или направо по знаку
        d += imu.getAngles(); // прибавляем к повороту наше положение в градусах
        if (d < -180) { // если сумма меньше -180, для её совместимости с гироскопом прибавляем 360 градусов
            d += 360;
        }
        if (d > 180) { // если сумма больше 180 градусов, для её совместимости с гироскопом вычитаем 360 градусов
            d -= 360;
        }
        while (((imu.getAngles() < d - GYRO_COURSE_TOLERANCE) ||
                (imu.getAngles() > d + GYRO_COURSE_TOLERANCE)) && opMode.opModeIsActive()) {
            drive(0, 0, 0.6 * dSign); // поворачиваем с учётом направлением поворота
            opMode.telemetry.addData("Angle:", imu.getAngles());
            opMode.telemetry.addData("Rotate:", d);
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
     * @param course - курс в градусах, заданное значение
     */
    public void coursePID(double course) {
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
            drive(0, 0, u * courseSign, 1);
            d0 = course - imu.getAngles();

            calcTime.reset();


//            dm.addData("SettingTime", setTime.seconds());
//            dm.addData("deltaTime", deltaTime.nanoseconds() * 10e-9);
//            dm.addLine("");
//            dm.addData("SetCourse", course);
//            dm.addData("ImuCourse", imu.getAngles());
//            dm.addData("D0", d0);
//            dm.addData("D1", d1);
//            dm.addLine("");
//            dm.addData("dDerivative", dDerivative);
//            dm.addData("dIntegral", dIntegral);
//            dm.addLine("");
//            dm.addData("U", u);
//            dm.addData("uP", kP * d1);
//            dm.addData("uI", kI * dIntegral);
//            dm.addData("uD", kD * dDerivative);
//            dm.update();
            if (setTime.seconds() > COURSEPID_MAX_TIME) {
                break;
            }

        }
        stop();
        setTime.reset();
    }

    public void rotatePID(double course) {
        course += imu.getAngles();
        if (course > 360) {
            course -= 360;
        } else if (course < 0) {
            course += 360;
        }
        coursePID(course);
    }

    public double angleDiff(double a, double b) {
        double d = a - b;
        if (d < -180) {
            d += 360;
        } else if (d > 180) {
            d -= 360;
        }
        return d;
    }

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

    public void driftLeftFront(double x, double y, double r) {
        rightFrontDrive.setPower((x - y - r) * slow);
        leftBackDrive.setPower((x + y - r) * slow);
        rightBackDrive.setPower((-x - y - r) * slow);
    }

    public void driftRightFront(double x, double y, double r) {
        leftFrontDrive.setPower((-x + y - r) * slow);
        leftBackDrive.setPower((x + y - r) * slow);
        rightBackDrive.setPower((-x - y - r) * slow);
    }

    public void driftLeftBack(double x, double y, double r) {
        leftFrontDrive.setPower((-x + y - r) * slow);
        rightFrontDrive.setPower((x - y - r) * slow);
        rightBackDrive.setPower((-x - y - r) * slow);
    }

    public void driftRightBack(double x, double y, double r) {
        leftFrontDrive.setPower((-x + y - r) * slow);
        leftBackDrive.setPower((x + y - r) * slow);
        rightFrontDrive.setPower((x - y - r) * slow);

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void setCoursePIDModule(double setValue) {
        imuPID.set(setValue);
    }


}

