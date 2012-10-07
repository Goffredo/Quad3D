package stabilization;

import myGame.motors.MotorControl;
import PID.PID;

import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.AbstractControl;
import com.jme3.scene.control.Control;

public class Stabilizer1 extends AbstractControl {
	MotorControl		controlloMotori;

	private final PID	pidX	= new PID(1000f, 0f, 10000f, 0f, 250f);
	private final PID	pidY	= new PID(0f, 0f, 0f, 0f, 250f);
	private final PID	pidZ	= new PID(1000f, 0f, 10000f, 0f, 250f);

	private float		target	= 0f;

	public Stabilizer1(MotorControl motors) {
		controlloMotori = motors;
	}

	@Override
	public Control cloneForSpatial(Spatial arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void controlRender(RenderManager rm, ViewPort vp) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void controlUpdate(float tpf) {

		if (enabled && getSpatial() != null) {
			float angoli[] = new float[3];
			getSpatial().getWorldRotation().toAngles(angoli);

			float pidOutputX, pidOutputY, pidOutputZ;

			System.out.println("a0:" + angoli[0]);
			System.out.println("a1:" + angoli[1]);
			System.out.println("a2:" + angoli[2]);

			pidOutputZ = pidZ.update(angoli[0], target);
			// pidOutputY = pidY.update(angoli[1], target);
			pidOutputX = pidX.update(angoli[2], target);

			controlloMotori.setPPM(1450 + pidOutputX, 0);
			controlloMotori.setPPM(1450 - pidOutputX, 2);
			controlloMotori.setPPM(1450 - pidOutputZ, 1);
			controlloMotori.setPPM(1450 + pidOutputZ, 3);
		}
	}

}
