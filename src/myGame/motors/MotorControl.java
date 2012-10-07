package myGame.motors;

import java.util.ArrayList;

import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.math.FastMath;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.AbstractControl;
import com.jme3.scene.control.Control;

public class MotorControl extends AbstractControl {

	private RigidBodyControl	rbControl;
	
	private ArrayList<ArrayList<Float>>	pools		= new ArrayList<ArrayList<Float>>();

	private Motor				motors[]	= new Motor[] {
			new Motor(),
			new Motor(),
			new Motor(),
			new Motor()					};

	public MotorControl(RigidBodyControl quad_phy) {
		rbControl = quad_phy;

		/** fuckyeah */
		/* fuck generics */
		pools.add(new ArrayList<Float>());
		pools.add(new ArrayList<Float>());
		pools.add(new ArrayList<Float>());
		pools.add(new ArrayList<Float>());
	}

	@Override
	public Control cloneForSpatial(Spatial spatial) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void controlRender(RenderManager rm, ViewPort vp) {
		// TODO Auto-generated method stub

	}

	public void setPPM(float PPMValue, int motorIndex) {
		PPMValue = Math.min(PPMValue, 1700);
		PPMValue = Math.max(PPMValue, 1200);
		pools.get(motorIndex).add(PPMValue);
	}

	@Override
	protected void controlUpdate(float tpf) {
		float force = 0;		
		for (int i = 0; i < pools.size(); i++) {
			motors[i].setPPM(getMeanValue(i));
			motors[i].update();
			force = motors[i].getCurrentForce();
			Vector2f angle = new Vector2f(FastMath.cos(i * (FastMath.PI / 2f)),
					FastMath.sin(i * (FastMath.PI / 2f)));
			Vector3f tAngle = new Vector3f(angle.x, 0, angle.y).mult(13);
			/** apply lift thrust */
			rbControl.applyForce(
					getSpatial().getLocalRotation().mult(
							Vector3f.UNIT_Y.mult(force)),
					getSpatial().getLocalRotation().mult(
							tAngle));
			pools.get(i).clear();
		}
	}


	private float getMeanValue(int i) {
		ArrayList<Float> c = pools.get(i);
		float out = 0;
		for (Float f : c) {
			out += f;
		}
		out /= c.size();
		return out;
	}

	public float getCurrentForce(int motorNumber) {
		return motors[motorNumber].getCurrentForce();
	}

	public float getCurrentRPM(int motorNumber) {
		return motors[motorNumber].getCurrentRPM();
	}

	public float getSetPPM(int motorNumber) {
		return motors[motorNumber].getSetPPM();
	}

}
