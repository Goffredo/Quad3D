package myGame;

import myGame.motors.MotorControl;

import PID.PIDControl;

import com.jme3.asset.AssetManager;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.bullet.util.CollisionShapeFactory;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.Control;
import com.jme3.scene.shape.Box;

public class Quadcopter {
	
	private static final float mass = 2f;
	private static RigidBodyControl quad_phy;
	private static MotorControl quad_motors;
	private static PIDControl quad_PID;
	private Node geometry;

	public Quadcopter(AssetManager assetManager){
		geometry = createGeometry(assetManager);
	}

	public Node getGeometry() {
		return geometry;
	}

	private static Node createGeometry(AssetManager assetManager) {
		/** Create meshes for the arms of the quadcopters */
		Box box1 = new Box(Vector3f.ZERO, 0.2f, 0.1f, 13f);
		Box box2 = new Box(Vector3f.ZERO, 13f, 0.1f, 0.2f);
		
		/** Build spatials from them */
		Geometry arm1 = new Geometry("Arm1", box1);
		Geometry arm2 = new Geometry("Arm2", box2);
		Material material1 = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
		Material material2 = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
		material1.setColor("Color", new ColorRGBA(1, 0, 0, 1));
		material2.setColor("Color", new ColorRGBA(0, 0, 1, 1));
		arm1.setMaterial(material1);		
		arm2.setMaterial(material2);
		
		/** Create the quadcopter spatial */
		Node quadcopter_node = new Node("Quadcopter");
		quadcopter_node.attachChild(arm1);
		quadcopter_node.attachChild(arm2);
		
		/** Physics setup */
		quad_phy =  new RigidBodyControl(CollisionShapeFactory.createBoxShape(quadcopter_node), mass);
		quad_phy.setFriction(0.5f);
		
		/** Motor setup */
		quad_motors = new MotorControl(quad_phy);
		
		/** PID setup */
		quad_PID = new PIDControl(1500f, 0f, 90f, quad_motors);
		
		//quadcopter_node.addControl(quad_PID);

		quadcopter_node.addControl(quad_motors);
		
		quadcopter_node.addControl(quad_phy);
		
		return quadcopter_node;
	}

	private static Control getPID() {
		return quad_PID;
	}

	public static RigidBodyControl getPhy() {
		return quad_phy;
	}

	public static MotorControl getQuad_motors() {
		return quad_motors;
	}

}
