package stabilizationControls;

import myGame.motors.MotorControl;
import PID.PID;

import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.AbstractControl;
import com.jme3.scene.control.Control;

public class HeightControl extends AbstractControl {

	private final MotorControl	motors;

	private final PID			pid		= new PID(50f, 1f, 2000f, 1200f, 1700f);

	private float				target			= 5.0f;

	public HeightControl(MotorControl motors) {
		this.motors = motors;
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
		if (enabled && getSpatial() != null) {
			float y = getSpatial().getLocalTranslation().y;
			float pidOutput;
			
			pidOutput = pid.update(y, target);

			for (int i = 0; i < 4; i++) {
				motors.setPPM(pidOutput, i);
			}
		}
	}

	public void setTargetHeight(float value) {
		target = value;
	}

}
