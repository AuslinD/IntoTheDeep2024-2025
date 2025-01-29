package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.AnalogInput;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.ServoController;
import com.qualcomm.robotcore.hardware.ServoControllerEx;

public class DiffyClaw {

    private double rightArmOffSet = 0;

    //These don't go negative anymore for some reason???
    public Servo leftArm, rightArm;

    public CRServo leftDiffy, rightDiffy;
    private Servo claw;

    public AnalogInput leftDiffyEncoder, rightDiffyEncoder;


    int armPos = 0;

    public DiffyClaw(OpMode opmode){
        leftArm = opmode.hardwareMap.get(Servo.class, "leftArm");
        rightArm = opmode.hardwareMap.get(Servo.class, "rightArm");


        leftDiffy = opmode.hardwareMap.get(CRServo.class, "leftDiffy");
        rightDiffy = opmode.hardwareMap.get(CRServo.class, "rightDiffy");

        rightDiffy.setDirection(DcMotorSimple.Direction.REVERSE);

        leftDiffyEncoder = opmode.hardwareMap.get(AnalogInput.class, "leftDiffyEncoder");
        rightDiffyEncoder = opmode.hardwareMap.get(AnalogInput.class, "rightDiffyEncoder");


        claw = opmode.hardwareMap.get(Servo.class, "claw");
    }

    /*
    public DiffyClaw(LinearOpMode linearOpMode){
        leftArm = linearOpMode.hardwareMap.get(Servo.class, "leftArm");
        rightArm = linearOpMode.hardwareMap.get(Servo.class, "rightArm");

        leftDiffy = linearOpMode.hardwareMap.get(CRServo.class, "leftDiffy");
        rightDiffy = linearOpMode.hardwareMap.get(CRServo.class, "rightDiffy");

        leftDiffyEncoder = linearOpMode.hardwareMap.get(AnalogInput.class, "leftDiffyEncoder");
        rightDiffyEncoder = linearOpMode.hardwareMap.get(AnalogInput.class, "rightDiffyEncoder");


        claw = linearOpMode.hardwareMap.get(Servo.class, "claw");
    }

     */

    public void setArmPos(double pos){
        rightArm.setPosition(pos);
        leftArm.setPosition(1-pos);
    }

    public void setClawPos(double pos){
        claw.setPosition(pos);
    }

    public double rightDiffyGoToPosition(double currentPostion, double targetPosition) {
        double power = (targetPosition - currentPostion) / 10;
        double error = Math.abs(targetPosition - currentPostion);
        rightDiffy.setPower(power);
        return error;
    }

    public double leftDiffyGoToPosition(double currentPostion, double targetPosition) {
        double power = (targetPosition - currentPostion) / 2;
        double error = Math.abs(targetPosition - currentPostion);
        if(error < .1){
            leftDiffy.setPower(0);
            return error;
        }
        leftDiffy.setPower(power);
        return error;
    }







}
