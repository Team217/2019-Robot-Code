package frc.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import org.team217.*;
import org.team217.rev.*;
import org.team217.pid.*;
import frc.robot.*;
import frc.robot.PresetState.Preset;

public class ArmSubsystem extends Subsystem {
    CANSparkMax rightArm1 = RobotMap.rightArm;
    CANSparkMax leftArm1 = RobotMap.leftArm;

    public Preset lastPreset = Preset.Manual;
    public double lastArmPos = 0;

    APID armAPID = RobotMap.armAPID;

    boolean isTelescopeOut = false;

    @Override
    protected void initDefaultCommand() {
    }

    /**
     * Returns the new arm speed after checking the arm limits.
     * 
     * @param speed
     *        The current arm speed
     */
    public double limitCheck(double speed) {
        if(isTelescopeOut) {
            if (rightArm1.getPosition() <= 0 && speed <= 0.0) { //get some limit switches, make sure logic is right
                speed = 0;
            }
            else if (rightArm1.getPosition() >= 109 && speed >= 0.0) { //Practice: 107 when tele is out, ; Comp: -207
                speed = 0;
            }
            else if (rightArm1.getPosition() >= 109 && speed >= 0.0){
                speed = 0;
            }
        } 
        else {
            if (rightArm1.getPosition() <= 0 && speed <= 0.0) { //get some limit switches, make sure logic is right
                speed = 0;
            }
            else if (rightArm1.getPosition() >= 109 && speed >= 0.0) { //Practice: 107 when tele is out, ; Comp: -207
                speed = 0;
            }
            else if (rightArm1.getPosition() >= 109 && speed >= 0.0){
                speed = 0;
            }
        }
        return speed;
    }

    /**
     * Runs the arm.
     * 
     * @param speed
     *        The arm speed
     */
    public void set(double speed) {
        double armMult = .50;
       
        if (!Robot.kClimbingSubsystem.isClimbing()) {
            if (speed != 0) {
                lastArmPos = RobotMap.rightArm.getPosition();
            }
            else {
                speed = RobotMap.armHoldPID.getOutput(RobotMap.rightArm.getPosition(), lastArmPos);
            }
    
            speed = limitCheck(speed);
        }
        else {
            lastArmPos = RobotMap.rightArm.getPosition();
            armMult = .65;
        }
      
        rightArm1.set(speed * armMult);
        leftArm1.set(speed * armMult);

       // System.out.println("Arm " + rightArm1.getPosition());
       // System.out.println("Arm speed" + speed);
 //       System.out.println("Wrist Gyro " + intakeGyro1.getAngle());
    }

    /**
     * Runs the arm using {@code APID} to reach a preset.
     * 
     * @param presetState
     *        The {@code Preset} state
     * @param isBack
     *        {@code true} if the preset moves the arm to the back region of the bot
     */
    public void preset(Preset presetState, boolean isBack) {
        System.out.println("Here");
        double target = 0;
        switch (presetState) {
        case Low:
            target = isBack ? 102 : 9.5;
            break;
        case Mid:
            target = isBack ? 75.6 : 45.9;
            break;
        case High:
            target = isBack ? 65.4 : 54.1;
            break;
        case Ball:
            target = isBack ? 101: 16.5;
            break;
        case Climb:
            target = 82;
            break;
        default:
            break;
        }

        double speed = 0;
        
        if (!presetState.equals(Preset.Manual)) {
            if (!lastPreset.equals(presetState)) {
                armAPID.initialize();
            }
            else {
                speed = Num.inRange(armAPID.getOutput(rightArm1.getPosition(), target), .75);
            }
        }
        
        lastPreset = presetState;

        set(speed);
    }
}