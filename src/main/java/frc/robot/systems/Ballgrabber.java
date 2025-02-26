package frc.robot.systems;
import com.revrobotics.spark.SparkFlex;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.SparkFlexConfig;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;




public class Ballgrabber {
    SparkFlex motor;
    SparkMax angleMotor;
    SparkFlexConfig config;

    public Ballgrabber(SparkFlex motor,SparkMax angleMotor){
        
        
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
    public void angleChange(){
        angleMotor.



    }
   
    
}
//mogus