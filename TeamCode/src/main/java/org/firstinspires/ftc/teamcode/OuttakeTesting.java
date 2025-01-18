package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name = "OuttakeTesting", group = "test")
public class OuttakeTesting extends OpMode {

    DiffyClaw outtake;

    double armPos = 0;
    double clawPos = 0;

    //double rightDiffyInit;
    //double leftDiffyInit;

    double rightDiffyChange = 0;
    double leftDiffyChange = 0;

    double lastLeftDiffyPos;
    double lastRightDiffyPos;

    double leftError = 0;

    double diffyTargetChange = 0;
    //double leftPos = 0;
    //double leftDiffyPos = 0;
    //double rightPos = 0;

    @Override
    public void init() {
        outtake = new DiffyClaw(this);
        //IMPORTANT
        lastLeftDiffyPos = outtake.leftDiffyEncoder.getVoltage();
        lastRightDiffyPos = outtake.rightDiffyEncoder.getVoltage();

        //leftDiffyInit = lastLeftDiffyPos;
        //rightDiffyInit = lastRightDiffyPos;
    }

    @Override
    public void loop() {


        if(gamepad2.right_trigger > .1){
            //outtake.rightDiffy.setPower(.15);
            diffyTargetChange += .05;
        }
        else if(gamepad1.right_trigger > .1){
            //outtake.rightDiffy.setPower(-.15);
        }
        else{
            //outtake.rightDiffy.setPower(0);
        }

        if(gamepad2.left_trigger > .1){
            diffyTargetChange -= .05;
            //outtake.leftDiffy.setPower(-.15);
            //outtake.setRightDiffyPosition(.5);
        }
        else if(gamepad1.left_trigger > .1){
            //outtake.leftDiffy.setPower(.15);
        }
        else{
            //outtake.leftDiffy.setPower(0);
        }

        if(gamepad2.dpad_up){
            armPos += .005;
            if (armPos > 1){
                armPos = 1;
            }
            else if (armPos < 0){
                armPos = 0;
            }
        }
        if(gamepad2.dpad_down){
            armPos -= .005;
        }

        if(gamepad2.right_bumper){
            clawPos += .005;
        }
        if(gamepad2.left_bumper){
            clawPos -= .005;
        }

        outtake.setArmPos(armPos);
        outtake.setClawPos(clawPos);



        //telemetry.addData("rightDiffy", rightPos);
        //telemetry.addData("leftDiffy", leftPos);


        if(Math.abs(lastLeftDiffyPos - outtake.leftDiffyEncoder.getVoltage()) > 2.2){
            if(lastLeftDiffyPos > outtake.leftDiffyEncoder.getVoltage()){
                leftDiffyChange += 3.3 - lastLeftDiffyPos + outtake.leftDiffyEncoder.getVoltage();
            }
            else{
                leftDiffyChange -= 3.3 - (outtake.leftDiffyEncoder.getVoltage() - lastLeftDiffyPos);
            }
        }
        else{
            leftDiffyChange += outtake.leftDiffyEncoder.getVoltage() - lastLeftDiffyPos;
        }

        if(Math.abs(lastRightDiffyPos - outtake.rightDiffyEncoder.getVoltage()) > 2.2){
            if(lastRightDiffyPos > outtake.rightDiffyEncoder.getVoltage()){
                rightDiffyChange -= 3.3 - lastRightDiffyPos + outtake.rightDiffyEncoder.getVoltage();
            }
            else{
                rightDiffyChange += 3.3 - (outtake.rightDiffyEncoder.getVoltage() - lastRightDiffyPos);
            }
        }
        else{
            rightDiffyChange -= outtake.rightDiffyEncoder.getVoltage() - lastRightDiffyPos;
        }


        if(leftDiffyChange > diffyTargetChange + .02) {
            outtake.leftDiffy.setPower(.07);
        }
        else if(leftDiffyChange < diffyTargetChange - .02){
            outtake.leftDiffy.setPower(-.07);
        }
        else{
            outtake.leftDiffy.setPower(0);
        }


        if(rightDiffyChange > diffyTargetChange + .02){
            outtake.rightDiffy.setPower(.07);
        }
        else if(rightDiffyChange < diffyTargetChange - .02){
            outtake.rightDiffy.setPower(-.07);
        }
        else{
            outtake.rightDiffy.setPower(0);
        }

        lastLeftDiffyPos = outtake.leftDiffyEncoder.getVoltage();
        lastRightDiffyPos = outtake.rightDiffyEncoder.getVoltage();

        telemetry.addData("rightDiffyAnalog", outtake.rightDiffyEncoder.getVoltage());
        telemetry.addData("leftDiffyAnalog", outtake.leftDiffyEncoder.getVoltage());

        telemetry.addData("diffyTargetChange", diffyTargetChange);

        telemetry.addData("rightChange", rightDiffyChange);
        telemetry.addData("leftChange", leftDiffyChange);


        telemetry.addData("armPos", armPos);
        telemetry.addData("clawPos", clawPos);
        telemetry.update();
        //telemetry.addData()
    }
}
