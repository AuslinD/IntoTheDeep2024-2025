package org.firstinspires.ftc.teamcode;

import com.acmerobotics.roadrunner.ParallelAction;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.arcrobotics.ftclib.command.Command;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.ParallelCommandGroup;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.CenterStageRobot.commands.ElevatorCommand;
import org.firstinspires.ftc.teamcode.CenterStageRobot.commands.InstantTelemetry;
import org.firstinspires.ftc.teamcode.CenterStageRobot.subsystems.ElevatorSubsystem;
import org.firstinspires.ftc.teamcode.CenterStageRobot.subsystems.IntakeArmSubsystem;
import org.firstinspires.ftc.teamcode.CenterStageRobot.subsystems.IntakeSubsystem;
import org.firstinspires.ftc.teamcode.CenterStageRobot.subsystems.OuttakeSusystem;
import org.firstinspires.ftc.teamcode.Extra.CommandAction;
import org.firstinspires.ftc.teamcode.Extra.CommandGroupBaseAction;

@Autonomous(name = "CenterStageAutonomous_HIGH", group = "Final Autonomous")
public class CenterStageAutonomous_HIGH extends LinearOpMode {

    protected MecanumDrive drive;
    protected RoadRunnerSubsystem RR;
    protected OuttakeSusystem outtakeSusystem;
    protected ElevatorSubsystem elevatorSubsystem;
    protected ElevatorCommand elevatorCommand;
    protected IntakeArmSubsystem intakeArmSubsystem;
    protected IntakeSubsystem intakeSubsystem;

    public RoadRunnerSubsystem.Randomizer randomizer;
    public TrajectoryActionBuilder ToPixel, ToBackdrop;

    protected static double starting_pos_error = 1;//inch

    protected Pose2d homePose_LOW_RED = new Pose2d((3 * RR.TileInverted) + (RR.RobotY/2) + starting_pos_error,(RR.TileInverted/2),Math.toRadians(180));
    protected Pose2d homePose_HIGH_RED = new Pose2d((3 * RR.TileInverted) + (RR.RobotY/2),(RR.Tile * 1.5),Math.toRadians(180));

