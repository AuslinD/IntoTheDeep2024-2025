package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@Autonomous(name = "forwardToSubmersible", group = "actual")
public class shortParkAuto extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {

        AutoMethods autoMethods = new AutoMethods(this);

        waitForStart();

        autoMethods.encoderDrive(.20, 15000);

    }
}
