package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.Servo;

public class Claw {

    private Servo left, right, arm;
    double leftGrab = 0;
    double rightGrab = .5;
    double leftRelease = .3;
    double rightRelease = .34;


    public Claw (OpMode opmode){
        left = opmode.hardwareMap.get(Servo.class, "claw");
        //right = opmode.hardwareMap.get(Servo.class, "rightclaw");
        arm = opmode.hardwareMap.get(Servo.class, "leftArm");
        
    }

    public Claw (LinearOpMode linearOpMode){
        left = linearOpMode.hardwareMap.get(Servo.class, "claw");
        //right = linearOpMode.hardwareMap.get(Servo.class, "rightclaw");
    }

    public void grabClaw(){
        left.setPosition(leftGrab);
        //right.setPosition(rightGrab);
    }

    public void releaseClaw(){
        left.setPosition(leftRelease);
        //right.setPosition(rightRelease);
    }

    public void setPosition(double position){
        arm.setPosition(position);
    }
}
