 package myGame.motors;


public class Motor {

	private static final float tolerance = 0.5f; //questo e' semplicemente per fermare la funzione dopo un po'
	private static final float responsivness = 0.5f; //questo e' la qualita' del motore piu' e' alto, meno tick c'e' bisogno affinche' raggiunga il target
	
	private float currentPPM = 0;
	private float targetPPM = 0;
	private float startPPM = 0;	

	public void setPPM(float newPPM) {		
		newPPM = Math.min(newPPM, 1700);
		newPPM = Math.max(newPPM, 1200);		
		startPPM = currentPPM;
		targetPPM = newPPM;
		
		currentPPM = newPPM;
	}

	protected void update() {
		/* TODO
		 * implement turn based exponential
		 */
	}
	
	public float getCurrentPPM() {
		return currentPPM;
	}
	
	public float getCurrentForce() {
		return (currentPPM*currentPPM)/19000000f;
	}

}