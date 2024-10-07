package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

public class Robot {
    Drivetrain drivetrain;


    public Robot (LinearOpMode opMode) {
        drivetrain = new Drivetrain(opMode);
    }

    public Robot (OpMode opMode) {
        drivetrain = new Drivetrain(opMode);
    }
}
