package frc.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import org.team217.*;
import org.team217.ctre.*;
import org.team217.pid.*;
import frc.robot.*;
import frc.robot.PresetState.Preset;

public class ElevatorSubsystem extends Subsystem {
    WPI_TalonSRX leftElevator1 = RobotMap.leftElevator;
    WPI_TalonSRX rightElevator1 = RobotMap.rightElevator;

    APID elevatorAPID = RobotMap.elevAPID;

    public double lastElevatorPos = 0;
    public Preset lastPreset = Preset.Manual;

    @Override
    protected void initDefaultCommand() {
        leftElevator1.follow(rightElevator1);
    }
    
    /**
     * Returns the new elevator speed after checking the elevator limits.
     * 
     * @param speed
     *        The current elevator speed
     */
    public double limitCheck(double speed) {
        if (!RobotMap.elevatorBottomLimit.get()) {
            leftElevator1.resetEncoder();
            if (speed > 0){
                speed = 0;
            }
        }
        else if (!RobotMap.elevatorTopLimit.get() && speed < 0) {
            speed = 0;
        }

        return speed;
    }

    /**
     * Runs the elevator.
     * 
     * @param speed
     *        The elevator speed
     */
    public void set(double speed) {
        double elevatorMult = 0.65;
        
        if (leftElevator1.getEncoder() <= -11500 && speed <= 0) { //encoder values are wrong, check the logic
        	elevatorMult = .35;
        }
        else if(leftElevator1.getEncoder() >= -5500 && speed >= 0.0) { //this one too
        	elevatorMult = .25;
        }

        if (leftElevator1.getEncoder() <= -16180 && speed < 0) { //Check this first so we can still hold it up
            speed = 0;
        }
        
        if (speed != 0) {
            lastElevatorPos = leftElevator1.getEncoder();
        }
     //   else {
     //       speed = RobotMap.elevatorHoldPID.getOutput(RobotMap.rightElevator.getEncoder(), lastElevatorPos);
     //       elevatorMult = 1;
     //   }  obselete
        
        speed = limitCheck(speed);

        rightElevator1.set(speed * elevatorMult);
        leftElevator1.set((speed * elevatorMult));
    }
    
    /**
     * Runs the elevator using {@code APID} to reach a preset.
     * 
     * @param presetState
     *        The {@code Preset} state
     */
    public void preset(Preset presetState) {
        double target = 0;
        switch (presetState) {
        case Low:
            target = -6800;
            break;
        case Mid:
            target = -1504;
            break;
        case High:
            target = -12487;
            break;
        case RocketAdj:
            switch (lastPreset) {
            case Low:
                target = -5183;
                break;
            case Mid:
                target = 0;
                break;
            case High:
                target = -12500;
                break;
            default:
                presetState = Preset.Manual;
                break;
            }
        default:
            break;
        }

        double speed = 0;
        
        if (!presetState.equals(Preset.Manual)) {
            if (!lastPreset.equals(presetState)) {
                elevatorAPID.initialize();
            }
            else {
                speed = Num.inRange(elevatorAPID.getOutput(leftElevator1.getEncoder(), target), 1);
            }

            if (!presetState.equals(Preset.RocketAdj)) {
                lastPreset = presetState;
            }
        }

        set(speed);
    }
}