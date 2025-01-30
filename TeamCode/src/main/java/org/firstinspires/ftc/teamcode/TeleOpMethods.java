package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

import java.util.Arrays;

public class TeleOpMethods {


    OpMode opMode;

    Robot robot;
    double up1p, up2p;
    double armAngle = 0;

    double armPos = 0;

    //ElapsedTime diffyTime;

    double rightDiffyChange = 0;
    double leftDiffyChange = 0;

    double lastLeftDiffyPos;
    double lastRightDiffyPos;

    double diffyTargetChange = 0;

    PID leftDiffyPID = new PID(.4, 0, 0, 0);
    PID rightDiffyPID = new PID(.4, 0, 0, 0);

    ElapsedTime intakeElapsedTime;


    private static boolean ignoreBounds = false;

    public TeleOpMethods(OpMode opMode) {
        this.opMode = opMode;
        robot = new Robot(opMode);

        armAngle = 0.05;
        up1p = 0;

        intakeElapsedTime = new ElapsedTime();

        lastLeftDiffyPos = robot.claw.leftDiffyEncoder.getVoltage();
        lastRightDiffyPos = robot.claw.rightDiffyEncoder.getVoltage();
    }

    public void teleOpControls(Gamepad gamepad1, Gamepad gamepad2)
    {
        fieldCentric(gamepad1, gamepad2);
        verticalLift(gamepad1, gamepad2);
        //claw(gamepad1, gamepad2);
        arm(gamepad1, gamepad2);
        diffyClaw(gamepad1, gamepad2);
        intakeMove(gamepad1, gamepad2);
        calcDiffy();


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

        int multiplier = 45;



        /* old version of lift
        if (Math.abs(gamepad2.right_stick_y) > 0.1){
            up1p += -gamepad2.right_stick_y * multiplier;

            if(gamepad2.right_stick_y > .1){
                robot.lift.power = .6;
            }
            else{
                robot.lift.power = 1;
            }

            up2p = up1p;

            if(up1p < 50)
            {
                up1p = 50;
            }
            else if(up1p > 2500){
                up1p = 2500;
            }
        }
        else{
            //up1p = robot.lift.rightSlide.getCurrentPosition();
        }
         */
        if(gamepad2.right_stick_y < -.1){
            up1p = 2500;
        }
        else if(gamepad2.right_stick_y > .1){
            up1p = 50;
        }
        else{
            up1p = robot.lift.leftSlide.getCurrentPosition();
        }

        robot.lift.goUpOrDown((int)(up1p));

    }

    public void horizontalSlide(Gamepad gamepad1, Gamepad gamepad2){
        //lstick
    }

    public void intake(Gamepad gamepad1, Gamepad gamepad2){
        //leftTriggerIntakeIn
        //button y IntakeReversed


    }

    public void claw(Gamepad gamepad1, Gamepad gamepad2){
        // claw grabby and release
        /*
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
         */
    }

    public void diffyClaw(Gamepad gamepad1, Gamepad gamepad2){
        if (gamepad2.right_trigger > 0){
            robot.claw.setClawPos(.355);
        }else{
            robot.claw.setClawPos(.065);
        }



        if(gamepad2.right_bumper){
            diffyTargetChange += .1;
        }

        if(gamepad2.left_bumper){
            diffyTargetChange -= .1;
        }

        /*

        double power = .08;

        if(Math.abs(leftDiffyChange - diffyTargetChange) > .06){
            power = .06;
        }
        else if (Math.abs(leftDiffyChange - diffyTargetChange) > .03 ){
            power = .03;
        }

         */


        leftDiffyPID.setTarget(diffyTargetChange);
        double leftPower = -leftDiffyPID.loop(leftDiffyChange, 0);

        if(leftPower >= .25) leftPower = .25;
        if(leftPower <= -.25) leftPower = -.25;

        rightDiffyPID.setTarget(diffyTargetChange);
        double rightPower = -rightDiffyPID.loop(rightDiffyChange,0);
        if(rightPower >= .25) rightPower = .25;
        if(rightPower <= -.25) rightPower = -.25;

        opMode.telemetry.addData("left, right Diffy Power", Arrays.toString(new double[]{leftPower, rightPower}));

        if(leftDiffyChange > diffyTargetChange + .03) {
            robot.claw.leftDiffy.setPower(leftPower);
        }
        else if(leftDiffyChange < diffyTargetChange - .03){
            robot.claw.leftDiffy.setPower(leftPower);
        }
        else{
            robot.claw.leftDiffy.setPower(0);
        }


        //Double.isNaN(rightPower);

        if(rightDiffyChange > diffyTargetChange + .03){
            robot.claw.rightDiffy.setPower(rightPower);
        }
        else if(rightDiffyChange < diffyTargetChange - .03){
            robot.claw.rightDiffy.setPower(rightPower);
        }
        else{
            robot.claw.rightDiffy.setPower(0);
        }

        /*if(gamepad2.left_stick_y > .1){
            diffyOffSet += .02;
        }
        else if (gamepad2.left_stick_y < -.1){
            diffyOffSet -= .02;
        }

         */


        //robot.claw.setRightDiffyPosition(diffyPos);
        //robot.claw.setLeftDiffyPosition(1-diffyPos);
    }

