package org.firstinspires.ftc.teamcode;

import com.acmerobotics.roadrunner.Line;
import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.bosch.JustLoggingAccelerationIntegrator;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.Orientation;

public class TestMethods{
    DcMotor fl, bl, fr, br;

    BNO055IMU imu;

    OpMode opMode;
    public TestMethods(LinearOpMode opMode){
        this.opMode = opMode;

        // Set up the parameters with which we will use our IMU. Note that integration
        // algorithm here just reports accelerations to the logcat log; it doesn't actually
        // provide positional information.
        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.angleUnit           = BNO055IMU.AngleUnit.DEGREES;
        parameters.accelUnit           = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
        parameters.calibrationDataFile = "BNO055IMUCalibration.json"; // see the calibration sample OpMode
        parameters.loggingEnabled      = true;
        parameters.loggingTag          = "IMU";
        parameters.accelerationIntegrationAlgorithm = new JustLoggingAccelerationIntegrator();

        // Retrieve and initialize the IMU. We expect the IMU to be attached to an I2C port
        // on a Core Device Interface Module, configured to be a sensor of type "AdaFruit IMU",
        // and named "imu".
        imu = opMode.hardwareMap.get(BNO055IMU.class, "imu");
        imu.initialize(parameters);



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
        while(Math.abs(fl.getCurrentPosition() - distance - initPosition) > 5){
            if (fl.getCurrentPosition() > distance)
                setMotorPowers(-power);
            else{
                setMotorPowers(power);
            }
            opMode.telemetry.update();
        }
        setMotorPowers(0);
    }

    public void turn(double power, double degrees) {
        if(degrees < 0) {
            power = -power;
        }

        degrees = -degrees;
        Orientation angles = imu.getAngularOrientation();
        double initHeading = angles.firstAngle;

        opMode.telemetry.addData("heading", angles.firstAngle);

        while (Math.abs(angles.firstAngle - degrees - initHeading) > 2) {
            angles = imu.getAngularOrientation();

            fr.setPower(-power);
            br.setPower(-power);
            fl.setPower(power);
            bl.setPower(power);

            opMode.telemetry.update();
        }
        setMotorPowers(0);

    }

    public void toHeading(double power, double degrees) {
        Orientation angles = imu.getAngularOrientation();
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
            angles = imu.getAngularOrientation();

            if (CCWDistance > CWDistance)
            {
                fr.setPower(-power);
                br.setPower(-power);
                fl.setPower(power);
                bl.setPower(power);
            }
            else {
                fr.setPower(power);
                br.setPower(power);
                fl.setPower(-power);
                bl.setPower(-power);
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
