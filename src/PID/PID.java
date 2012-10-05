package PID;


public class PID {

	private float	Kp;
	private float	Ki;
	private float	Kd;

	private float	pState		= 0;
	private float	iState		= 0;
	private float	dState		= 0;

	private float	lastError	= 0;
	private float	lastY		= 0;

	public PID(float Kp, float Ki, float Kd) {
		this.Kp = Kp;
		this.Ki = Ki;
		this.Kd = Kd;
	}

	public float update(float output, float target) {
		float err = target - output;
		pState = Kp * err;
		iState += Ki * err;
		iState = Math.min(Math.max(1200, iState), 1700);
		dState = Kd * -Math.signum(lastError - err) * Math.abs(lastY - output);
		lastError = err;
		lastY = output;
		return pState + iState + dState;
	}

	public void resetIState() {
		iState = 0;
	}
}
