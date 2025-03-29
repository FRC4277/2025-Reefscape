package frc.robot.systems;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkFlex;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.spark.config.SparkFlexConfig;
import com.revrobotics.spark.config.SparkMaxConfig;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.Timer;
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
    Timer timer;
    public Ballgrabber(SparkFlex motor,SparkMax angleMotor,DigitalInput stopperTop, DigitalInput stopperBottom,DigitalInput grabStop, Relay relay,Timer timer){
        
        this.angleMotor = angleMotor;
        this.motor = motor;
        this.stopperTop = stopperTop;
        this.stopperBottom = stopperBottom;
        this.relay = relay;
        this.grabStop = grabStop;
        this.timer = timer;
        SparkFlexConfig config = new SparkFlexConfig();
        config.idleMode(IdleMode.kBrake);
        config.inverted(true);

        SparkMaxConfig config2 = new SparkMaxConfig();
        config2.idleMode(IdleMode.kBrake);
        config2.inverted(true);
        
        this.motor.configure(config,ResetMode.kResetSafeParameters,PersistMode.kPersistParameters );
        this.angleMotor.configure(config2,ResetMode.kResetSafeParameters,PersistMode.kPersistParameters );
        magneton();
    }

    public void startGrabber(double setSpeed){
        if (grabStop.get() == true){
            motor.set(setSpeed);
        } 
        
    }

    public void stopGrabber(){
        motor.set(0);
    }
    public boolean launchAlgae(double setSpeed){
        motor.set(-setSpeed);
        timer.delay(0.5);
        stopGrabber();
        return false;
    }

    public boolean angleChangePos(){
        magneton();
        if (stopperTop.get()){
            
            angleMotor.set(0.3);
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
        if(grabStop.get() == true){
            startGrabber(setSpeed);
            return false;
        }
        else if (grabStop.get() == false){
            stopGrabber();
            angleChangePos();
            return true;
        }
        return false;
        }
}