package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@Autonomous (name = "Auto_Distance")
public class Test_Auto_Distance extends LinearOpMode {
    AutoMethods automethods;
    @Override
    public void runOpMode() throws InterruptedException {
        automethods = new AutoMethods(this);
        waitForStart();

        automethods.encoderDrive(.2, 6000);
        automethods.toHeading(.5, 180);
        automethods.encoderDrive(.2, 6000);


    }
}
