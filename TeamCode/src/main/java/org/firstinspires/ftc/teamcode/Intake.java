package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;

public class Intake {

    OpMode opMode;
    DcMotor intake;
    Servo intakeAngle;

    public Intake (OpMode opMode){
        intake = opMode.hardwareMap.get(DcMotorEx.class, "intake");
        intakeAngle = opMode.hardwareMap.get(Servo.class, "intakeAngle");
    }

}
