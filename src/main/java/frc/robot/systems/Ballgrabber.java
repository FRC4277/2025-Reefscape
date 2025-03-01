package frc.robot.systems;
import com.revrobotics.spark.SparkFlex;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.SparkFlexConfig;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import edu.wpi.first.wpilibj.DigitalInput;



public class Ballgrabber {
    SparkFlex motor;
    SparkMax angleMotor;
    SparkFlexConfig config;
    double speedFix;
    DigitalInput input;
    public Ballgrabber(SparkFlex motor,SparkMax angleMotor,DigitalInput stopperTop, DigitalInput stopperBottom){
        
        
        config = new SparkFlexConfig();
        config.idleMode(IdleMode.kCoast);
        config.inverted(true);

    }

    public void startGrabber(double setSpeed){
        motor.set(setSpeed);
    }

    public void stopGrabber(){
        motor.set(0);
    }

    public void angleChangePos(){
        angleMotor.set(.05);

    }

    public void angleChangeNeg(){
        angleMotor.set(-.05);

    }
    public void stopAngleChange(){
    angleMotor.stopMotor();


   }
    public void switchstop(){
     angleMotor.stopMotor();
       

    }
   
    
}














//mogus