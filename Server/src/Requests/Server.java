package Requests;

import Actions.Command;
import Collections.TicketMap;
import Other.CSVReader;
import Other.CSVToFile;
import Other.ReadCommand;

import java.io.*;
import java.net.BindException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;
import java.util.logging.Logger;

/**
 * класс, реализующий сервер
 */

public class Server {

    private static String path;
    private static Logger log = Logger.getLogger(Server.class.getName());
    private static String s = "";

    public static void server() throws IOException, ClassNotFoundException {
        try {

            Selector selector = Selector.open();
            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.bind(new InetSocketAddress(8089));
            serverSocketChannel.configureBlocking(false);
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);


            if (System.in.available() > 0){
                BufferedReader r = new BufferedReader(new InputStreamReader(System.in));
                s = r.readLine();
                readCmd(s);
            }


            while (true) {
                try {
                    selector.select(700);
                    Set<SelectionKey> selectedKeys = selector.selectedKeys();
                    Iterator<SelectionKey> iter = selectedKeys.iterator();



                    if (System.in.available() > 0){
                        BufferedReader r = new BufferedReader(new InputStreamReader(System.in));
                        s = r.readLine();
                        readCmd(s);
                    }
                    while (iter.hasNext()) {
                        if (System.in.available() > 0){
                            BufferedReader r = new BufferedReader(new InputStreamReader(System.in));
                            s = r.readLine();
                            readCmd(s);
                        }

                        SelectionKey key = iter.next();
                        try {
                            if (key.isAcceptable()) {
                                register(selector, serverSocketChannel);
                            }

                            if (key.isReadable()) {
                                readClient(key);
                            }
                            iter.remove();

                        }catch(ClosedSelectorException e){
                            log.info("client disconnected");
                        } catch (IOException e) {
                            log.info("disconnection");
                            key.cancel();
                            selectedKeys.remove(key);
                            if (path != null) {
                                CSVToFile ctf = new CSVToFile();
                                ctf.csvtofile(TicketMap.getTickets(), path);
                            }
                           /* if (selectedKeys.isEmpty()) {
                                selector.close();
                                serverSocketChannel.close();


                            }*/
                        }
                    }

                } catch (ClosedSelectorException e) {
                    log.info("Selector closed");
                    System.exit(0);
                }
            }
        } catch (BindException exc) {
            log.info("Port is already using");
        }
        log.info("No clients more");
    }

    public static void readClient(SelectionKey key)
            throws IOException, ClassNotFoundException {
        Command command;
        SocketChannel channel = (SocketChannel) key.channel();
        ByteBuffer buffer = ByteBuffer.allocate(1024 * 1024);
        channel.read(buffer);
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(buffer.array());
            ObjectInputStream objIn = new ObjectInputStream(byteArrayInputStream);
            Object o = objIn.readObject();
            log.info("Server got object");
            if (o instanceof String) {
                path = (String) o;
                if (TicketMap.getTickets().isEmpty()) {
                    CSVReader csvr = new CSVReader();
                    csvr.CSVReader(path);
                }
            } else if (o instanceof ReadCommand) {
                ReadCommand ob = (ReadCommand) o;
                command = ob.getComm();
                command.execute(ob, channel);
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
        log.info("Client connected");

    }

    private static void readCmd(String str) {
        CSVToFile ctf = new CSVToFile();
        if (str.equals("save")) {
            ctf.csvtofile(TicketMap.getTickets(), path);
        } else {
            if (str.equals("exit")) {
                ctf = new CSVToFile();
                ctf.csvtofile(TicketMap.getTickets(), path);
                System.exit(0);
            } else {
                log.info("Unknown command");
            }
        }
    }
}

