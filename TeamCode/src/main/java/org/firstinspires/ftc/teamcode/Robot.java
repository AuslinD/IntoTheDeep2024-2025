package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

public class Robot {
    Drivetrain drivetrain;
    Lift lift;


    public Robot (LinearOpMode opMode) {
        drivetrain = new Drivetrain(opMode);
        lift = new Lift(opMode);

    }

    public Robot (OpMode opMode) {
        drivetrain = new Drivetrain(opMode);
        lift = new Lift(opMode);
    }
}
