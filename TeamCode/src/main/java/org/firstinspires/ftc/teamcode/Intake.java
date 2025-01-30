package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;

public class Intake {

    OpMode opMode;
    DcMotor intakeMotor;
    Servo rightIntakeExtension;
    Servo leftIntakeExtension;
    Servo intakeAngle;

    double down = 0;
    double up = .3;

    public Intake (OpMode opMode){
        intakeMotor = opMode.hardwareMap.get(DcMotorEx.class, "intake");
        intakeAngle = opMode.hardwareMap.get(Servo.class, "intakeAngle");
        rightIntakeExtension = opMode.hardwareMap.get(Servo.class, "rightIntakeExtension");
        leftIntakeExtension = opMode.hardwareMap.get(Servo.class, "leftIntakeExtension");
    }

    public void setIntakeExtension(double pos){
        rightIntakeExtension.setPosition(pos);
        leftIntakeExtension.setPosition(1-pos);
    }


    public void setIntakeDown(){
        rightIntakeExtension.setPosition(down);
        leftIntakeExtension.setPosition(1-down);
    }

    public void setIntakeUp(){
        rightIntakeExtension.setPosition(up);
        leftIntakeExtension.setPosition(1-up);
    }

}
