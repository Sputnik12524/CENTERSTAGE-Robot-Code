package org.firstinspires.ftc.teamcode.modules.recognition;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;
import static org.firstinspires.ftc.teamcode.modules.recognition.Position.LEFT;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvPipeline;


@Config
public class Recognition extends OpenCvPipeline {

    public static int LEFT_REGION_X = 10, MIDDLE_REGION_X = 120, RIGHT_REGION_X = 270, LEFT_REGION_Y = 155, RIGHT_REGION_Y = 155, MIDDLE_REGION_Y = 130;
    public static int THRESH_CB = 150, THRESH_CR = 120, MAXVAL_CB = 255, MAXVAL_CR = 255;
    public static int ALLIANCE_COLOR = 0; //  0 - red alliance
    //  1 - blue alliance
    public static int OUTPUT = 1;
    private static final int VALUE_FOR_RECOGNITION = 200; // В Cb - синий, в Cr - красный
    public Position position = Position.LEFT;
    private Point regionMiddleTopLeftAnchorPoint = new Point(MIDDLE_REGION_X, MIDDLE_REGION_Y);
    private Point regionRightTopLeftAnchorPoint = new Point(RIGHT_REGION_X, RIGHT_REGION_Y);
    private int REGION_WIDTH = 25;
    private int REGION_HEIGHT = 25;

    private Point regionMiddle_pointA = new Point(
            regionMiddleTopLeftAnchorPoint.x,
            regionMiddleTopLeftAnchorPoint.y);
    private Point regionMiddle_pointB = new Point(
            regionMiddleTopLeftAnchorPoint.x + 110,
            regionMiddleTopLeftAnchorPoint.y + REGION_HEIGHT);
    private Point regionRight_pointA = new Point(
            regionRightTopLeftAnchorPoint.x,
            regionRightTopLeftAnchorPoint.y);
    private Point regionRight_pointB = new Point(
            regionRightTopLeftAnchorPoint.x + REGION_WIDTH,
            regionRightTopLeftAnchorPoint.y + REGION_HEIGHT);
    private static final Scalar BLUE = new Scalar(0, 0, 255);
    private static final Scalar GREEN = new Scalar(0, 255, 0);
    /*
     * основные главные аргументы для определения расположения  и размера регионов выборки
    /*
     * точки, которые определяют регион прямоугольников, полученные из вышеуказанных значений
     * Точка А- левый верхний угол прямоугольника, Точка В- правый нижний угол прямоугольник
     * присваиваем  им формулы по осям
     *   ----------------------------------
     *   |                                  |
     *   | (0,0) Point A                    |
     *   |                                  |
     *   |                                  |
     *   |                                  |
     *   |                                  |
     *   |                                  |
     *   |                  Point B (70,50) |
     *   ----------------------------------
     *
     */

    // используемые переменные в Mat

    private Mat regionMiddle, regionRight;
    private Mat cb = new Mat();
    private Mat bin = new Mat();
    private Mat cr = new Mat();
    private int avgMiddle, avgRight;
    private final Telemetry telemetry;

    public Recognition(LinearOpMode opMode) {
        telemetry = opMode.telemetry;
    }

    void inputToCb(Mat input) {
        Imgproc.cvtColor(input, cb, Imgproc.COLOR_RGB2YCrCb);
        Core.extractChannel(cb, bin, 2);
        Imgproc.threshold(bin, bin, THRESH_CB, MAXVAL_CB, Imgproc.THRESH_BINARY);
    }

    void inputToCr(Mat input) {
        Imgproc.cvtColor(input, cr, Imgproc.COLOR_RGB2YCrCb);
        Core.extractChannel(cr, bin, 2);
        Imgproc.threshold(bin, bin, THRESH_CR, MAXVAL_CR, Imgproc.THRESH_BINARY_INV);
    }

    void setAllianceColor(int alliance) {
        ALLIANCE_COLOR = alliance;
    }

    public void depictingRegions(Position team_element, Mat input) {
        switch (team_element) {
            case MIDDLE:
                Imgproc.rectangle(
                        input,
                        regionMiddle_pointA,
                        regionMiddle_pointB,
                        GREEN,
                        -1);
                break;
            case RIGHT:
                Imgproc.rectangle(
                        input,
                        regionRight_pointA,
                        regionRight_pointB,
                        GREEN,
                        -1);
                break;

        }
    }

    @Override
    public void init(Mat firstFrame) {
        if (ALLIANCE_COLOR == 1)
            inputToCb(firstFrame);
        else inputToCr(firstFrame);
        /*
         * ПодМаты  - это постоянная ссылка на область родительского (главного)
         * буфера. Любые изменения в младшем элементе влияют на родителя и наоборот
         */
    }

