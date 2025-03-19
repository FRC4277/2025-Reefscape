package frc.robot.systems;

import com.revrobotics.spark.SparkFlex;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.config.SparkFlexConfig;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import edu.wpi.first.wpilibj.DigitalInput;
public class Corallauncher {
    SparkFlex motor;
    SparkFlexConfig config;
    DigitalInput stopGrab;

     public Corallauncher(SparkFlex motor, DigitalInput stopGrab){
        this.motor=motor;
        this.stopGrab = stopGrab;
        //config = new SparkFlexConfig();
        //config.idleMode(IdleMode.kCoast);
        //config.inverted(false);
        //this.motor.configure(config, ResetMode.kResetSafeParameters, PersistMode.kNoPersistParameters);
        
    }
    public void launchCoral(double launchSpeed){
       
        motor.set(launchSpeed);
    }

    
    public void stopLauncher(){
        motor.set(0);

    }
    public void intakeCoral(double intakeSpeed){
        
        motor.set(-intakeSpeed);
    
    }
}

