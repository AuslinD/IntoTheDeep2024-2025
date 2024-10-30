package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.Gamepad;

public class TeleOpMethods {


    OpMode opMode;

    Robot robot;
    static double up1p, up2p;
    static double armAngle;
    private static boolean ignoreBounds = false;

    public TeleOpMethods(OpMode opMode) {
        this.opMode = opMode;
        robot = new Robot(opMode);
    }

    public void teleOpControls(Gamepad gamepad1, Gamepad gamepad2)
    {
        // call each method
        drivetrain(gamepad1, gamepad2);
        verticalLift(gamepad1, gamepad2);
        claw(gamepad1, gamepad2);
    }
    public void drivetrain(Gamepad gamepad1, Gamepad gamepad2) {
        double FLP, FRP, BRP, BLP;
        FLP = 0;
        FRP = 0;
        BRP = 0;
        BLP = 0;

        // Moves forward and back
        if (Math.abs(gamepad1.left_stick_y) > 0.1){
            FLP += -gamepad1.left_stick_y;
            FRP += -gamepad1.left_stick_y;
            BLP += -gamepad1.left_stick_y;
            BRP += -gamepad1.left_stick_y;
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
            FLP += gamepad1.left_stick_x;
            FRP -= gamepad1.left_stick_x;
            BLP -= gamepad1.left_stick_x;
            BRP += gamepad1.left_stick_x;
        }
        // slow robot down holding trigger
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

        opMode.telemetry.addData("vertical", robot.drivetrain.fl.getCurrentPosition());
        opMode.telemetry.addData("horizontal", robot.drivetrain.fr.getCurrentPosition());
        opMode.telemetry.update();




    }
    public void verticalLift (Gamepad gamepad1, Gamepad gamepad2){
        /*if(gamepad1.dpad_left) ignoreBounds = true;

        if(gamepad1.dpad_right){
            ignoreBounds = false;
            robot = new Robot(robot.teleOpMode);
        }*/
        // move life up and down
        int multiplier = 120;
        if (Math.abs(gamepad2.left_stick_y) > 0.1){
            up1p += -gamepad2.left_stick_y * multiplier;

            up2p = up1p;

            if(up1p < -10/* && !ignoreBounds*/)
            {
                up1p = -10;
            }
            else if(up1p > 3600/* && !ignoreBounds*/){
                up1p = 3600;
            }
            robot.lift.goUpOrDown((int)(up1p));
        }
        else{
            up1p = robot.lift.leftSlide.getCurrentPosition();
        }
    }

    public void claw(Gamepad gamepad1, Gamepad gamepad2){
        // claw grabby and release
        if(gamepad2.a){
            robot.claw.grabClaw();
        }
        if(gamepad2.b){
            robot.claw.releaseClaw();
        }
        // claw angle up and down
        if(gamepad2.dpad_down){
            armAngle -= .05;
        }
        if(gamepad2.dpad_up){
            armAngle += .05;
        }
        if (armAngle > 1){
            armAngle = 1;
        }
        if (armAngle < -1){
            armAngle = -1;
        }
        robot.claw.setPosition(armAngle);
    }



}
