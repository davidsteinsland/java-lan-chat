package net.davidsteinsland;

import javax.swing.SwingWorker;
import javax.swing.JTextPane;

import java.net.Socket;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;

import java.util.List;

public class ChatReader extends SwingWorker<Void, String> {
	private Socket socket;

	private JTextPane messagesPane;

	public ChatReader(JTextPane messagesPane, Socket socket) {
		this.socket = socket;
		this.messagesPane = messagesPane;
	}

	@Override
	protected void process(List<String> lines) {
		for (String line : lines) {
			messagesPane.setText(messagesPane.getText() + line + "\n");
		}
	}

	@Override
	public Void doInBackground() throws IOException {
        InputStream is = socket.getInputStream();
        BufferedReader in = new BufferedReader(new InputStreamReader(is));

        String line;
        while ((line = in.readLine()) != null) {
            publish(line);
        }
        /* if line = null, server disconnected on us */
        return null;
	}
}
