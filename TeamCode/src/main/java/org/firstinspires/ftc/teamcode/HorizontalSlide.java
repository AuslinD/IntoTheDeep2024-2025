package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;

public class HorizontalSlide {

    OpMode opMode;
    DcMotor leftHorizontal, rightHorizontal;
    double power = .8;
    public HorizontalSlide(OpMode opMode){
        this.opMode = opMode;
        leftHorizontal = opMode.hardwareMap.get(DcMotorEx.class, "leftHorizontal");
        rightHorizontal = opMode.hardwareMap.get(DcMotorEx.class, "rightHorizontal");

        // This will Reset the encoder once it gets to the fully extent point?
        //leftSlide.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightHorizontal.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        // I am not sure what this does but it was in the verticle one so i kept it
        leftHorizontal.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightHorizontal.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        leftHorizontal.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightHorizontal.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        leftHorizontal.setDirection(DcMotor.Direction.FORWARD);
        rightHorizontal.setDirection(DcMotor.Direction.REVERSE);

        /// Sets position back to start
        leftHorizontal.setTargetPosition(0);
        rightHorizontal.setTargetPosition(0);

        ///runs to set position
        leftHorizontal.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightHorizontal.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        /// Says what set power is reffering to
        leftHorizontal.setPower(power);
        rightHorizontal.setPower(power);
    }

}
