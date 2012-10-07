package myGame;

import myGame.GUI.GuiManager;

import com.jme3.app.SimpleApplication;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.PhysicsSpace.BroadphaseType;
import com.jme3.bullet.collision.shapes.CollisionShape;
import com.jme3.bullet.collision.shapes.PlaneCollisionShape;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.font.BitmapText;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.MouseButtonTrigger;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Plane;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.niftygui.NiftyJmeDisplay;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Box;
import com.jme3.scene.shape.Sphere;
import com.jme3.scene.shape.Sphere.TextureMode;

import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.NiftyEventSubscriber;
import de.lessvoid.nifty.controls.SliderChangedEvent;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;

/**
 * Example 12 - how to give objects physical properties so they bounce and fall.
 * 
 * @author base code by double1984, updated by zathras
 */
public class Quad3D extends SimpleApplication implements ScreenController {

	public static void main(String args[]) {
		Quad3D app = new Quad3D();
		app.start();
	}

	/** Prepare the Physics Application State (jBullet) */
	private BulletAppState		bulletAppState;

	/** Prepare Materials */
	Material					cannon_mat;
	Material					floor_mat;

	/** Prepare geometries and physical nodes */
	private RigidBodyControl	ball_phy;
	private static final Sphere	sphere;
	private RigidBodyControl	floor_phy;
	private static final Box	floor;

	/** Prepare quadcopter */
	Quadcopter					quad;
	private Node				quad_Node;

	private Nifty				nifty;

	static {
		/** Initialize the cannon ball geometry */
		sphere = new Sphere(32, 32, 2f, true, false);
		sphere.setTextureMode(TextureMode.Projected);
		/** Initialize the floor geometry */
		floor = new Box(Vector3f.ZERO, 10f, 0.1f, 5f);
		floor.scaleTextureCoordinates(new Vector2f(3, 6));
	}

	/**
	 * Every time the shoot action is triggered, a new cannon ball is produced.
	 * The ball is set up to fly from the camera position in the camera
	 * direction.
	 */
	private ActionListener	actionListener	= new ActionListener() {
												public void onAction(
														String name,
														boolean keyPressed,
														float tpf) {
													if (name.equals("shoot")
															&& !keyPressed) {
														makeCannonBall();
													}
												}
											};

	@Override
	public void bind(Nifty nifty, Screen screen) {
	}

	/** A plus sign used as crosshairs to help the player with aiming. */
	protected void initCrossHairs() {
		guiNode.detachAllChildren();
		guiFont = assetManager.loadFont("Interface/Fonts/Default.fnt");
		BitmapText ch = new BitmapText(guiFont, false);
		ch.setSize(guiFont.getCharSet().getRenderedSize() * 2);
		ch.setText("+"); // fake crosshairs :)
		ch.setLocalTranslation(
				// center
				settings.getWidth() / 2
						- guiFont.getCharSet().getRenderedSize() / 3 * 2,
				settings.getHeight() / 2 + ch.getLineHeight() / 2, 0);
		guiNode.attachChild(ch);
	}

	/** Make a solid floor and add it to the scene. */
	public void initFloor() {
		Geometry floor_geo = new Geometry("Floor", floor);
		floor_geo.setMaterial(floor_mat);
		/* Make the floor physical with mass 0.0f! */
		CollisionShape plane_coll = new PlaneCollisionShape(new Plane(
				new Vector3f(0, 1, 0), 0.1f));
		floor_phy = new RigidBodyControl(plane_coll, 0.0f);
		floor_geo.addControl(floor_phy);
		this.rootNode.attachChild(floor_geo);
		bulletAppState.getPhysicsSpace().add(floor_phy);
	}

	/** Initialize the materials used in this scene. */
	public void initMaterials() {
		cannon_mat = new Material(assetManager,
				"Common/MatDefs/Misc/Unshaded.j3md");
		cannon_mat.setColor("Color", new ColorRGBA(1.0f, 0.5f, 0.0f, 1.0f));
		floor_mat = new Material(assetManager,
				"Common/MatDefs/Misc/Unshaded.j3md");
		floor_mat.setColor("Color", new ColorRGBA(0.5f, 1.0f, 1.0f, 1.0f));
	}

