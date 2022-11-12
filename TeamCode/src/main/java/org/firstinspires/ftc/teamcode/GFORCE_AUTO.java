package org.firstinspires.ftc.teamcode;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.trajectorysequence.TrajectorySequence;

/**
 * This opmode demonstrates how one would implement field centric control using
 * `SampleMecanumDrive.java`. This file is essentially just `TeleOpDrive.java` with the addition of
 * field centric control. To achieve field centric control, the only modification one needs is to
 * rotate the input vector by the current heading before passing it into the inverse kinematics.
 * <p>
 * See lines 42-57.
 */
@Autonomous(name="G-FORCE AUTO", group = "advanced")
public class GFORCE_AUTO extends LinearOpMode {

    private Elevator elevator;
    private ConeTracker coneTracker;
    AutoConfig autoConfig = new AutoConfig();
    boolean weAreRed;
    GFORCE_KiwiDrive drive = null;
    boolean trackConeNow = false;
    boolean coneGrabbed = false;

    Double ONE_TILE = 23.5;


    @Override
    public void runOpMode() throws InterruptedException {

        // Initialize GFORCE_KiwiDrive
        drive = new GFORCE_KiwiDrive(hardwareMap);
        autoConfig.init(this);
        elevator = new Elevator(this, true);
        coneTracker = new ConeTracker(this);

        // We want to turn off velocity control for teleop
        // Velocity control per wheel is not necessary outside of motion profiled auto
        // drive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        // Retrieve our pose from the PoseStorage.currentPose static field
        // See AutoTransferPose.java for further details


        TrajectorySequence ourTrajectory = null;

        while (opModeInInit()) {
            elevator.update();
            elevator.runStateMachine();
            autoConfig.init_loop();
            //read signal cone
            // Select the desired trajectory
        }

        if (opModeIsActive()) {
            if (autoConfig.autoOptions.enabled) {
                weAreRed = autoConfig.autoOptions.redAlliance;
                if (weAreRed) {
                    if (autoConfig.autoOptions.startFront) {
                        Pose2d startPosition = new Pose2d(new Vector2d(-35, -62), Math.toRadians(90));
                        drive.setExternalHeading(Math.toRadians(90));
                        drive.setPoseEstimate(startPosition);
                        if (autoConfig.autoOptions.scoreJunction) {
                            //we are red, starting at the front, scoring the junction
                            ourTrajectory = drive.trajectorySequenceBuilder(startPosition)
                                    .addDisplacementMarker(0.5, () -> {
                                        elevator.levelUp();
                                    })
                                    .splineTo(new Vector2d(-30, -53), Math.toRadians(45))
                                    .UNSTABLE_addTemporalMarkerOffset(0.5, () -> {
                                        elevator.autoRelease();
                                    })
                                    .waitSeconds(1.5)
                                    .setReversed(true)
                                    .splineTo(new Vector2d(-35, -59), Math.toRadians(0))
                                    .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                                        elevator.setLiftTargetPosition(Elevator.ELEVATOR_STACK_TOP);
                                    })
                                    .lineTo(new Vector2d((ONE_TILE * -1.5), (ONE_TILE * -1)))
                                    .splineToConstantHeading(new Vector2d(-51, (ONE_TILE * -0.5)), Math.toRadians(180))
                                    .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                                        elevator.setWristOffset(0);
                                    })
                                    .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                                        trackConeNow = true;
                                    })
                                    .waitSeconds(2)
                                    .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                                        trackConeNow = false;
                                    })
                                    .build();
                            followGforceSequence(ourTrajectory);
                        }
                    }
                }
            }
        }

        // save the last field position in shared states class for teleop to use.
        SharedStates.currentPose = drive.getPoseEstimate();

        // Set Elevator state back to Idle for next autonomous
        elevator.setState(ElevatorState.IDLE);

    }


    public void followGforceSequence(TrajectorySequence trajectory) {
        drive.followTrajectorySequenceAsync(trajectory);
        while (!Thread.currentThread().isInterrupted() && drive.isBusy()) {
            elevator.update();
            elevator.runStateMachine();
            if (!trackConeNow) {
                drive.update();
            } else {
                coneHoming();
            }
        }
    }

    public void coneHoming() {
        if (coneTracker.update() && !coneGrabbed) {
            coneTracker.showRanges();
            double turn = coneTracker.coneDirection / 5.0;
            double speed = 0;

            if (coneTracker.coneRange > 120) {
                speed = 0.2;
            } else if (coneTracker.coneRange > 100) {
                speed = 0.1;
            } else if (coneTracker.coneRange < 80) {
                speed = -0.1;
            }
            drive.setWeightedDrivePower(new Pose2d(speed, 0, turn));
            if (((coneTracker.coneRange > 70) && (coneTracker.coneRange < 90))) {
                elevator.setWristPosition(elevator.HAND_CLOSE);
                coneGrabbed = true;
            }
        } else {
            drive.setWeightedDrivePower(new Pose2d(0, 0, 0));
        }
        telemetry.update();
    }
}
