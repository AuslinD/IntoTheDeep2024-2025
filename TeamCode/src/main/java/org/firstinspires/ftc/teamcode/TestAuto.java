package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@Autonomous(name="test")
public class TestAuto extends LinearOpMode {
    TestMethods automethods;
    @Override
    public void runOpMode() throws InterruptedException {
        automethods = new TestMethods(this);
        waitForStart();

        automethods.turn(.5, -90);

    }

}
