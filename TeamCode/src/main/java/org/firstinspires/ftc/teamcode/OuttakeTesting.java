package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name = "OuttakeTesting", group = "test")
public class OuttakeTesting extends OpMode {

    DiffyClaw outtake;

    double armPos = 0;
    double clawPos = 0;

    @Override
    public void init() {
        outtake = new DiffyClaw(this);

    }

    @Override
    public void loop() {


        if(gamepad2.right_trigger > .1){
            outtake.setRightDiffyPower(.5);
        }
        else{
            outtake.setLeftDiffyPower(0);
        }

        if(gamepad2.left_trigger > .1){
            outtake.setLeftDiffyPower(.5);
        }
        else{
            outtake.setLeftDiffyPower(0);
        }

        if(gamepad2.dpad_up){
            armPos += .05;
        }
        if(gamepad2.dpad_down){
            armPos -= .05;
        }

        if(gamepad2.right_bumper){
            clawPos += .05;
        }
        if(gamepad2.left_bumper){
            clawPos -= .05;
        }

        outtake.setArmPos(armPos);
        outtake.setClawPos(clawPos);



        telemetry.addData("rightDiffy", outtake.rightDiffyEncoder.getVoltage() / 3.3 * 360);
        telemetry.addData("leftDiffy", outtake.leftDiffyEncoder.getVoltage() / 3.3 * 360);
        telemetry.addData("armPos", armPos);
        telemetry.addData("clawPos", clawPos);


        telemetry.update();
        //telemetry.addData()
    }
}
