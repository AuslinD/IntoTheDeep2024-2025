package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

public class Test_Auto_Distance extends LinearOpMode {
    TestMethods automethods;
    @Override
    public void runOpMode() throws InterruptedException {
        automethods = new TestMethods(this);
        waitForStart();

        automethods.distance(.5, 1000);

    }
}
