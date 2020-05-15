package Actions;

import Other.ReadCommand;

import java.io.IOException;
import java.io.Serializable;
import java.nio.channels.SocketChannel;

/**
 * Класс для реализации команды show
 */

public class Show extends Command implements Serializable {
    private static final long serialVersionUID = 32L;

    @Override
    public void execute(ReadCommand com, SocketChannel channel) throws IOException {
        CommandReceiver commandReceiver = new CommandReceiver(channel);
        commandReceiver.show();
    }
}
