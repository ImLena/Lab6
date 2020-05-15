package Actions;

import Collections.Ticket;
import Collections.TicketMap;
import java.io.IOException;
import java.nio.channels.SocketChannel;

public class CommandReceiver {
    private final SocketChannel channel;

    public CommandReceiver(SocketChannel socket) {
        this.channel = socket;
    }

    public void clear() {
        TicketMap.clear(channel);
    }

    public void info() throws IOException {
        TicketMap.info(channel);
    }

    public void insert(Long id, Ticket tic) throws IOException {
        TicketMap.insert(id, tic, channel);
    }

    public void min_by_creation_date() throws IOException {
        TicketMap.min_by_creation_date(channel);
    }

    public void print_descending(){
        TicketMap.print_descending(channel);
    }

    public void remove(Long id) throws IOException {
        TicketMap.remove(id, channel);
    }

    public void remove_greater(Ticket tic){
        TicketMap.remove_greater(tic);
    }
    public void replace_if_greater(Long id, Ticket tic) throws IOException {
        TicketMap.replace_if_greater(id, tic, channel);
    }

    public void update(Long id, Ticket tic) throws IOException {
        TicketMap.update(id, tic, channel);
    }

    public void show() throws IOException {
        TicketMap.show(channel);
    }

    public void count_greater_than_price(Float price) throws IOException {
        TicketMap.count_greater_than_price(price, channel);
    }
}
