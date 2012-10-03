package PID;

import myGame.motors.Motor;
import myGame.motors.MotorControl;

import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.AbstractControl;
import com.jme3.scene.control.Control;

public class PIDControl extends AbstractControl {

	private MotorControl mControl;

	private float Kp;
	private float Ki;
	private float Kd;

	private float target = 5f;

	private float lastError;


	public PIDControl(float Kp, float Ki, float Kd, MotorControl quad_motors){
		this.Kp = Kp;
		this.Ki = Ki;
		this.Kd = Kd;		
		mControl = quad_motors;
	}

	@Override
	public Control cloneForSpatial(Spatial arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void controlRender(RenderManager arg0, ViewPort arg1) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void controlUpdate(float arg0) {
		float err = target - getSpatial().getLocalTranslation().y;
		
		float P = Kp * err;
		float D = Kd * (lastError - err);
		
		if (enabled && spatial != null) {
			for(Motor motor : mControl.motors){
				motor.setPPM(P-D);				
			}
		}
		
		lastError = getSpatial().getLocalTranslation().y;
	}
}
