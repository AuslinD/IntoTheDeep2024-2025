package org.firstinspires.ftc.teamcode;

import com.acmerobotics.roadrunner.Line;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;

public class TestMethods{
    DcMotor fl, bl, fr, br;

    OpMode opMode;
    public TestMethods(LinearOpMode opMode){
        this.opMode = opMode;
        fl = opMode.hardwareMap.get(DcMotorEx.class, "fl");
        fr = opMode.hardwareMap.get(DcMotorEx.class, "fr");
        br = opMode.hardwareMap.get(DcMotorEx.class, "br");
        bl = opMode.hardwareMap.get(DcMotorEx.class, "bl");

        fl.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        fl.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);


        fl.setDirection(DcMotorSimple.Direction.REVERSE);
        bl.setDirection(DcMotorSimple.Direction.REVERSE);

        fl.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        fr.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        bl.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        br.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);


    }

    public void drive(double power, double milliseconds){
        ElapsedTime time = new ElapsedTime();
        time.reset();
        while(time.milliseconds() < milliseconds){
            setMotorPowers(power);
        }
        setMotorPowers(0);

    }

    public void distance (double power, double distance){
        int initPosition = fl.getCurrentPosition();
        opMode.telemetry.addData("fl",fl.getCurrentPosition());
        while(Math.abs(fl.getCurrentPosition() - distance - initPosition) > 0){
            if (fl.getCurrentPosition() > distance)
                setMotorPowers(-power);
            else{
                setMotorPowers(power);
            }
            opMode.telemetry.update();
        }
        setMotorPowers(0);

    }

    public void setMotorPowers(double power){
        fl.setPower(power);
        bl.setPower(power);
        fr.setPower(power);
        br.setPower(power);
    }



}