    public void intakeMove(Gamepad gamepad1, Gamepad gamepad2){
        if(gamepad1.a){
            robot.intake.intakeAngle.setPosition(.4);
        }
        else if (gamepad2.left_trigger > 0){
            robot.intake.intakeMotor.setPower(1);
            //intake extension port 5 expansion hub
            //intake updown port 5 control hub
            robot.intake.setIntakeExtension(.5);
            if(intakeElapsedTime.milliseconds() > 500){
                robot.intake.intakeAngle.setPosition(0.1);
            }

        }
        else if (gamepad2.right_trigger > 0){
            robot.intake.intakeMotor.setPower(1);
            //intake extension port 5 expansion hub
            //intake updown port 5 control hub
            robot.intake.setIntakeExtension(.5);
            robot.intake.intakeAngle.setPosition(0.1);


        }
        else if (gamepad2.y){
            robot.intake.intakeMotor.setPower(-1);

        }
        else{
            intakeElapsedTime.reset();
            robot.intake.setIntakeExtension(0); //meant to be 0
            robot.intake.intakeMotor.setPower(0);
            robot.intake.intakeAngle.setPosition(0.3);
        }
    }

    public void arm(Gamepad gamepad1, Gamepad gamepad2){
        if(gamepad2.dpad_up){
            armAngle += .05;
        }

        if(gamepad2.dpad_down) {
            armAngle -= .05;
        }

        if(gamepad2.a){
            armAngle = 0;
        }
        //.16? straight

        robot.claw.setArmPos(armAngle);
    }

    public void macro(Gamepad gamepad1, Gamepad gamepad2){
        if(gamepad2.b){
            robot.intake.setIntakeExtension(0);
        }
    }

    public void calcDiffy(){
        if(Math.abs(lastLeftDiffyPos - robot.claw.leftDiffyEncoder.getVoltage()) > 2.6){
            if(lastLeftDiffyPos > robot.claw.leftDiffyEncoder.getVoltage()){
                leftDiffyChange += 3.3 - lastLeftDiffyPos + robot.claw.leftDiffyEncoder.getVoltage();
            }
            else{
                leftDiffyChange -= 3.3 - (robot.claw.leftDiffyEncoder.getVoltage() - lastLeftDiffyPos);
            }
        }
        else{
            leftDiffyChange += robot.claw.leftDiffyEncoder.getVoltage() - lastLeftDiffyPos;
        }

        if(Math.abs(lastRightDiffyPos - robot.claw.rightDiffyEncoder.getVoltage()) > 2.2){
            if(lastRightDiffyPos > robot.claw.rightDiffyEncoder.getVoltage()){
                rightDiffyChange -= 3.3 - lastRightDiffyPos + robot.claw.rightDiffyEncoder.getVoltage();
            }
            else{
                rightDiffyChange += 3.3 - (robot.claw.rightDiffyEncoder.getVoltage() - lastRightDiffyPos);
            }
        }
        else{
            rightDiffyChange -= robot.claw.rightDiffyEncoder.getVoltage() - lastRightDiffyPos;
        }

        lastLeftDiffyPos = robot.claw.leftDiffyEncoder.getVoltage();
        lastRightDiffyPos = robot.claw.rightDiffyEncoder.getVoltage();
    }

    public void telemetry(){
        opMode.telemetry.addData("lift wanted", up1p);
        opMode.telemetry.addData("diffyTargetChange", diffyTargetChange);
        opMode.telemetry.addData("leftDiffyChange", leftDiffyChange);
        opMode.telemetry.addData("rightDiffyChange", rightDiffyChange);
        //opMode.telemetry.addData("diffyOffset", diffyOffSet);
        opMode.telemetry.addData("vertical", robot.drivetrain.fl.getCurrentPosition());
        opMode.telemetry.addData("horizontal", robot.drivetrain.fr.getCurrentPosition());
        opMode.telemetry.addData("firstangle", robot.imu.getRobotYawPitchRollAngles());
        opMode.telemetry.addData("arm", armAngle);
        opMode.telemetry.update();
    }






}
