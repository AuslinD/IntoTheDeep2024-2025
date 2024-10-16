package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

public class AutoMethods {

    OpMode opMode;
    Robot robot;
    public AutoMethods(OpMode opMode){
        this.opMode = opMode;
        robot = new Robot(opMode);
    }


    public void encoderDrive(double power, int ticks){
        int initPosition = robot.drivetrain.fl.getCurrentPosition();
        opMode.telemetry.addData("fl",robot.drivetrain.fl.getCurrentPosition());
        while(Math.abs(robot.drivetrain.fl.getCurrentPosition() - ticks - initPosition) > 5){
            if (robot.drivetrain.fl.getCurrentPosition() > ticks) {
                robot.drivetrain.fl.setPower(-power);
                robot.drivetrain.fr.setPower(-power);
                robot.drivetrain.bl.setPower(-power);
                robot.drivetrain.br.setPower(-power);
            }
            else{
                robot.drivetrain.fl.setPower(power);
                robot.drivetrain.fr.setPower(power);
                robot.drivetrain.bl.setPower(power);
                robot.drivetrain.br.setPower(power);
            }
            opMode.telemetry.update();
        }
        robot.drivetrain.fl.setPower(0);
        robot.drivetrain.fr.setPower(0);
        robot.drivetrain.bl.setPower(0);
        robot.drivetrain.br.setPower(0);
    }
}
