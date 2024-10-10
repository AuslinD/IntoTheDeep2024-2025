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
    public void drivetrain(Gamepad gamepad1, Gamepad gamepad2) {
        double FLP, FRP, BRP, BLP;
        FLP = 0;
        FRP = 0;
        BRP = 0;
        BLP = 0;

        // Moves forward and back
        if (Math.abs(gamepad1.left_stick_y) > 0.1){
            FLP += gamepad1.left_stick_y;
            FRP += gamepad1.left_stick_y;
            BLP += gamepad1.left_stick_y;
            BRP += gamepad1.left_stick_y;
        }


        //Moves right and left
        if (Math.abs(gamepad1.right_stick_x) > 0.1) {
            FLP += gamepad1.right_stick_x;
            FRP -= gamepad1.right_stick_x;
            BLP += gamepad1.right_stick_x;
            BRP -= gamepad1.right_stick_x;
        }



        // Strafe right and left
        if (Math.abs(gamepad1.left_stick_x) > 0.1) {
            FLP -= gamepad1.left_stick_x;
            FRP += gamepad1.left_stick_x;
            BLP += gamepad1.left_stick_x;
            BRP -= gamepad1.left_stick_x;
        }

        if (gamepad1.right_trigger > 0.2){
            FLP /= 2;
            FRP /= 2;
            BLP /= 2;
            BRP /= 2;
        }


        robot.drivetrain.fl.setPower(FLP);
        robot.drivetrain.fr.setPower(FRP);
        robot.drivetrain.bl.setPower(BLP);
        robot.drivetrain.br.setPower(BRP);


    }




}
