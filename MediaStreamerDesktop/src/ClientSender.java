
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import com.google.gson.Gson;


class ClientSender extends Thread
{
	private PrintWriter out;

	public ClientSender(PrintWriter out)
	{
		this.out = out;
	}
	public void sendMessage(String message)
	{
		out.println(message);
	}
	public void sendCommand(Command command)
	{
		Gson gson = new Gson();
    	String output = gson.toJson(command, Command.class);
		out.println(output);
		MainWindow.addChat(output);
	}
	//Reads messages from the console and sends them to the server
	public void run()
	{
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		while (!isInterrupted()) {
			
			out.flush();
		}
	}
}
