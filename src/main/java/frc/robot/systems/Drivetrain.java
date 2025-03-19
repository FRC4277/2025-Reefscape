package frc.robot.systems;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.InvertedValue;
import com.ctre.phoenix6.signals.NeutralModeValue;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;
import com.ctre.phoenix6.configs.MotorOutputConfigs;
import com.ctre.phoenix6.configs.TalonFXConfigurator;

import edu.wpi.first.math.MathUtil;
import com.ctre.phoenix6.controls.DutyCycleOut;
import com.studica.frc.AHRS;
import edu.wpi.first.wpilibj.Timer;
public class Drivetrain {
    
    TalonFX dtFrontLeft;
    TalonFX dtFrontRight;
    TalonFX dtBackLeft;
    TalonFX dtBackRight;
    MotorOutputConfigs leftConfig;
    MotorOutputConfigs rightConfig;
    TalonFXConfigurator frontLeftConfigurator;
    TalonFXConfigurator frontRightConfigurator;
    TalonFXConfigurator backLeftConfigurator;
    TalonFXConfigurator backRightConfigurator;
    DutyCycleOut dutycyclefl;
    DutyCycleOut dutycyclefr;
    DutyCycleOut dutycyclebl;
    DutyCycleOut dutycyclebr;
    boolean coastmodemode;
    
    double dtDeadband;
    AHRS gyro;
    boolean first;
    double startTime;
    public Drivetrain(TalonFX frontLeft,TalonFX frontRight,TalonFX backLeft,TalonFX backRight, AHRS passedGyro, double deadband){
        dtFrontLeft = frontLeft;
        dtFrontRight = frontRight;
        dtBackLeft = backLeft;
        dtBackRight = backRight;
        leftConfig = new MotorOutputConfigs();
        rightConfig = new MotorOutputConfigs();
        dutycyclefl = new DutyCycleOut(0);
        dutycyclefr = new DutyCycleOut(0);
        dutycyclebl = new DutyCycleOut(0);
        dutycyclebr = new DutyCycleOut(0);
        leftConfig.Inverted = InvertedValue.CounterClockwise_Positive;
        rightConfig.Inverted = InvertedValue.Clockwise_Positive;
        dtDeadband = deadband;
        leftConfig.NeutralMode = NeutralModeValue.Coast;
        rightConfig.NeutralMode = NeutralModeValue.Coast;
        frontLeftConfigurator = dtFrontLeft.getConfigurator();
        frontRightConfigurator = dtFrontRight.getConfigurator();
        backLeftConfigurator = dtBackLeft.getConfigurator();
        backRightConfigurator = dtBackRight.getConfigurator();
        gyro = passedGyro;
        frontLeftConfigurator.apply(leftConfig);
        frontRightConfigurator.apply(rightConfig);
        backLeftConfigurator.apply(leftConfig);
        backRightConfigurator.apply(rightConfig);
        first = true;
        
    } 

