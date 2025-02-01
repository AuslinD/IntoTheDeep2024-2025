package org.firstinspires.ftc.teamcode.tuning;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.ParallelAction;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.DiffyClaw;
import org.firstinspires.ftc.teamcode.Drivetrain;
import org.firstinspires.ftc.teamcode.Intake;
import org.firstinspires.ftc.teamcode.Lift;
import org.firstinspires.ftc.teamcode.MecanumDrive;
import org.firstinspires.ftc.teamcode.PID;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous(name = "Red_RR_Left_Auto", group = "Autonomous")
public class RedAuto extends LinearOpMode{


    Lift lift;
    DiffyClaw diffyClaw;
    ElapsedTime elapsedTime;
    @Override
    public void runOpMode() throws InterruptedException {
        Pose2d initialPose = new Pose2d(0, -65, Math.toRadians(270));
        MecanumDrive drivetrain = new MecanumDrive(hardwareMap, initialPose);
        lift = new Lift(this);
        diffyClaw = new DiffyClaw(this);
        Action toSubmersible;
        Action toSample;
        Action toPark;

        toSubmersible = drivetrain.actionBuilder(initialPose)
                .setReversed(true)
                .splineToConstantHeading(new Vector2d(0, -34), Math.toRadians(90))

                .build();

        toSample = drivetrain.actionBuilder(new Pose2d(0, -34, Math.toRadians(90)))
                .setReversed(true)
                .splineToLinearHeading(new Pose2d(38, -30, -1.5708), Math.toRadians(90))
                .splineToConstantHeading(new Vector2d(36, -14), Math.toRadians(0))
                .splineToLinearHeading(new Pose2d(45, -15, -1.5708), Math.toRadians(0))

                .build();

        toPark = drivetrain.actionBuilder(new Pose2d(45, -15, -1.5708))
                .splineToConstantHeading(new Vector2d(56, -60), Math.toRadians(0))
                .build();

        waitForStart();

        Actions.runBlocking(
                new SequentialAction(
                    toSubmersible,
                    liftUp(),
                    /*DiffyAngle(),*/
                    ClawPosition(.355),
                    liftDown(),
                    toSample,
                    toPark
                )
        );



    }
    public class LiftUp implements Action {
        private boolean initialized = false;

        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            if (!initialized) {
                lift.goUpOrDown(2000);
                initialized = true;
            }

            double pos = lift.leftSlide.getCurrentPosition();
            packet.put("liftPos", pos);
            if (pos < 1900) {
                return true;
            } else {
                //lift.setMotorPower(0);
                return false;
            }
        }
    }
    public Action liftUp() {
        return new LiftUp();
    }

    public class LiftDown implements Action {
        private boolean initialized = false;

        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            if (!initialized) {
                lift.goUpOrDown(0);
                initialized = true;
            }

            double pos = lift.leftSlide.getCurrentPosition();
            packet.put("liftPos", pos);
            if (pos > 50.0) {
                return true;
            } else {
                lift.setMotorPower(0);
                return false;
            }
        }
    }
    public Action liftDown(){
        return new LiftDown();
    }

    private class ClawPosition implements Action{

        double position;


        public void setPosition(double position) {
            this.position = position;
        }


        private boolean initialized = false;
        ElapsedTime elapsedTime = new ElapsedTime();

        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            if(!initialized){
                elapsedTime.reset();
                initialized = true;
            }
            diffyClaw.setClawPos(position);

            return (elapsedTime.milliseconds() < 600);
        }
    }
    private Action ClawPosition(double pos){
        ClawPosition action = new ClawPosition();
        action.setPosition(pos);
        return action;
    }

    private class DiffyAngle implements Action{
        ElapsedTime elapsedTime;
        PID pid;
        double position;
        public void setPosition(double position) {
            this.position = position;
        }
        private boolean initialized = false;
        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            if(!initialized){
                elapsedTime = new ElapsedTime();
                elapsedTime.reset();

                initialized = true;
            }
            //diffyClaw.setArmPos(position);
            // not done
            return true;
        }
    }
    private Action DiffyAngle(double pos){
        DiffyAngle action = new DiffyAngle();
        action.setPosition(pos);
        return action;
    }

}
