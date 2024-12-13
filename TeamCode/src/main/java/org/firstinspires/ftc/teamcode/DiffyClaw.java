package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.AnalogInput;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.Servo;

public class DiffyClaw {

    private double rightArmOffSet = 0;
    private Servo leftArm, rightArm;
    private CRServo leftDiffy, rightDiffy;
    private Servo claw;

    public AnalogInput leftDiffyEncoder, rightDiffyEncoder;


    int armPos = 0;

    public DiffyClaw(OpMode opmode){
        leftArm = opmode.hardwareMap.get(Servo.class, "leftArm");
        rightArm = opmode.hardwareMap.get(Servo.class, "rightArm");

        leftDiffy = opmode.hardwareMap.get(CRServo.class, "leftDiffy");
        rightDiffy = opmode.hardwareMap.get(CRServo.class, "rightDiffy");

        leftDiffyEncoder = opmode.hardwareMap.get(AnalogInput.class, "leftDiffyEncoder");
        rightDiffyEncoder = opmode.hardwareMap.get(AnalogInput.class, "rightDiffyEncoder");


        claw = opmode.hardwareMap.get(Servo.class, "claw");
    }

    public DiffyClaw(LinearOpMode linearOpMode){
        leftArm = linearOpMode.hardwareMap.get(Servo.class, "leftArm");
        rightArm = linearOpMode.hardwareMap.get(Servo.class, "rightArm");

        leftDiffy = linearOpMode.hardwareMap.get(CRServo.class, "leftDiffy");
        rightDiffy = linearOpMode.hardwareMap.get(CRServo.class, "rightDiffy");

        leftDiffyEncoder = linearOpMode.hardwareMap.get(AnalogInput.class, "leftDiffyEncoder");
        rightDiffyEncoder = linearOpMode.hardwareMap.get(AnalogInput.class, "rightDiffyEncoder");


        claw = linearOpMode.hardwareMap.get(Servo.class, "claw");
    }

    public void setArmPos(double pos){
        leftArm.setPosition(pos);
        rightArm.setPosition(pos + rightArmOffSet);
    }

    public void setClawPos(double pos){
        claw.setPosition(pos);
    }

    public void setRightDiffyPower(double power){
        rightDiffy.setPower(power);
    }

    public void setLeftDiffyPower(double power){
        leftDiffy.setPower(power);
    }







}