    public void autonomousTest(){
        

    }
   
   
    public void timedDrive(double time,Timer timer, double xSpeed, double ySpeed, double zSpeed){
    
        
        arcadeDrive(xSpeed, ySpeed,0.5*zSpeed);
        timer.delay(time);
        stop();
   }
   public void turnToRotation(double desiredAngle, double deadZone){
        
    
        if (gyro.getAngle() < desiredAngle){
           while (gyro.getAngle() < desiredAngle){
            if (Math.abs(gyro.getAngle() - desiredAngle) < deadZone){
                break;
            }
            arcadeDrive(0, 0, 0.5);
           } 
            

            }
        else if (gyro.getAngle() > desiredAngle){
            while (gyro.getAngle() > desiredAngle){
                if (Math.abs(gyro.getAngle() - desiredAngle) < deadZone){
                    break;
                }
                arcadeDrive(0, 0, -0.25);
        }    
    }
        stop();
        
    }

   
    public void fieldOrientedDrive(double xSpeed, double ySpeed, double zSpeed){
        List<Double> wheelSpeeds = getWheelSpeeds(xSpeed, ySpeed, zSpeed, Math.toRadians(gyro.getAngle()));
       
        /*dutycyclefl.Output = wheelSpeeds.get(0);
        dutycyclefr.Output = wheelSpeeds.get(1);
        dutycyclebl.Output = wheelSpeeds.get(2);
        dutycyclebr.Output = wheelSpeeds.get(3);



        dtFrontLeft.setControl(dutycyclefl);
        dtFrontRight.setControl(dutycyclefr);
        dtBackLeft.setControl(dutycyclebr);
        dtBackRight.setControl(dutycyclebr);*/
        dtFrontLeft.set(wheelSpeeds.get(0));
        dtFrontRight.set(wheelSpeeds.get(1));
        dtBackLeft.set(wheelSpeeds.get(2));
        dtBackRight.set(wheelSpeeds.get(3));
    }
    public void stop(){
        fieldOrientedDrive(0, 0, 0);
    }

    public void resetGyro(){
    gyro.reset();

    }

    public void arcadeDrive(double xSpeed, double ySpeed, double zSpeed){
        List<Double> wheelSpeeds = getWheelSpeeds(xSpeed, ySpeed, zSpeed, 0);

        dtFrontLeft.set(wheelSpeeds.get(0));
        dtFrontRight.set(wheelSpeeds.get(1));
        dtBackLeft.set(wheelSpeeds.get(2));
        dtBackRight.set(wheelSpeeds.get(3));
        //System.out.println(xSpeed + " " + ySpeed + " " + zSpeed);
    }

    private List<Double> getWheelSpeeds(double xSpeed, double ySpeed, double zSpeed, double angleRad){
       
        double sinAngle = Math.sin(angleRad);
        double cosAngle = Math.cos(angleRad);


        
        MathUtil.clamp(xSpeed, -1.0, 1.0 );
        MathUtil.clamp(ySpeed, -1.0, 1.0 );
        MathUtil.clamp(zSpeed, -1.0, 1.0 );

        xSpeed =  dtApplyFilter(xSpeed, dtDeadband);
        ySpeed =  dtApplyFilter(ySpeed, dtDeadband);
        zSpeed =  dtApplyFilter(zSpeed, dtDeadband);

        double rotXSpeed = xSpeed * cosAngle - ySpeed * sinAngle;
        double rotYSpeed = xSpeed * sinAngle + ySpeed * cosAngle;
       
        

        double fl = rotYSpeed + rotXSpeed + zSpeed;
        double fr = rotYSpeed - rotXSpeed - zSpeed;
        double bl = rotYSpeed - rotXSpeed + zSpeed;
        double br = rotYSpeed + rotXSpeed - zSpeed;

        return Arrays.asList(fl, fr, bl, br);
    }
    
    public double dtApplyFilter (double value, double deadband){
        if (Math.abs(value) < deadband){
            return 0.0; 

        }
        else{
            return Math.pow(value, 3.0);

        }
        
    
    }

    public void motorBrakeMode(){
        leftConfig.NeutralMode = NeutralModeValue.Brake;
        rightConfig.NeutralMode = NeutralModeValue.Brake; 
        frontLeftConfigurator.apply(leftConfig);
        frontRightConfigurator.apply(rightConfig);
        backLeftConfigurator.apply(leftConfig);
        backRightConfigurator.apply(rightConfig);
    }
    public void motorCoastMode(){
        leftConfig.NeutralMode = NeutralModeValue.Coast;
        rightConfig.NeutralMode = NeutralModeValue.Coast;
        frontLeftConfigurator.apply(leftConfig);
        frontRightConfigurator.apply(rightConfig);
        backLeftConfigurator.apply(leftConfig);
        backRightConfigurator.apply(rightConfig);

    }
}
