package net.davidsteinsland;

public class App {

	public static void main(String[] args) {
		FrameManager.invokeFrame(new MainFrame());
	}

	public static void terminateApplication() {
		System.exit(0);
	}
}
