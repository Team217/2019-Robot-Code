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

    public boolean atPreset = false;

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
            lastElevatorPos = 0;

            if (speed > 0) {
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
        
        if (leftElevator1.getEncoder() >= 12500 && speed <= 0) { //encoder values are wrong, check the logic
        	elevatorMult = .425;
        }
        else if(leftElevator1.getEncoder() <= 2000 && speed >= 0.0) { //this one too
        	elevatorMult = .375;
        }

        if (leftElevator1.getEncoder() >= 15500 && speed < 0) { //Check this first so we can still hold it up
            speed = 0;
        }
        
        if (speed != 0) {
            lastElevatorPos = leftElevator1.getEncoder();
        }
        else {
            if (Math.abs(lastElevatorPos - leftElevator1.getEncoder()) > 500) {
                lastElevatorPos = leftElevator1.getEncoder();
            }
            speed = -RobotMap.elevatorHoldPID.getOutput(RobotMap.leftElevator.getEncoder(), lastElevatorPos);
            elevatorMult = 1;
        } 
        
        speed = limitCheck(speed);

        System.out.println("elevator pos " + leftElevator1.getEncoder());
        System.out.println("last elevator pos " + lastElevatorPos);
        rightElevator1.set(speed * elevatorMult);
        leftElevator1.set(speed * elevatorMult);
    }
    
    /**
     * Runs the elevator using {@code APID} to reach a preset.
     * 
     * @param presetState
     *        The {@code Preset} state
     * @param isBack
     *        {@code true} if the preset moves the arm to the back region of the bot
     */
    public void preset(Preset presetState, boolean isBack) {
        double target = 0;
        switch (presetState) {
        case Low:
            target = isBack ? 4744 : 9649; //comp is 4744 : 7668
            break;
        case Mid:
            target = isBack ? 4939 : 6647; //comp is 4939 : 4850
            break;
        case High:
            target = isBack ? 11790 : 15037; //comp is 11790 : 13050
            break;
        case Ball:
            target = isBack ? 1752 : 1914; //comp is 1752 : 1914
            break;
        case Climb:
            target = 0; //comp is 0
            break;
        default:
            break;
        }

        double speed = 0;
        
        if (!presetState.equals(Preset.Manual)) {
            if (!lastPreset.equals(presetState)) {
                elevatorAPID.initialize();
            }
            else {
                speed = -Num.inRange(elevatorAPID.getOutput(leftElevator1.getEncoder(), target), 1);
            }
        }
        
        lastPreset = presetState;

        atPreset = Num.isWithinRange(leftElevator1.getEncoder(), target - 50, target + 50);

        set(speed);
    }

    public void reset() {
        leftElevator1.set(0);
        rightElevator1.set(0);
    }
}