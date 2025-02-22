package frc.robot.systems;
import com.revrobotics.spark.SparkFlex;
import com.revrobotics.spark.config.SparkFlexConfig;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;

public class Ballgrabber {
    SparkFlex motor;
    SparkFlexConfig config;
    private final Compressor compressor;
    private final Solenoid solenoid1;
    private final Solenoid solenoid2;


    public Ballgrabber(int motorId,int solenoid1Id,int solenoid2Id){
        config = new SparkFlexConfig();
        motor = new SparkFlex(motorId, MotorType.kBrushless);
        config.idleMode(IdleMode.kCoast);
        config.inverted(true);
        compressor = new Compressor(PneumaticsModuleType.REVPH);
        compressor.enableDigital();
        solenoid1 = new Solenoid(PneumaticsModuleType.REVPH,solenoid1Id);
        solenoid2 = new Solenoid(PneumaticsModuleType.REVPH,solenoid2Id);
        motor.configure(config,ResetMode.kResetSafeParameters,PersistMode.kPersistParameters);
    }
    public void startGrabber(double setSpeed){
        motor.set(setSpeed);
    }
    public void stopGrabber(){
        motor.set(0);
    }
    
    public void pneumaticsExtend(){
        solenoid1.set(true);
        solenoid2.set(true);



    }
    public void pneumaticsRetract(){
        solenoid1.set(false);
        solenoid2.set(false);



    }
}
