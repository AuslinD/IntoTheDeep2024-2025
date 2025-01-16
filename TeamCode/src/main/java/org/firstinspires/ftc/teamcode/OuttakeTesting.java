package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name = "OuttakeTesting", group = "test")
public class OuttakeTesting extends OpMode {

    DiffyClaw outtake;

    double armPos = 0;
    double clawPos = 0;

    double leftPos = 0;
    double leftDiffyPos = 0;
    double rightPos = 0;

    @Override
    public void init() {
        outtake = new DiffyClaw(this);

    }

    @Override
    public void loop() {


        if(gamepad2.right_trigger > .1){
            outtake.setRightDiffyPosition(.5);
        }
        else{
            outtake.setRightDiffyPosition(0);
        }

        if(gamepad2.left_trigger > .1){
            outtake.setLeftDiffyPosition(.5);
            //outtake.setRightDiffyPosition(.5);
        }
        else{
            outtake.setLeftDiffyPosition(0);
        }

        if(gamepad2.dpad_up){
            armPos += .005;
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
        telemetry.addData("rightDiffyAnalog", outtake.rightDiffyEncoder.getVoltage());
        telemetry.addData("leftDiffyAnalog", outtake.leftDiffyEncoder.getVoltage());
        telemetry.addData("armPos", armPos);
        telemetry.addData("clawPos", clawPos);


        telemetry.update();
        //telemetry.addData()
    }
}
