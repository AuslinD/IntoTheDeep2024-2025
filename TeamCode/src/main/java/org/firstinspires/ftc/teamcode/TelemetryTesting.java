package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp(name="TelemetryTesting", group = "test")
public class TelemetryTesting extends OpMode {
    //this code is for telemtry testing


    Robot robot;
    @Override
    public void init() {
        robot = new Robot(this);
    }

    @Override
    public void loop() {
        telemetry.addData("fl", robot.drivetrain.fl.getCurrentPosition());
        telemetry.addData("firstangle", robot.imu.getAngularOrientation().firstAngle);
        telemetry.addData("secondangle", robot.imu.getAngularOrientation().secondAngle);
        telemetry.addData("thirdangle", robot.imu.getAngularOrientation().thirdAngle);
        telemetry.update();
    }
}
