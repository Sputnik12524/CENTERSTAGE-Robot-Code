package org.firstinspires.ftc.teamcode.modules;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;

/**
 * Класс датчика BNO055: акселерометра, гироскопа
 */
public class ImuSensor {
    private final BNO055IMU imu;
    private Orientation angles;

    /**
     * Параметры сенсора
     * Bandwidth - частота обновления датчика
     * Unit - формат данных датчика
     * PowerMode - режим работы системы питания
     *
     * @param _opMode ссылка на opMode вызвавший конструктором
     */
    public ImuSensor(LinearOpMode _opMode) {
        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        // Accelerometer
        parameters.accelBandwidth = BNO055IMU.AccelBandwidth.HZ1000;
        parameters.accelPowerMode = BNO055IMU.AccelPowerMode.NORMAL;
        parameters.accelUnit = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
        parameters.accelRange = BNO055IMU.AccelRange.G4;
        // Gyroscope
        parameters.gyroBandwidth = BNO055IMU.GyroBandwidth.HZ523;
        parameters.gyroPowerMode = BNO055IMU.GyroPowerMode.ADVANCED;
        parameters.angleUnit = BNO055IMU.AngleUnit.DEGREES; // градусы
        parameters.gyroRange = BNO055IMU.GyroRange.DPS125;

        parameters.calibrationData = BNO055IMU.CalibrationData.deserialize(parameters.calibrationDataFile);

        parameters.useExternalCrystal = true;

        //parameters.accelerationIntegrationAlgorithm = new JustLoggingAccelerationIntegrator();
        //Position pos = new Position(DistanceUnit.MM, 0, 0, 0, 0);
        //Velocity vel = new Velocity(DistanceUnit.MM, 0, 0, 0, 0);
        //Acceleration accel = new Acceleration(DistanceUnit.MM, 0, 0, 0, 0);
        //parameters.accelerationIntegrationAlgorithm.initialize(parameters, imu.getPosition(), imu.getVelocity());

        //parameters.accelerationIntegrationAlgorithm.update(imu.getLinearAcceleration());

        parameters.mode = BNO055IMU.SensorMode.NDOF_FMC_OFF;

        imu = _opMode.hardwareMap.get(BNO055IMU.class, "imu");
        imu.initialize(parameters);
        //imu.startAccelerationIntegration(imu.getPosition(), imu.getVelocity(), 1000);

        angles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
    }

    /**
     * Метод получения градусов
     *
     * @return кол-во градусов
     */
    public double getAngles() {
        angles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
        return (AngleUnit.DEGREES.normalize(angles.firstAngle));
    }
    public double getRadians() {
        angles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.RADIANS);
        return (AngleUnit.RADIANS.normalize(angles.firstAngle));
    }

    // получение угловой скорости
    public double getAngularVelocity() {
        return imu.getAngularVelocity().zRotationRate;
    }

    // получение статуса калибровки
    public int getCalibrationStatus() { // получение статуса калибровки
        return imu.getCalibrationStatus().calibrationStatus;
    }

    // получение ускорения по вертикальной оси
    public double getOverallAccel() {
        return imu.getOverallAcceleration().zAccel;
    }

    // получение ускорения без учёта ускорения свободного падения по вертикальной оси
    public double getLinearAccel() {
        return imu.getLinearAcceleration().zAccel;
    }
}
