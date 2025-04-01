package frc.robot.systems;
import com.ctre.phoenix6.hardware.TalonFX;
import frc.robot.systems.Drivetrain;
import com.studica.frc.AHRS;
import frc.robot.systems.Ballgrabber;
import frc.robot.systems.Corallauncher;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.DigitalInput;
public class autonomousRoutines {
    Drivetrain drivetrain;
    Ballgrabber ballgrabber;
    Corallauncher corallauncher;
    Timer timer;
    
    public autonomousRoutines(Drivetrain drivetrain,Ballgrabber ballgrabber,Corallauncher corallauncher, Timer timer){
       this.drivetrain = drivetrain; 
       this.ballgrabber = ballgrabber;
       this.corallauncher = corallauncher;
       this.timer = timer;
    }
//all positions relative to driver station
//robot travels 30in/sec at 0.5
//robot travels in/sec at 0.75
    public void positionLeft(){
    drivetrain.timedDrive(3, timer, 0, -0.5, 0);
    drivetrain.turnToRotation(60, 5);
    drivetrain.timedDrive(1.9, timer, 0, -0.5, 0);
    corallauncher.launchCoral(0.3);   
     timer.delay(1);
     corallauncher.stopLauncher();
     timer.delay(15);
    }
    public void positionMiddle(){
    drivetrain.timedDrive(3, timer, 0, 0.5, 0);
    corallauncher.launchCoral(0.3);   
     timer.delay(1);
     corallauncher.stopLauncher();
     timer.delay(15);
    }
    public void positionRight(){
    drivetrain.timedDrive(3, timer, 0, -0.5, 0);
    drivetrain.turnToRotation(-60, 10);
    drivetrain.timedDrive(1.9, timer, 0, -0.5, 0);
    corallauncher.launchCoral(0.3);   
     timer.delay(1);
     corallauncher.stopLauncher();
     timer.delay(15);
    }
}
