package org.firstinspires.ftc.teamcode.tuning;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.acmerobotics.roadrunner.Vector2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Drivetrain;
import org.firstinspires.ftc.teamcode.MecanumDrive;

@Autonomous(name = "Red_Auto", group = "Autonomous")
public class RedAuto extends LinearOpMode{

    @Override
    public void runOpMode() throws InterruptedException {
        Pose2d initialPose = new Pose2d(0, -65, 1.5708);
        MecanumDrive drivetrain = new MecanumDrive(hardwareMap, initialPose);

        TrajectoryActionBuilder driveOne = drivetrain.actionBuilder(initialPose)
                .splineToConstantHeading(new Vector2d(0, -34), Math.toRadians(90))
                .waitSeconds(2);

        TrajectoryActionBuilder driveTwo = drivetrain.actionBuilder(initialPose)
                .setReversed(true)
                .splineToLinearHeading(new Pose2d(38, -30, -1.5708), Math.toRadians(90))
                .splineToConstantHeading(new Vector2d(36, -14), Math.toRadians(0))
                .splineToLinearHeading(new Pose2d(45, -15, -1.5708), Math.toRadians(0))
                .waitSeconds(2);

        TrajectoryActionBuilder driveThree = drivetrain.actionBuilder(initialPose)
                .splineToConstantHeading(new Vector2d(56, -60), Math.toRadians(0));
    }

}
