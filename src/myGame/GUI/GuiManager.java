package myGame.GUI;

import myGame.Quadcopter;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.controls.Label;
import de.lessvoid.nifty.controls.Slider;
import de.lessvoid.nifty.controls.SliderChangedEvent;

public class GuiManager {

	public static void sliderMotorsChanged(SliderChangedEvent event, Quadcopter quad) {
		float newPPM = event.getValue();
		int motorNumber = Integer.parseInt(event.getSlider().getId().substring(
				7));
		quad.getMotors().setPPM(newPPM, motorNumber);
	}

	private static void updateForceLabel(Label niftyElement, Quadcopter quad) {
		int motorNumber = Integer.parseInt(niftyElement.getId().substring(6));
		niftyElement.setText("Force erogated by motor " + motorNumber + " : "
						+ Float.toString(quad.getMotors().getCurrentForce(
								motorNumber)));
	}

	private static void updatePPMLabel(Label niftyElement, Quadcopter quad) {
		int motorNumber = Integer.parseInt(niftyElement.getId().substring(4));
		niftyElement.setText("Current set PPM motor " + motorNumber + " : "
				+ Float.toString(quad.getMotors().getCurrentRPM(motorNumber)));
	}

	public static void updateLabels(Nifty nifty, Quadcopter quad) {
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

		niftyElement = nifty.getCurrentScreen().findNiftyControl("height",
				Label.class);
		niftyElement.setText("Height: "
				+ quad.getGeometry().getLocalTranslation().y);
	}

	public static void updateSliders(Nifty nifty, Quadcopter quad) {
		Slider slider = null;
		for (int i = 0; i < 4; i++) {
			slider = nifty.getCurrentScreen().findNiftyControl("sliderH" + i,
					Slider.class);
			slider.setValue(quad.getMotors().getSetPPM(i));
		}
	}

	public static void sliderHeightChanged(SliderChangedEvent event,
			Quadcopter quad) {
		quad.getHeightControl().setTargetHeight(event.getValue());
	}

}
