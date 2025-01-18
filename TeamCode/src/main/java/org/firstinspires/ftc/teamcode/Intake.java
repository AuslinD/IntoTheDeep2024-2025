package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;

public class Intake {

    OpMode opMode;
    DcMotor intakeMotor;
    Servo intakeExtension;
    Servo intakeAngle;

    public Intake (OpMode opMode){
        intakeMotor = opMode.hardwareMap.get(DcMotorEx.class, "intake");
        intakeAngle = opMode.hardwareMap.get(Servo.class, "intakeAngle");
        intakeExtension = opMode.hardwareMap.get(Servo.class, "intakeExtension");
    }

}
