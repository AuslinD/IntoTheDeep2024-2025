package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;

public class Lift {

    OpMode opMode;
    DcMotor leftSlide, rightSlide;
    public Lift(OpMode opMode){
        this.opMode = opMode;
        leftSlide = opMode.hardwareMap.get(DcMotorEx.class, "leftslide");
        rightSlide = opMode.hardwareMap.get(DcMotorEx.class, "rightslide");
    }



}
