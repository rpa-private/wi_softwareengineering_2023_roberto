package ch.fhnw.richards.Week_06.solarSystem;

public class Controller extends Thread {
	public final int FRAME_RATE = 30;
	private Model model;
	private View view;

	public Controller(Model model, View view) {
		super("Controller");
		this.model = model;
		this.view = view;

		this.setDaemon(true); // Will be killed when program ends
		this.start(); // Begin simulation
	}

	@Override
	public void run() {
		while (true) {
			view.update();

			try {
				Thread.sleep(1000 / FRAME_RATE);
			} catch (InterruptedException e) {
			}
		}
	}
}
