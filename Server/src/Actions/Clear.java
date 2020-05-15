package Actions;

import Other.ReadCommand;
import Requests.Server;

import java.io.IOException;
import java.nio.channels.SocketChannel;

/**
 * Класс для реализации команды clear
 */

public class Clear extends Command {
    private static final long serialVersionUID = 32L;
    @Override
    public void execute(ReadCommand com, SocketChannel channel) throws IOException {
        CommandReceiver commandReceiver = new CommandReceiver(channel);
        commandReceiver.clear();
        Server.answer(channel, "Collection is clear");
    }
}
