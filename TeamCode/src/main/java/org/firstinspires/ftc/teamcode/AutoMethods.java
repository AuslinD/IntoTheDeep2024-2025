package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.robotcore.external.navigation.Orientation;

public class AutoMethods {

    OpMode opMode;
    Robot robot;
    public AutoMethods(OpMode opMode){
        this.opMode = opMode;
        robot = new Robot(opMode);
    }


    public void encoderDrive(double power, int ticks){
        int initPosition = robot.drivetrain.par.getPositionAndVelocity().position;

        while(Math.abs(robot.drivetrain.par.getPositionAndVelocity().position - ticks - initPosition) > 500){
            opMode.telemetry.addData("par position",robot.drivetrain.par.getPositionAndVelocity().position);

            if (robot.drivetrain.par.getPositionAndVelocity().position > ticks) {
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

    public void toHeading(double power, double degrees){
        Orientation angles = robot.imu.getAngularOrientation();
        degrees += 180;

        double curHeading = angles.firstAngle + 180;

        double CCWDistance = (360 - curHeading + degrees) % 360;
        double CWDistance = (360 + curHeading - degrees) % 360;
        double distance = Math.min(CWDistance, CCWDistance);



        while (distance > 2) {

            CCWDistance = (180 - angles.firstAngle + degrees) % 360;
            CWDistance = (540 + angles.firstAngle - degrees) % 360;

            distance = Math.min(CWDistance, CCWDistance);

            opMode.telemetry.addData("heading", angles.firstAngle + 180);
            opMode.telemetry.addData("CCWDistance", (180 - angles.firstAngle + degrees) % 360);
            opMode.telemetry.addData("CWDistance", (540 + angles.firstAngle - degrees) % 360);
            angles = robot.imu.getAngularOrientation();

            if (CCWDistance > CWDistance)
            {
                robot.drivetrain.fr.setPower(-power);
                robot.drivetrain.br.setPower(-power);
                robot.drivetrain.fl.setPower(power);
                robot.drivetrain.bl.setPower(power);
            }
            else {
                robot.drivetrain.fr.setPower(power);
                robot.drivetrain.br.setPower(power);
                robot.drivetrain.fl.setPower(-power);
                robot.drivetrain.bl.setPower(-power);
            }
            opMode.telemetry.update();
        }
        robot.drivetrain.fr.setPower(0);
        robot.drivetrain.br.setPower(0);
        robot.drivetrain.fl.setPower(0);
        robot.drivetrain.bl.setPower(0);
    }
}
