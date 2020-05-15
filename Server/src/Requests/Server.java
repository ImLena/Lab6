package Requests;

import Actions.Command;
import Actions.CommandInvoker;
import Collections.Ticket;
import Collections.TicketMap;
import Other.CSVReader;
import Other.CSVToFile;
import Other.ReadCommand;

import java.io.*;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;
/**
 * класс, реализующий сервер
 */
public class Server {

    private static CommandInvoker commandInvoker = new CommandInvoker();
    private static String path;

    public static void server() throws IOException, ClassNotFoundException {

        Selector selector = Selector.open();
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.bind(new InetSocketAddress("127.0.0.1", 9999));
        serverSocketChannel.configureBlocking(false);
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        while (true) {
            try {

                selector.select();
                Set<SelectionKey> selectedKeys = selector.selectedKeys();
                Iterator<SelectionKey> iter = selectedKeys.iterator();

                while (iter.hasNext()) {

                    SelectionKey key = iter.next();
                    try {
                        if (key.isAcceptable()) {
                            register(selector, serverSocketChannel);
                        }

                        if (key.isReadable()) {
                            readClient(key);
                        }
                        iter.remove();
                    } catch (IOException e) {
                        System.out.println("disconnection");
                        key.cancel();
                        selectedKeys.remove(key);
                        if (selectedKeys.isEmpty()) {
                            if (path != null) {
                                CSVToFile ctf = new CSVToFile();
                                ctf.csvtofile(TicketMap.getTickets(), path);
                            }
                            selector.close();
                            serverSocketChannel.close();
                        }
                    }
                }
            } catch (ClosedSelectorException e) {
                System.out.println("Selector closed");
                System.exit(0);

            }
        }
    }

    public static void readClient(SelectionKey key)
            throws IOException, ClassNotFoundException {
        Command command;
        SocketChannel channel = (SocketChannel) key.channel();
        ByteBuffer buffer = ByteBuffer.allocate(1024 * 1024);
        channel.read(buffer);
            System.out.println("Server got message");
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(buffer.array());
            ObjectInputStream objIn = new ObjectInputStream(byteArrayInputStream);
            Object o = objIn.readObject();
            System.out.println("Server got object");
            if (o instanceof String) {
                path = (String) o;
                CSVReader csvr = new CSVReader();
                csvr.CSVReader(path);
                System.out.println(path.isEmpty());
            } else if (o instanceof ReadCommand) {
                ReadCommand ob = (ReadCommand) o;
                command = ob.getComm();
                command.execute(ob, channel);
                System.out.println(ob.getComm());
            } else {
                answer(channel, "Unidentified object type");
            }
    }

    public static void answer(SocketChannel channel, String msg) throws IOException {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(msg);
        byte[] data = baos.toByteArray();
        channel.write(ByteBuffer.wrap(data));
    }

    private static void register(Selector selector, ServerSocketChannel serverSocket)
            throws IOException {

        SocketChannel client = serverSocket.accept();
        client.configureBlocking(false);
        client.register(selector, SelectionKey.OP_READ);
        System.out.println("Client connected");
    }
}

