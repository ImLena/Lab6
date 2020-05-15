package Actions;

import Other.ReadCommand;

import java.nio.channels.SocketChannel;

/**
 * Класс для реализации команды print_descending
 */


public class PrintDescending extends Command {

    private static final long serialVersionUID = 32L;
    @Override
    public void execute(ReadCommand com, SocketChannel channel) {
        CommandReceiver commandReceiver = new CommandReceiver(channel);
        commandReceiver.print_descending();
    }

}

