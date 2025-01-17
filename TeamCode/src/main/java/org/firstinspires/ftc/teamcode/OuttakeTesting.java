package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name = "OuttakeTesting", group = "test")
public class OuttakeTesting extends OpMode {

    DiffyClaw outtake;

    double armPos = 0;
    double clawPos = 0;

    double rightDiffyChange = 0;
    double leftDiffyChange = 0;

    double lastLeftDiffyPos;
    double lastRightDiffyPos;

    //double leftPos = 0;
    //double leftDiffyPos = 0;
    //double rightPos = 0;

    @Override
    public void init() {
        outtake = new DiffyClaw(this);

    }

    @Override
    public void loop() {


        if(gamepad2.right_trigger > .1){
            outtake.rightDiffy.setPower(.5);
        }
        else{
            outtake.rightDiffy.setPower(0);
        }

        if(gamepad2.left_trigger > .1){
            outtake.leftDiffy.setPower(.5);
            //outtake.setRightDiffyPosition(.5);
        }
        else{
            outtake.leftDiffy.setPower(0);
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


        if(Math.abs(lastLeftDiffyPos - outtake.leftDiffyEncoder.getVoltage()) > 2){
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

        if(Math.abs(lastRightDiffyPos - outtake.rightDiffyEncoder.getVoltage()) > 2){

        }
        else{
            rightDiffyChange += outtake.rightDiffyEncoder.getVoltage() - lastRightDiffyPos;
        }


        lastLeftDiffyPos = outtake.leftDiffyEncoder.getVoltage();
        lastRightDiffyPos = outtake.rightDiffyEncoder.getVoltage();

        telemetry.addData("rightDiffyAnalog", outtake.rightDiffyEncoder.getVoltage());
        telemetry.addData("leftDiffyAnalog", outtake.leftDiffyEncoder.getVoltage());
        telemetry.addData("armPos", armPos);
        telemetry.addData("clawPos", clawPos);
        telemetry.update();
        //telemetry.addData()
    }
}