    @Override
    public void runOpMode() {
        drive = new MecanumDrive(hardwareMap, homePose_HIGH_RED);
        RR = new RoadRunnerSubsystem(drive, RoadRunnerSubsystem.Alliance.RED, RoadRunnerSubsystem.Start.HIGH,
                RoadRunnerSubsystem.Corridor.INNER, RoadRunnerSubsystem.Corridor.INNER,
                RoadRunnerSubsystem.Station.INNER, RoadRunnerSubsystem.Parking.OUTER);

        ////////////////////////////////////////////////////////////////////////////////////////////

        outtakeSusystem = new OuttakeSusystem(hardwareMap);
        elevatorSubsystem = new ElevatorSubsystem(hardwareMap, telemetry, () -> 0);
        intakeSubsystem = new IntakeSubsystem(hardwareMap, telemetry);
        intakeArmSubsystem = new IntakeArmSubsystem(hardwareMap);

        ////////////////////////////////////////////////////////////////////////////////////////////

        waitForStart();

        ////////////////////////////////////////////////////////////////////////////////////////////
        randomizer = RoadRunnerSubsystem.Randomizer.CENTER;
        ToPixel = RR.RobotToBackdrop(randomizer).first;
        ToBackdrop = RR.RobotToBackdrop(randomizer).second;

        ////////////////////////////////////////////////////////////////////////////////////////////


        Actions.runBlocking(new SequentialAction(

                ////////////////////////////////////////////////////////////////////////////////////
                ToPixel.build(),
                RR.HIGH_ToStation.build(),
                ////////////////////////////////////////////////////////////////////////////////////
                new SequentialAction(

                        ////////////////////////////////////////////////////////////////////////////////////
                        RR.RobotStation().first.build(),
                        new CommandAction(
                                new InstantTelemetry(telemetry, Double.toString(drive.getHeading()))
                        ),
                        ////////////////////////////////////////////////////////////////////////////////////

                        new CommandGroupBaseAction(
                                new ParallelCommandGroup(
                                        new InstantCommand(intakeArmSubsystem::midArm, intakeArmSubsystem),
                                        new ElevatorCommand(elevatorSubsystem, ElevatorSubsystem.Level.LOADING),
                                        new SequentialCommandGroup(
                                                new InstantCommand(outtakeSusystem::go_intake_second, outtakeSusystem),
                                                new WaitCommand(70),
                                                new InstantCommand(outtakeSusystem::go_intake_first, outtakeSusystem)
                                        )
                                )
                        )
                ),

                new ParallelAction(
                        RR.RobotStation().second.build(),
                        new CommandAction(new InstantCommand(intakeSubsystem::slow_grabbing, intakeSubsystem))
                ),

                new CommandAction(new InstantCommand(intakeSubsystem::stop, intakeSubsystem)),

                new ParallelAction(

                        ////////////////////////////////////////////////////////////////////////////////////
                        RR.INNER_Station_INNER_SMALL.build(),
                        ////////////////////////////////////////////////////////////////////////////////////

                        new CommandGroupBaseAction(
                                new SequentialCommandGroup(
                                        new ParallelCommandGroup(
                                                new InstantCommand(intakeArmSubsystem::lowerArm, intakeArmSubsystem),
                                                new ElevatorCommand(elevatorSubsystem, ElevatorSubsystem.Level.LOADING),
                                                new SequentialCommandGroup(
                                                        new InstantCommand(outtakeSusystem::go_intake_second, outtakeSusystem),
                                                        new WaitCommand(70),
                                                        new InstantCommand(outtakeSusystem::go_intake_first, outtakeSusystem)
                                                )
                                        ),
                                        new SequentialCommandGroup(
                                                new ParallelCommandGroup(
                                                        new InstantCommand(intakeSubsystem::run_auto, intakeSubsystem),
                                                        new InstantCommand(outtakeSusystem::wheel_grab)
                                                ),
                                                new WaitCommand(4000)
                                        )
                                )
                        )
                ),

                ////////////////////////////////////////////////////////////////////////////////////

                new CommandGroupBaseAction(
                        new SequentialCommandGroup(
                                new InstantCommand(outtakeSusystem::wheel_stop),
                                new InstantCommand(intakeArmSubsystem::raiseArm),
                                new InstantCommand(intakeSubsystem::reverse),
                                new WaitCommand(1000),
                                new InstantCommand(intakeSubsystem::stop, intakeSubsystem)
                        )
                ),

                ////////////////////////////////////////////////////////////////////////////////////
                RR.RobotStation().second.build(),
                ///////////////////////////////////////////////////////////////////////////////////

                new ParallelAction(
                        RR.RobotStationToBackdrop().build(),
                        new SequentialAction(
                                new CommandAction(new WaitCommand(2000)),
                                new ParallelAction(
                                        new CommandAction(
                                                new ElevatorCommand(elevatorSubsystem, ElevatorSubsystem.Level.LOW)
                                        ),
                                        new CommandGroupBaseAction(new SequentialCommandGroup(
                                                new InstantCommand(outtakeSusystem::go_outtake_first, outtakeSusystem),
                                                new WaitCommand(80),
                                                new InstantCommand(outtakeSusystem::go_outtake_second, outtakeSusystem)
                                        ))
                                )
                        )
                ),
//
//                ////////////////////////////////////////////////////////////////////////////////////
//                RR.RobotBackdropToStation().build(),
//                ////////////////////////////////////////////////////////////////////////////////////
//
//                new SequentialAction(
//
//                        ////////////////////////////////////////////////////////////////////////////////////
//                        RR.RobotStation().first.build(),
//                        new CommandAction(
//                                new InstantTelemetry(telemetry, Double.toString(drive.getHeading()))
//                        ),
//                        ////////////////////////////////////////////////////////////////////////////////////
//
//                        new CommandGroupBaseAction(
//                                new ParallelCommandGroup(
//                                        new InstantCommand(intakeArmSubsystem::midArm, intakeArmSubsystem),
//                                        new ElevatorCommand(elevatorSubsystem, ElevatorSubsystem.Level.LOADING),
//                                        new SequentialCommandGroup(
//                                                new InstantCommand(outtakeSusystem::go_intake_second, outtakeSusystem),
//                                                new WaitCommand(70),
//                                                new InstantCommand(outtakeSusystem::go_intake_first, outtakeSusystem)
//                                        )
//                                )
//                        )
//                ),
//
//                new ParallelAction(
//                        RR.RobotStation().second.build(),
//                        new CommandAction(new InstantCommand(intakeSubsystem::slow_grabbing, intakeSubsystem))
//                ),
//
//                new CommandAction(new InstantCommand(intakeSubsystem::stop, intakeSubsystem)),
//
//                new ParallelAction(
//
//                        ////////////////////////////////////////////////////////////////////////////////////
//                        RR.INNER_Station_INNER_SMALL.build(),
//                        ////////////////////////////////////////////////////////////////////////////////////
//
//                        new CommandGroupBaseAction(
//                                new SequentialCommandGroup(
//                                        new ParallelCommandGroup(
//                                                new InstantCommand(intakeArmSubsystem::lowerArm, intakeArmSubsystem),
//                                                new ElevatorCommand(elevatorSubsystem, ElevatorSubsystem.Level.LOADING),
//                                                new SequentialCommandGroup(
//                                                        new InstantCommand(outtakeSusystem::go_intake_second, outtakeSusystem),
//                                                        new WaitCommand(70),
//                                                        new InstantCommand(outtakeSusystem::go_intake_first, outtakeSusystem)
//                                                )
//                                        ),
//                                        new SequentialCommandGroup(
//                                                new ParallelCommandGroup(
//                                                        new InstantCommand(intakeSubsystem::run_auto, intakeSubsystem),
//                                                        new InstantCommand(outtakeSusystem::wheel_grab)
//                                                ),
//                                                new WaitCommand(4000)
//                                        )
//                                )
//                        )
//                ),
//
//                ////////////////////////////////////////////////////////////////////////////////////
//
//                new CommandGroupBaseAction(
//                        new SequentialCommandGroup(
//                                new InstantCommand(outtakeSusystem::wheel_stop),
//                                new InstantCommand(intakeArmSubsystem::raiseArm),
//                                new InstantCommand(intakeSubsystem::reverse),
//                                new WaitCommand(1000),
//                                new InstantCommand(intakeSubsystem::stop, intakeSubsystem)
//                        )
//                ),
//
//                ////////////////////////////////////////////////////////////////////////////////////
//                RR.RobotStation().second.build(),
//                ///////////////////////////////////////////////////////////////////////////////////
//
//                new ParallelAction(
//                        RR.RobotStationToBackdrop().build(),
//                        new SequentialAction(
//                                new CommandAction(new WaitCommand(2000)),
//                                new ParallelAction(
//                                        new CommandAction(
//                                                new ElevatorCommand(elevatorSubsystem, ElevatorSubsystem.Level.LOW)
//                                        ),
//                                        new CommandGroupBaseAction(new SequentialCommandGroup(
//                                                new InstantCommand(outtakeSusystem::go_outtake_first, outtakeSusystem),
//                                                new WaitCommand(80),
//                                                new InstantCommand(outtakeSusystem::go_outtake_second, outtakeSusystem)
//                                        ))
//                                )
//                        )
//                ),

                new ParallelAction(

                ////////////////////////////////////////////////////////////////////////////////////
                        RR.RobotParking().build(),
                ////////////////////////////////////////////////////////////////////////////////////

                        new CommandGroupBaseAction(
                                new SequentialCommandGroup(
                                        new InstantCommand(outtakeSusystem::go_intake_second, outtakeSusystem),
                                        new WaitCommand(80),
                                        new InstantCommand(outtakeSusystem::go_intake_first, outtakeSusystem)
                                )
                        ),
                        new CommandAction(new ElevatorCommand(elevatorSubsystem, ElevatorSubsystem.Level.LOADING))
                )
        ));
//        Actions.runBlocking(RR.test);
    }
}