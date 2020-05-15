package Actions;

import Other.ReadCommand;
import Requests.Server;

import java.io.IOException;
import java.nio.channels.SocketChannel;

/**
 * Класс для реализации команды remove
 */

public class Remove extends Command {
    private static final long serialVersionUID = 32L;
    @Override
    public void execute(ReadCommand com, SocketChannel channel) throws IOException {
        CommandReceiver commandReceiver = new CommandReceiver(channel);
        try{
        String arg = com.getStrArgs();
        Long id = Long.valueOf(arg);
        commandReceiver.remove(id);
            Server.answer(channel, "Element " + arg + " removed");
        } catch (NumberFormatException e) {
            Server.answer(channel, "Not correct key");
        }
    }
}
