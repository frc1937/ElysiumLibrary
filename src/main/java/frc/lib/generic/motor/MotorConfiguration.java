package frc.lib.generic.motor;

/**
 * Generic configurations class with default values
 */
public class MotorConfiguration {
    public boolean inverted = false;
    public MotorProperties.IdleMode idleMode = MotorProperties.IdleMode.BRAKE;

    public MotorProperties.Slot slot0 = new MotorProperties.Slot(0, 0, 0, 0, 0, 0, 0, null);
    public MotorProperties.Slot slot1 = new MotorProperties.Slot(0, 0, 0, 0, 0, 0, 0, null);
    public MotorProperties.Slot slot2 = new MotorProperties.Slot(0, 0, 0, 0, 0, 0, 0, null);

    public int slotToUse = 0;

    /**
     * If non-zero, this determines how much time to ramp from 0% output to 100% during open-loop modes.
     * @| Minimum Value: 0
     * @| Maximum Value: 1
     * @| Default Value: 0
     * @| Units: sec
     */
    public double dutyCycleOpenLoopRampPeriod = 0;
    /**
     * If non-zero, this determines how much time to ramp from 0% output to 100% during closed-loop modes.
     * @| Minimum Value: 0
     * @| Maximum Value: 1
     * @| Default Value: 0
     * @| Units: sec
     */
    public double dutyCycleClosedLoopRampPeriod = 0;

    /**
     * Wrap position error within [-0.5,+0.5) mechanism rotations. Typically used for continuous position closed-loops like swerve azimuth.
     * This uses the mechanism rotation value. If there is a gear ratio between the sensor and the mechanism, make sure to apply a SensorToMechanismRatio so the closed loop operates on the full rotation.
     * Default Value: False
     */
    public boolean closedLoopContinuousWrap = false;

    public double supplyCurrentLimit = -1, statorCurrentLimit = -1;

    /** Convert between 1 system rotation to motor rotation. 1 / number*/
    public double gearRatio = 1;

    /**
     * This is the maximum velocity of the motion profile. Only set this if a profile is desired.
     *
     * <ul>
     *   <li> <b>Minimum Value:</b> 0
     *   <li> <b>Maximum Value:</b> 9999
     *   <li> <b>Default Value:</b> 0
     *   <li> <b>Units:</b> rps
     * </ul>
     */
    public double profiledMaxVelocity = 0;

    /**
     * This is the target acceleration the motion profile will try to honour.
     * Only set this if a profile is desired.
     *
     * <ul>
     *   <li> <b>Minimum Value:</b> 0
     *   <li> <b>Maximum Value:</b> 9999
     *   <li> <b>Default Value:</b> 0
     *   <li> <b>Units:</b> rot per sec²
     * </ul>
     */
    public double profiledTargetAcceleration = 0;
}
