package frc.robot.systems;

import com.revrobotics.spark.SparkFlex;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.config.SparkFlexConfig;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Timer;
public class Corallauncher {
    SparkFlex motor;
    SparkFlexConfig config;
    DigitalInput stopGrab;
    Timer timer;

     public Corallauncher(SparkFlex motor,DigitalInput stopGrabber,Timer timer){
        this.motor=motor;
        this.stopGrab = stopGrabber;
        this.timer = timer;
        config = new SparkFlexConfig();
        config.idleMode(IdleMode.kCoast);
        config.inverted(true);
        this.motor.configure(config, ResetMode.kResetSafeParameters, PersistMode.kNoPersistParameters);
        
    }
    public void launchCoral(double launchSpeed){
       motor.set(launchSpeed);
       timer.delay(0.5);
       stopLauncher();

    }

    
    public void stopLauncher(){
         motor.set(0);

    }
    public boolean intakeCoral(double intakeSpeed){
        System.out.println(stopGrab.get());
        if (stopGrab.get() == false){
            motor.set(intakeSpeed);
            return false;
        } 
        else{
            stopLauncher();
            return true;
        }    
    }
}

