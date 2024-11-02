package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@Autonomous(name = "DONOTUSE", group = "actual")
public class RedForwardPark extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {

        AutoMethods autoMethods = new AutoMethods(this);

        waitForStart();


        autoMethods.encoderDrive(.25, 15000);

        //deliver
        autoMethods.setVerticalLiftPosition(200);
        // release claw


        autoMethods.setVerticalLiftPosition(0);


        autoMethods.encoderDrive(.25, -15000);

        autoMethods.toHeading(.25, -90);

        autoMethods.encoderDrive(.25, 27000);

    }
}
