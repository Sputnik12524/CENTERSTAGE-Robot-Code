package org.firstinspires.ftc.teamcode.opmodes.examples;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

/*
 * Этот файл содержит в себе пример итеративного (нелинейного) сценария.
 * Сценарий это программа, которая запускается в автономном или телеуправляемом режиме матча FTC.
 * Этот конкретный сценарий служит шаблоном для создания примеров исполнения модулей,
 * и материалом, помогающим понять структуру программы.*/
/*
 * @TeleOp(group= "Examples", name="ExampleExample") - телеуправляемый
 * @Autonomous(group= "Examples", name="ExampleExample") - автономный
 * @Disabled - не используемый
 * @Deprecated - устаревший
 * @Config - настраиваемый через Dashboard
 */
@Disabled
public class ExampleExample extends LinearOpMode {
    // единожды выполняемые действия до запуска программы
    // здесь следует создавать переменные и константы для сценария

    @Override
    public void runOpMode() {
        // единожды выполняемые действия до инициализации
        // здесь следует создавать экземпляры модулей
        while (opModeInInit()) {
            // единожды выполняемые действия во время инициализации

        }
        // единожды выполняемые действия после инициализации, но до запуска сценария

        waitForStart();
        // единожды выполняемые действия после запуска сценария


        while (opModeIsActive()) {
            // единожды выполняемые действия после запуска сценария

        }

    }

}
