package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;

public class HorozontalSlide {

    OpMode opMode;
    DcMotor leftHorozontol, rightHorozontol;
    double power = .8;
    public Lift(OpMode opMode){
        this.opMode = opMode;
        leftHorozontol = opMode.hardwareMap.get(DcMotorEx.class, "leftHorozontol");
        rightHorozontol = opMode.hardwareMap.get(DcMotorEx.class, "rightHorozontol");

        // This will Reset the encoder once it gets to the fully extent point?
        //leftSlide.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightHorozontol.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        // I am not sure what this does but it was in the verticle one so i kept it
        leftHorozontol.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightHorozontol.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        leftHorozontol.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightHorozontol.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        leftHorozontol.setDirection(DcMotor.Direction.FORWARD);
        rightHorozontol.setDirection(DcMotor.Direction.REVERSE);

        /// Sets position back to start
        leftHorozontol.setTargetPosition(0);
        rightHorozontol.setTargetPosition(0);

        ///runs to set position
        leftHorozontol.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightHorozontol.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        /// Says what set power is reffering to
        leftHorozontol.setPower(power);
        rightHorozontol.setPower(power);
    }
    /*public Lift(LinearOpMode linearOpMode){

        leftHorozontol = opMode.hardwareMap.get(DcMotorEx.class, "leftslide");
        rightHorozontol = opMode.hardwareMap.get(DcMotorEx.class, "rightslide");

        leftHorozontol.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightHorozontol.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        leftHorozontol.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightHorozontol.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        leftHorozontol.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightHorozontol.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        leftHorozontol.setDirection(DcMotor.Direction.FORWARD);
        rightHorozontol.setDirection(DcMotor.Direction.REVERSE);

        leftHorozontol.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightHorozontol.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        leftHorozontol.setTargetPosition(0);
        rightHorozontol.setTargetPosition(0);


        leftHorozontol.setPower(power);
        rightHorozontol.setPower(power);
    }

    public void goUpOrDown (int targetPosition){
        leftHorozontol.setTargetPosition(targetPosition);
        rightHorozontol.setTargetPosition(targetPosition);
    }

    public void setMotorPower(double power){

        leftHorozontol.setPower(power);
        rightHorozontol.setPower(power);
    }

}
