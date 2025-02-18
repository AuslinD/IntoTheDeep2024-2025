package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;

public class Intake {

    OpMode opMode;
    DcMotor intakeMotor;
    Servo rightIntakeExtension;
    Servo leftIntakeExtension;
    Servo intakeAngle;

    ColorSensor colorSensor;

    double down = 0.06;
    double up = .3;
    boolean bLedOn = true;



    public Intake (OpMode opMode){
        intakeMotor = opMode.hardwareMap.get(DcMotorEx.class, "intake");
        intakeAngle = opMode.hardwareMap.get(Servo.class, "intakeAngle");
        rightIntakeExtension = opMode.hardwareMap.get(Servo.class, "rightIntakeExtension");
        leftIntakeExtension = opMode.hardwareMap.get(Servo.class, "leftIntakeExtension");
        colorSensor = opMode.hardwareMap.get(ColorSensor.class, "sensor_color");

        // Set the LED in the beginning
        colorSensor.enableLed(bLedOn);
    }



    public void setIntakeExtension(double pos){
        rightIntakeExtension.setPosition(pos);
        leftIntakeExtension.setPosition(1-pos);
    }


    public void setIntakeDown(){
        intakeAngle.setPosition(down);

    }

    public void setIntakeUp(){
        intakeAngle.setPosition(up);
    }

    public boolean isValidBrick(boolean blueSide){

        if(blueSide) {
            if (colorSensor.red() > 400 && colorSensor.red() < 2000) {
                return false;
            }
        }
        else{
            if (colorSensor.red() < 399 && colorSensor.red() > 70){
                return false;
            }
        }

        return true;
    }

}
