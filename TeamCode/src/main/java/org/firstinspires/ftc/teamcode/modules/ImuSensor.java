package org.firstinspires.ftc.teamcode.modules;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;

/**
 * Класс датчика BNO055: акселерометра, гироскопа
 */
public class ImuSensor {
    private final IMU imu;
    private Orientation angles;
    private IMU.Parameters parameters;
    /**
     * Параметры сенсора
     * Bandwidth - частота обновления датчика
     * Unit - формат данных датчика
     * PowerMode - режим работы системы питания
     *
     * @param _opMode ссылка на opMode вызвавший конструктором
     */
    public ImuSensor(LinearOpMode _opMode) {
        imu = _opMode.hardwareMap.get(IMU.class, "imu");
        imu.initialize(parameters);
        angles = imu.getRobotOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
    }

    /**
     * Метод получения градусов
     *
     * @return кол-во градусов
     */
    public double getAngles() {

        angles = imu.getRobotOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
        return (AngleUnit.DEGREES.normalize(angles.firstAngle));
    }

    public double getRadians() {
        angles = imu.getRobotOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.RADIANS);
        return (AngleUnit.RADIANS.normalize(angles.firstAngle));
    }
    public void init(){
        imu.resetYaw();
    }

    // получение угловой скорости
    public double getAngularVelocity() {
        return imu.getRobotAngularVelocity(AngleUnit.RADIANS).zRotationRate;
    }
}
