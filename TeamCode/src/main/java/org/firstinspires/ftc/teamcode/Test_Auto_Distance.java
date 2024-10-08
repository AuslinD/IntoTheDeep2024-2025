package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@Autonomous (name = "Auto_Distance")
public class Test_Auto_Distance extends LinearOpMode {
    TestMethods automethods;
    @Override
    public void runOpMode() throws InterruptedException {
        automethods = new TestMethods(this);
        waitForStart();

        automethods.distance(.5, 1000);
        automethods.distance(.5, -1000);

    }
}
