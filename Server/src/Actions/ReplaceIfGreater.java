package Actions;


import Collections.Ticket;
import Other.ReadCommand;
import Requests.Server;

import java.io.IOException;
import java.nio.channels.SocketChannel;

/**
 * Класс для реализации команды replace_if_greater
 */

public class ReplaceIfGreater extends Command{
    private static final long serialVersionUID = 32L;

    @Override
    public void execute(ReadCommand com, SocketChannel channel) throws IOException {
        CommandReceiver commandReceiver = new CommandReceiver(channel);
        try{
        Ticket tic = com.getTicket();
        Long id = Long.valueOf(com.getStrArgs());
        if (tic != null) {
            commandReceiver.replace_if_greater(id, tic);
        }else {
            Server.answer(channel, "Ticket expected");
        }
        } catch (NumberFormatException e) {
            Server.answer(channel, "Not correct key");
        }
    }
}
