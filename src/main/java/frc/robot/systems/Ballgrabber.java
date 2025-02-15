package frc.robot.systems;
import com.revrobotics.spark.SparkFlex;
import com.revrobotics.spark.config.SparkFlexConfig;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;

public class Ballgrabber {
    SparkFlex motor;
    SparkFlexConfig config;
    public Ballgrabber(int deviceId){
        config = new SparkFlexConfig();
        motor = new SparkFlex(deviceId, MotorType.kBrushless);
        config.idleMode(IdleMode.kCoast);
        config.inverted(true);


        motor.configure(config,ResetMode.kResetSafeParameters,PersistMode.kPersistParameters);
    }
    public void startGrabber(double setSpeed){
        motor.set(setSpeed);
    }
    public void stopGrabber(){
        motor.set(0);
    }
    
}
