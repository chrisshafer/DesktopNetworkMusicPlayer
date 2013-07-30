
public class Command {
	int command;
	String commandData;
	String text;
	// {"command":101,"commandData":"none","text":"hello"}
	// {
	Command(int command, String commandData, String text) {
		this.command = command;
		this.commandData = commandData;
		this.text = text;
	}
}
