package frc.lib.generic.pigeon;

import com.ctre.phoenix.sensors.WPI_PigeonIMU;
import frc.lib.generic.advantagekit.HardwareManager;
import frc.robot.poseestimation.poseestimator.SparkOdometryThread;
import org.littletonrobotics.junction.Logger;
import org.littletonrobotics.junction.inputs.LoggableInputs;

import java.util.HashMap;
import java.util.Map;
import java.util.Queue;

public class GenericIMU extends WPI_PigeonIMU implements Pigeon {
    private final GenericPigeonInputsAutoLogged inputs = new GenericPigeonInputsAutoLogged();
    private final String name;

    private final Queue<Double> timestampQueue = SparkOdometryThread.getInstance().getTimestampQueue();
    private final Map<String, Queue<Double>> signalQueueList = new HashMap<>();

    public GenericIMU(String name, int deviceNumber) {
        super(deviceNumber);

        this.name = name;
        HardwareManager.addHardware(this);
    }

    @Override
    public void resetConfigurations() {
        super.configFactoryDefault();
    }

    public void setGyroYaw(double yawDegrees) {
        super.setYaw(yawDegrees);
    }

    @Override
    public void setupSignalUpdates(PigeonSignal signal) {
        if (!signal.useFasterThread()) return;

        switch (signal.getType()) {
            case YAW -> signalQueueList.put("yaw", SparkOdometryThread.getInstance().registerSignal(this::getYaw));
            case ROLL -> signalQueueList.put("roll", SparkOdometryThread.getInstance().registerSignal(this::getRoll));
            case PITCH -> signalQueueList.put("pitch",SparkOdometryThread.getInstance().registerSignal(this::getPitch));
        }
    }

    @Override
    public void periodic() {
        refreshInputs();
        Logger.processInputs(name, inputs);
    }

    @Override
    public LoggableInputs getInputs() {
        return inputs;
    }

    private void refreshInputs() {
        inputs.gyroYawDegrees = getYaw();
        inputs.gyroRollDegrees = getRoll();
        inputs.gyroPitchDegrees = getPitch();

        if (signalQueueList.isEmpty()) return;

        inputs.odometryUpdatesYawDegrees = signalQueueList.get("yaw").stream().mapToDouble(Double::doubleValue).toArray();
        inputs.odometryUpdatesPitchDegrees = signalQueueList.get("pitch").stream().mapToDouble(Double::doubleValue).toArray();
        inputs.odometryUpdatesRollDegrees = signalQueueList.get("roll").stream().mapToDouble(Double::doubleValue).toArray();
        inputs.odometryUpdatesTimestamp = timestampQueue.stream().mapToDouble(Double::doubleValue).toArray();

        timestampQueue.clear();
        signalQueueList.forEach((k, v) -> v.clear());
    }
}