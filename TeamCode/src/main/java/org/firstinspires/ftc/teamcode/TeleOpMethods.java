package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

public class TeleOpMethods {


    OpMode opMode;

    Robot robot;
    static double up1p, up2p;
    static double armAngle;
    private static boolean ignoreBounds = false;

    public TeleOpMethods(OpMode opMode) {
        this.opMode = opMode;
        robot = new Robot(opMode);

        armAngle = .3;

    }

    public void teleOpControls(Gamepad gamepad1, Gamepad gamepad2)
    {
        fieldCentric(gamepad1, gamepad2);
        verticalLift(gamepad1, gamepad2);
        claw(gamepad1, gamepad2);
        telemetry();
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
    }

    public void fieldCentric(Gamepad gamepad1, Gamepad gamepad2){
        double y = -gamepad1.left_stick_y; // Remember, Y stick value is reversed
        double x = gamepad1.left_stick_x;
        double rx = gamepad1.right_stick_x;

        // This button choice was made so that it is hard to hit on accident,
        // it can be freely changed based on preference.
        // The equivalent button is start on Xbox-style controllers.
        if (gamepad1.options) {
            robot.imu.resetYaw();
        }

        double botHeading = robot.imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.RADIANS);

        // Rotate the movement direction counter to the bot's rotation
        double rotX = x * Math.cos(-botHeading) - y * Math.sin(-botHeading);
        double rotY = x * Math.sin(-botHeading) + y * Math.cos(-botHeading);

        rotX = rotX * 1.1;  // Counteract imperfect strafing

        // Denominator is the largest motor power (absolute value) or 1
        // This ensures all the powers maintain the same ratio,
        // but only if at least one is out of the range [-1, 1]
        double denominator = Math.max(Math.abs(rotY) + Math.abs(rotX) + Math.abs(rx), 1);
        double frontLeftPower = (rotY + rotX + rx) / denominator;
        double backLeftPower = (rotY - rotX + rx) / denominator;
        double frontRightPower = (rotY - rotX - rx) / denominator;
        double backRightPower = (rotY + rotX - rx) / denominator;

        if (gamepad1.right_trigger > 0.2){
            frontLeftPower /= 2;
            frontRightPower /= 2;
            backRightPower /= 2;
            backLeftPower /= 2;
        }

        robot.drivetrain.fl.setPower(frontLeftPower);
        robot.drivetrain.fr.setPower(frontRightPower);
        robot.drivetrain.bl.setPower(backLeftPower);
        robot.drivetrain.br.setPower(backRightPower);
    }

    public void verticalLift (Gamepad gamepad1, Gamepad gamepad2){
        /*if(gamepad1.dpad_left) ignoreBounds = true;

        if(gamepad1.dpad_right){
            ignoreBounds = false;
            robot = new Robot(robot.teleOpMode);
        }*/

        int multiplier = 5;

        if (Math.abs(gamepad2.left_stick_y) > 0.1){
            up1p += -gamepad2.left_stick_y * multiplier;

            if(gamepad2.left_stick_y > .1){
                robot.lift.power = .6;
            }
            else{
                robot.lift.power = 1;
            }

            up2p = up1p;

            if(up1p < 15/* && !ignoreBounds*/)
            {
                up1p = 15;
            }
            else if(up1p > 750/* && !ignoreBounds*/){
                up1p = 750;
            }
            robot.lift.goUpOrDown((int)(up1p));
        }
        else{
            //up1p = robot.lift.rightSlide.getCurrentPosition();
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
        if(gamepad2.dpad_up){
            armAngle -= .01;
        }
        if(gamepad2.dpad_down){
            armAngle += .01;
        }
        if (armAngle > 1){
            armAngle = 1;
        }
        if (armAngle < -1){
            armAngle = -1;
        }
        robot.claw.setPosition(armAngle);
    }

    public void telemetry(){
        opMode.telemetry.addData("lift wanted", up1p);
        opMode.telemetry.addData("vertical", robot.drivetrain.fl.getCurrentPosition());
        opMode.telemetry.addData("horizontal", robot.drivetrain.fr.getCurrentPosition());
        opMode.telemetry.addData("firstangle", robot.imu.getRobotYawPitchRollAngles());
        opMode.telemetry.addData("arm", armAngle);
        opMode.telemetry.update();
    }






}
