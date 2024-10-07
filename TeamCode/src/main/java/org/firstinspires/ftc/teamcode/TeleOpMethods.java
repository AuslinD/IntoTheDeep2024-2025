package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.Gamepad;

public class TeleOpMethods {

    Robot robot;

    public TeleOpMethods(OpMode opMode) {
        robot = new Robot(opMode);
    }

    public void teleOpControls(Gamepad gamepad1, Gamepad gamepad2)
    {
        drivetrain(gamepad1, gamepad2);
    }
    public void drivetrain(Gamepad gamepad1, Gamepad gamepad2)
    {
        robot.drivetrain.fl.setPower(1);
    }



}
