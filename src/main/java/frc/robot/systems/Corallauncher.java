package frc.robot.systems;
import com.revrobotics.spark.SparkFlex;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.config.SparkFlexConfig;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;

public class Corallauncher {
    SparkFlex motor;
    SparkFlexConfig config;


     public Corallauncher(SparkFlex motor){
        this.motor=motor;
        config = new SparkFlexConfig();
        config.idleMode(IdleMode.kCoast);
        config.inverted(false);
        motor.configure(config, ResetMode.kResetSafeParameters, PersistMode.kNoPersistParameters);
    }
    public void launchCoral(double launchSpeed){
        motor.set(launchSpeed);

    }
    public void resetLauncher(){
        motor.set(0);

    }
}
