package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@Autonomous(name="forwardtest")
public class TestAuto extends LinearOpMode {
    TestMethods automethods;
    @Override
    public void runOpMode() throws InterruptedException {
        automethods = new TestMethods(this);
        waitForStart();

        automethods.drive(.5, 1000);

    }

}
