package myGame.GUI;

import myGame.Quadcopter;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.controls.Label;
import de.lessvoid.nifty.controls.SliderChangedEvent;

public class GuiManager {

	public static void updateQuadLabels(Nifty nifty, Quadcopter quad) {
		Label niftyElement = nifty.getCurrentScreen().findNiftyControl(
				"forceM0", Label.class);
		updateForceLabel(niftyElement, quad);
		niftyElement = nifty.getCurrentScreen().findNiftyControl("PPMM0",
				Label.class);
		updatePPMLabel(niftyElement, quad);

		niftyElement = nifty.getCurrentScreen().findNiftyControl(
				"forceM1", Label.class);
		updateForceLabel(niftyElement, quad);
		niftyElement = nifty.getCurrentScreen().findNiftyControl("PPMM1",
				Label.class);
		updatePPMLabel(niftyElement, quad);

		niftyElement = nifty.getCurrentScreen().findNiftyControl(
				"forceM2", Label.class);
		updateForceLabel(niftyElement, quad);
		niftyElement = nifty.getCurrentScreen().findNiftyControl("PPMM2",
				Label.class);
		updatePPMLabel(niftyElement, quad);

		niftyElement = nifty.getCurrentScreen().findNiftyControl(
				"forceM3", Label.class);
		updateForceLabel(niftyElement, quad);
		niftyElement = nifty.getCurrentScreen().findNiftyControl("PPMM3",
				Label.class);
		updatePPMLabel(niftyElement, quad);
	}

	private static void updatePPMLabel(Label niftyElement, Quadcopter quad) {
		int motorNumber = Integer.parseInt(niftyElement.getId().substring(4));
		niftyElement.setText("Current set PPM motor " + motorNumber + " : "
				+ Float.toString(quad.getQuad_motors().motors[motorNumber]
						.getCurrentPPM()));
	}

	private static void updateForceLabel(Label niftyElement, Quadcopter quad) {
		int motorNumber = Integer.parseInt(niftyElement.getId().substring(6));
		niftyElement.setText("Force erogated by motor " + motorNumber + " : "
				+ Float.toString(quad.getQuad_motors().motors[motorNumber]
						.getCurrentForce()));
	}

	public static void sliderChanged(SliderChangedEvent event, Quadcopter quad) {
		float newPPM = event.getValue();
		int motorNumber = Integer.parseInt(event.getSlider().getId().substring(
				7));
		quad.getQuad_motors().motors[motorNumber].setPPM(newPPM);
	}

}
