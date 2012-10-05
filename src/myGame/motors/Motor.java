package myGame.motors;


public class Motor {

	private static final float	tolerance		= 0.001f;	// questo e'
														// semplicemente per
														// fermare la funzione
														// dopo un po'
	private static final float	responsivness	= 0.8f;	// questo e' la
														// qualita'
														// del motore (tra 0 e
														// 1)
	private float				currentRPM		= 1200;
	private float				targetRPM		= 1200;
	private float				conversion		= 1;

	public float getCurrentForce() {
		float c = currentRPM - 1190;
		c *= c;
		c /= 200000f;
		return c;
	}

	public float getCurrentRPM() {
		return currentRPM;
	}

	public float getSetPPM() {
		return targetRPM / conversion;
	}

	public void setPPM(float newPPM) {
		newPPM = Math.min(newPPM, 1700);
		newPPM = Math.max(newPPM, 1200);
		targetRPM = newPPM * conversion;
	}

	public void setPPMUnbounded(float newPPM) {
		targetRPM = newPPM * conversion;
	}

	protected void update() {
		float delta = targetRPM - currentRPM;
		if (Math.abs(delta) > tolerance)
			currentRPM = targetRPM - delta * responsivness;
	}
}