package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.Servo;

public class DiffyClaw {
    private Servo leftArm, rightArm;
    private Servo leftDiffy, rightDiffy;
    private Servo claw;


    int armPos = 0;

    public DiffyClaw(OpMode opmode){
        leftArm = opmode.hardwareMap.get(Servo.class, "leftArm");
        rightArm = opmode.hardwareMap.get(Servo.class, "rightArm");

        leftDiffy = opmode.hardwareMap.get(Servo.class, "leftDiffy");
        rightDiffy = opmode.hardwareMap.get(Servo.class, "rightDiffy");

        claw = opmode.hardwareMap.get(Servo.class, "claw");
    }

    public void setArmPos(double pos){
        leftArm.setPosition(pos);
    }

    



}
