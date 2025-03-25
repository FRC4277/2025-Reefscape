package frc.robot.systems;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkFlex;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.spark.config.SparkFlexConfig;
import com.revrobotics.spark.config.SparkMaxConfig;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.Relay.Value;
import edu.wpi.first.wpilibj.DigitalInput;



public class Ballgrabber {
    SparkFlex motor;
    SparkMax angleMotor;
    SparkFlexConfig config;
    double speedFix;
    DigitalInput stopperTop;
    DigitalInput stopperBottom;
    DigitalInput grabStop;
    Relay relay;
    public Ballgrabber(SparkFlex motor,SparkMax angleMotor,DigitalInput stopperTop, DigitalInput stopperBottom,DigitalInput grabStop, Relay relay){
        
        this.angleMotor = angleMotor;
        this.motor = motor;
        this.stopperTop = stopperTop;
        this.stopperBottom = stopperBottom;
        this.relay = relay;
        this.grabStop = grabStop;
        SparkFlexConfig config = new SparkFlexConfig();
        config.idleMode(IdleMode.kCoast);
        config.inverted(true);

        SparkMaxConfig config2 = new SparkMaxConfig();
        config2.idleMode(IdleMode.kCoast);
        config2.inverted(true);
        
        this.motor.configure(config,ResetMode.kResetSafeParameters,PersistMode.kPersistParameters );
        this.angleMotor.configure(config2,ResetMode.kResetSafeParameters,PersistMode.kPersistParameters );
        magneton();
    }

    public void startGrabber(double setSpeed){
        while (grabStop.get() == false){
            motor.set(setSpeed);
        } 
        
    }

    public void stopGrabber(){
        motor.set(0);
    }

    public boolean angleChangePos(){
        magneton();
        if (stopperTop.get()){
            
            angleMotor.set(.2);
            return true;
        }
        stopAngleChange();
        return false;
    }

    public boolean angleChangeNeg(){
        magnetoff();
        if (stopperBottom.get()){
            angleMotor.set(-.2);
            return true;
        }
        stopAngleChange();
        return false;
    }
    public void stopAngleChange(){
    angleMotor.stopMotor();


   }
   public void magneton(){
    relay.set(Value.kOn);
   }
   public void magnetoff(){
    relay.set(Value.kOff);
    
}
    public boolean autoGrab(double setSpeed){
        angleChangeNeg();
        if(grabStop.get() == false){
            startGrabber(setSpeed);
            return false;
        }
        else if (grabStop.get() == true){
            stopGrabber();
            angleChangePos();
            return true;
        }
        return false;
        }
}