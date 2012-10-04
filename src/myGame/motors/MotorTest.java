package myGame.motors;

import java.io.IOException;

public class MotorTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Motor testMotor = new Motor();

		boolean goON = true;

		while (goON) {

			System.out
					.println("Input RPM (1=49 2=50 ...) or simply Return to continue");
			int read = 0;
			try {
				read = System.in.read();
				/** lol at flush */
				System.in.skip(System.in.available());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			if (read != 10) {
				testMotor.setPPMUnbounded(read * 10);
			}

			testMotor.update();
			System.out.println("Motor RPM: " + testMotor.getCurrentPPM());
			System.out.println("Byte read: " + read);

		}
	}

}