	/* Initialize the quadcopter */
	private void initQuadcopter() {
		/** render setup */
		quad = new Quadcopter(assetManager);
		quad_Node = quad.getGeometry();
		this.rootNode.attachChild(quad_Node);
		bulletAppState.getPhysicsSpace().add(quad_Node);
		quad.getPhy().setPhysicsLocation(new Vector3f(0.0f, 5.0f, 0.0f));
	}

	/**
	 * This method creates one individual physical cannon ball. By defaul, the
	 * ball is accelerated and flies from the camera position in the camera
	 * direction.
	 */
	public void makeCannonBall() {
		/** Create a cannon ball geometry and attach to scene graph. */
		Geometry ball_geo = new Geometry("cannon ball", sphere);
		ball_geo.setMaterial(cannon_mat);
		rootNode.attachChild(ball_geo);
		/** Position the cannon ball */
		ball_geo.setLocalTranslation(cam.getLocation());
		/** Make the ball physcial with a mass > 0.0f */
		ball_phy = new RigidBodyControl(3f);
		/** Add physical ball to physics space. */
		ball_geo.addControl(ball_phy);
		bulletAppState.getPhysicsSpace().add(ball_phy);
		/** Accelerate the physcial ball to shoot it. */
		ball_phy.setLinearVelocity(cam.getDirection().mult(25f));
		ball_phy.setDamping(0.1f, 0.5f);
	}

	@Override
	public void onEndScreen() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onStartScreen() {
		// TODO Auto-generated method stub

	}

	@Override
	public void simpleInitApp() {
		/** Set up Physics Game */
		bulletAppState = new BulletAppState();
		stateManager.attach(bulletAppState);
		bulletAppState.setSpeed(10.0f);
		bulletAppState.getPhysicsSpace().enableDebug(assetManager);
		bulletAppState.setBroadphaseType(BroadphaseType.AXIS_SWEEP_3);
		bulletAppState.setWorldMax(new Vector3f(10, 10, 10));
		bulletAppState.setWorldMin(new Vector3f(-10, -10, -10));
		bulletAppState.getPhysicsSpace().setAccuracy(1 / 100f);

		/** Configure cam to look at scene */
		cam.setLocation(new Vector3f(0, 20f, 60f));
		cam.lookAt(new Vector3f(2, 15, 0), Vector3f.UNIT_Y);
		flyCam.setMoveSpeed(50.0f);
		flyCam.setDragToRotate(true);

		/** Add InputManager action: Left click triggers shooting. */
		inputManager.addMapping("shoot", new MouseButtonTrigger(
				MouseInput.BUTTON_RIGHT));
		inputManager.addListener(actionListener, "shoot");
		/** Initialize the scene, materials, and physics space */
		initMaterials();
		initFloor();
		initQuadcopter();
		initCrossHairs();

		/** Initialize GUI */
		NiftyJmeDisplay niftyDisplay = new NiftyJmeDisplay(assetManager,
				inputManager, audioRenderer, guiViewPort);
		nifty = niftyDisplay.getNifty();
		nifty.fromXml("Interface/HUD.xml", "HUD", this);
		guiViewPort.addProcessor(niftyDisplay);
		java.util.logging.Logger.getAnonymousLogger().getParent()
				.setLevel(java.util.logging.Level.SEVERE);
		java.util.logging.Logger.getLogger("de.lessvoid.nifty.*").setLevel(
				java.util.logging.Level.SEVERE);
	}

	@Override
	public void simpleUpdate(float tpf) {
		super.simpleUpdate(tpf);
		GuiManager.updateLabels(nifty, quad);
		GuiManager.updateSliders(nifty, quad);
	}

	@NiftyEventSubscriber(pattern = "sliderH.*")
	public void sliderMotorsChanged(final String id,
			final SliderChangedEvent event) {
		GuiManager.sliderMotorsChanged(event, quad);
	}

	@NiftyEventSubscriber(id = "heightSlider")
	public void sliderHeightChanged(final String id,
			final SliderChangedEvent event) {
		GuiManager.sliderHeightChanged(event, quad);
	}
}