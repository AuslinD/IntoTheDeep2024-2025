package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.Servo;

public class Claw {

    private Servo left, right;

    public Claw (OpMode opmode){
        left = opmode.hardwareMap.get(Servo.class, "leftclaw");
        right = opmode.hardwareMap.get(Servo.class, "rightclaw");
    }

    public Claw (LinearOpMode linearOpMode){
        left = linearOpMode.hardwareMap.get(Servo.class, "leftclaw");
        right = linearOpMode.hardwareMap.get(Servo.class, "rightclaw");
    }
}
