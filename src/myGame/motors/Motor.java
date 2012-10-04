package myGame.motors;

public class Motor {

	private static final float	tolerance		= 0.5f; // questo e'
														// semplicemente per
														// fermare la funzione
														// dopo un po'
	private static final float	responsivness	= 0.9f; // questo e' la
														// qualita'
														// del motore (tra 0 e
														// 1)
	private float				currentPPM		= 1200;
	private float				targetPPM		= 1200;

	public void setPPM(float newPPM) {
		newPPM = Math.min(newPPM, 1700);
		newPPM = Math.max(newPPM, 1200);
		targetPPM = newPPM;
	}

	protected void update() {
		float delta = targetPPM - currentPPM;
		if (Math.abs(delta) > tolerance)
			currentPPM = targetPPM - delta * responsivness;
	}

	public float getCurrentPPM() {
		return currentPPM;
	}

	public float getCurrentForce() {
		float c = currentPPM - 1190;
		c *= c;
		c /= 200000f;
		return c;
	}

	public void setPPMUnbounded(float newPPM) {
		targetPPM = newPPM;
	}
}