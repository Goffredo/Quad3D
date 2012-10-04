package PID;


public class PID {

	private float	Kp;
	private float	Ki;
	private float	Kd;

	private float	lastError;

	public PID(float Kp, float Ki, float Kd) {
		this.Kp = Kp;
		this.Ki = Ki;
		this.Kd = Kd;
	}

	protected float controlUpdate(float output, float target) {
		float err = target - output;
		return err;
	}
}