    public Mat processFrame(Mat input) {
        /*
         * Сначала мы преобразуем в цветовое пространство YCrCb из цветового пространства RGB.
         * так как в цветовом пространстве RGB, цветности и
         * яркости переплетаются. В YCrCb цветность и яркость разделены.
         * YCrCb - это 3-канальное цветовое пространство, как и RGB. 3 канала YCrCb:
         * Y, канал яркости (который, по сути, просто черно-белое изображение),
         * Канал Cr, который записывает отличие от красного, и канал Cb,
         * который регистрирует отличие от синего. Потому что цветность и яркость
         * не относится к YCrCb, код видения, написанный для поиска определенных значений
         * в каналах Cr / Cb не сильно пострадают от различных
         * интенсивностей света, поскольку эта разница, скорее всего, будет
         * отражено в канале Y.
         * После преобразования в YCrCb мы извлекаем только второй канал,
         * Канал Cb. Мы делаем это потому, что утки ярко-желтые и контрастные.
         * Затем мы берем среднее значение пикселя в 3 разных регионах на этом Cb
         * канал. эти  3 региона означает, что там точно есть кольцо
         *потом мы сравниваем
         */
        /*
         *Получить канал Cb входного кадра после преобразования в YCrCb
         */
        if (ALLIANCE_COLOR == 1)
            inputToCb(input);
        else inputToCr(input);        /*
         * Вычислите среднее значение пикселя для каждой области ПодМата.
         * берем  среднее значение для одноканального буфера, поэтому значение
         * нам нужен индекс 0. Мы также могли взять среднее
         * значение в пикселях 3-канального изображения и указанное значение
         * здесь по индексу 2
         */

        regionMiddle = bin.submat(new Rect(regionMiddle_pointA, regionMiddle_pointB));
        regionRight = bin.submat(new Rect(regionRight_pointA, regionRight_pointB));

        avgMiddle = (int) Core.mean(regionMiddle).val[0];
        avgRight = (int) Core.mean(regionRight).val[0];

        Imgproc.rectangle(
                input, // буфер для рисования
                regionMiddle_pointA, // первая точка, которая распознает прямоугольник
                regionMiddle_pointB, // вторая точка, которая распознает прямоугольник
                BLUE, // Цвет, которым нарисован прямоугольник
                2); // Толщина линий прямоугольника

        Imgproc.rectangle(
                input, // буфер для рисования
                regionRight_pointA, // первая точка, которая распознает прямоугольник
                regionRight_pointB, // вторая точка, которая распознает прямоугольник
                BLUE, // Цвет, которым нарисован прямоугольник
                2); // Толщина линий прямоугольника
        if (avgRight < VALUE_FOR_RECOGNITION && avgMiddle > VALUE_FOR_RECOGNITION) {
            position = Position.MIDDLE;
        } else {
            if (avgRight > VALUE_FOR_RECOGNITION && avgMiddle < VALUE_FOR_RECOGNITION) {
                position = Position.RIGHT;
            } else
                position = Position.LEFT;
        }
        depictingRegions(position, input);
        if (OUTPUT == 0)
            return bin;
        else
            return input;
    }

    public void editRec(Gamepad gamepad1) {
        if (gamepad1.a) {
            MAXVAL_CB += 1;
            MAXVAL_CR += 1;
        }
        if (gamepad1.b) {
            MAXVAL_CB -= 1;
            MAXVAL_CR -= 1;
        }
        if (gamepad1.x) {
            THRESH_CR += 1;
            THRESH_CB += 1;
        }
        if (gamepad1.y) {
            THRESH_CR -= 1;
            THRESH_CB -= 1;
        }
        if (gamepad1.dpad_left) {
            OUTPUT = 1;
        }
        if (gamepad1.dpad_right) {
            OUTPUT = 0;
        }
        telemetry.addData("position is ", getAnalysis());
        telemetry.addData("avgMiddle is", getAvgMiddle());
        telemetry.addData("avgRight is", getAvgRight());
        telemetry.addData("threshCb", THRESH_CB);
        telemetry.addData("threshCr", THRESH_CR);
        telemetry.addData("maxvalCb", MAXVAL_CB);
        telemetry.addData("maxvalCr", MAXVAL_CR);
        telemetry.update();

    }

    public Position getAnalysis() {
        return position;
    }

    public int getAvgMiddle() {
        return avgMiddle;
    }

    public int getAvgRight() {
        return avgRight;
    }

}



