package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.Servo;

public class Claw {

    private Servo grab, angle;

    public Claw (OpMode opmode){
        grab = opmode.hardwareMap.get(Servo.class, "grab");
        angle = opmode.hardwareMap.get(Servo.class, "clawAng");
    }

    public Claw (LinearOpMode linearOpMode){
        grab = linearOpMode.hardwareMap.get(Servo.class, "grab");
        angle = linearOpMode.hardwareMap.get(Servo.class, "clawAng");
    }
}
