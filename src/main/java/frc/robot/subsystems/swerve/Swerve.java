package frc.robot.subsystems.swerve;

import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.FunctionalCommand;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.GlobalConstants;
import org.littletonrobotics.junction.AutoLogOutput;

import java.util.function.DoubleSupplier;

import static frc.robot.subsystems.swerve.real.RealSwerveConstants.SWERVE_KINEMATICS;

public class Swerve extends SubsystemBase {
    private final SwerveConstants constants;

    private final SwerveModuleIO[] swerveModules;

    public Swerve() {
        constants = SwerveConstants.generateConstants();
        swerveModules = getSwerveModules();
    }

    @Override
    public void periodic() {
        for (SwerveModuleIO swerveModule : swerveModules) {
            swerveModule.periodic();
        }
    }

    public Command drive(DoubleSupplier x, DoubleSupplier y, DoubleSupplier rotation) {
        return new FunctionalCommand(
                () -> {},
                () -> {
                    ChassisSpeeds speeds = new ChassisSpeeds(x.getAsDouble(), y.getAsDouble(), rotation.getAsDouble());

                    SwerveModuleState[] targetStates = SWERVE_KINEMATICS.toSwerveModuleStates(speeds);

                    for (int i = 0; i < swerveModules.length; i++) {
                        swerveModules[i].setTargetState(targetStates[i]);
                    }
                },
                (interrupt) -> {
                },
                () -> false,
                this
        );
    }

    private SwerveModuleIO[] getSwerveModules() {
        if (GlobalConstants.CURRENT_MODE == GlobalConstants.Mode.REPLAY) {
            return new SwerveModuleIO[]{
                    new SwerveModuleIO("FrontLeft"),
                    new SwerveModuleIO("FrontRight"),
                    new SwerveModuleIO("RearLeft"),
                    new SwerveModuleIO("RearRight")
            };
        }

        return constants.getSwerveModules();
    }

    @AutoLogOutput(key = "Swerve/CurrentStates")
    private SwerveModuleState[] getModuleStates() {
        final SwerveModuleState[] states = new SwerveModuleState[swerveModules.length];

        for (int i = 0; i < swerveModules.length; i++)
            states[i] = swerveModules[i].getCurrentState();

        return states;
    }

    @AutoLogOutput(key = "Swerve/TargetStates")
    private SwerveModuleState[] getTargetStates() {
        final SwerveModuleState[] states = new SwerveModuleState[swerveModules.length];

        for (int i = 0; i < swerveModules.length; i++)
            states[i] = swerveModules[i].getTargetState();

        return states;
    }
}
