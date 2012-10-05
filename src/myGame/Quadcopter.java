package myGame;

import myGame.motors.MotorControl;
import stabilizationControls.HeightControl;

import com.jme3.asset.AssetManager;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.bullet.util.CollisionShapeFactory;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Box;

public class Quadcopter {

	private final float				mass	= 2f;
	private final RigidBodyControl	phy;
	private final MotorControl		motors;
	private final HeightControl		heightControl;

	private final Node	geometry;

	public Quadcopter(AssetManager assetManager) {
		geometry = createGeometry(assetManager);
		phy = preparePhy();
		motors = prepareMotors();
		heightControl = new HeightControl(motors);
		addControls();
	}

	private void addControls() {

		geometry.addControl(getHeightControl());

		geometry.addControl(motors);

		geometry.addControl(phy);
	}

	private Node createGeometry(AssetManager assetManager) {
		/** Create meshes for the arms of the quadcopters */
		final Box box1 = new Box(Vector3f.ZERO, 0.2f, 0.1f, 13f);
		final Box box2 = new Box(Vector3f.ZERO, 13f, 0.1f, 0.2f);

		/** Build spatials from them */
		final Geometry arm1 = new Geometry("Arm1", box1);
		final Geometry arm2 = new Geometry("Arm2", box2);
		final Material material1 = new Material(assetManager,
				"Common/MatDefs/Misc/Unshaded.j3md");
		final Material material2 = new Material(assetManager,
				"Common/MatDefs/Misc/Unshaded.j3md");
		material1.setColor("Color", new ColorRGBA(1, 0, 0, 1));
		material2.setColor("Color", new ColorRGBA(0, 0, 1, 1));
		arm1.setMaterial(material1);
		arm2.setMaterial(material2);

		/** Create the quadcopter spatial */
		final Node quadcopter_node = new Node("Quadcopter");
		quadcopter_node.attachChild(arm1);
		quadcopter_node.attachChild(arm2);

		return quadcopter_node;
	}

	public Node getGeometry() {
		return geometry;
	}

	public MotorControl getMotors() {
		return motors;
	}

	public RigidBodyControl getPhy() {
		return phy;
	}

	public HeightControl getHeightControl() {
		return heightControl;
	}

	private MotorControl prepareMotors() {
		/** Motor setup */
		return new MotorControl(phy);
	}

	private RigidBodyControl preparePhy() {
		/** Physics setup */
		RigidBodyControl temp = new RigidBodyControl(
				CollisionShapeFactory.createBoxShape(geometry), mass);
		temp.setFriction(0.5f);
		return temp;
	}

}
