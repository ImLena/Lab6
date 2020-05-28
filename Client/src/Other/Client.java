package Other;

import Actions.*;
import java.net.*;
import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Scanner;

public class Client {
    private static final int serverPort = 8080;
    private static final String localhost = "127.0.0.1";
    private static SocketChannel channel;

    public static void client(String[] args){
        CommandInvoker commandInvoker = new CommandInvoker();
        channel = null;
        InetSocketAddress ip = new InetSocketAddress(localhost, serverPort);

        try{
            channel = SocketChannel.open(ip);
            channel.configureBlocking(false);
            CommandReceiver cr = new CommandReceiver(commandInvoker, channel);
            Command clear = new Clear(cr);
            Command cgtp = new CountGreaterThanPrice(cr);
            Command es = new ExecuteScript(cr);
            Command help = new Help(cr);
            Command history = new History(cr);
            Command info = new Info(cr);
            Command insert = new Insert(cr);
            Command mbcd = new MinByCreationDate(cr);
            Command pd = new PrintDescending(cr);
            Command remove = new Remove(cr);
            Command rg = new RemoveGreater(cr);
            Command rig = new ReplaceIfGreater(cr);
            Command show = new Show(cr);
            Command update = new Update(cr);

            commandInvoker.addCom("clear", clear);
            commandInvoker.addCom("count_greater_than_price", cgtp);
            commandInvoker.addCom("execute_script", es);
            commandInvoker.addCom("help", help);
            commandInvoker.addCom("history", history);
            commandInvoker.addCom("info", info);
            commandInvoker.addCom("insert", insert);
            commandInvoker.addCom("min_by_creation_date", mbcd);
            commandInvoker.addCom("print_descending", pd);
            commandInvoker.addCom("remove_key", remove);
            commandInvoker.addCom("remove_greater", rg);
            commandInvoker.addCom("replace_if_greater", rig);
            commandInvoker.addCom("show", show);
            commandInvoker.addCom("update", update);
            Reader r = new BufferedReader(new InputStreamReader(System.in));
            Scanner in = new Scanner(r);
            writeMessage(channel, args[0]);
            while (true) {
                System.out.print("Enter command\n");
                commandInvoker.execute(in.nextLine().split(" "), in);
            }

            } catch(ConnectException ex){
                System.out.println("No connection");
            }catch (Exception exc) {
                exc.printStackTrace();
            } finally {
            try {
                if (channel != null)
                    channel.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public static void getMessage(SocketChannel socketChannel) throws IOException, ClassNotFoundException {
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024*1024);
        socketChannel.read(byteBuffer);

        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(byteBuffer.array());
        ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);

        Object o = objectInputStream.readObject();
        System.out.println((String) o);
    }

    public static void writeCommand(SocketChannel channel, ReadCommand com) throws IOException {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(com);
        byte[] data = baos.toByteArray();
        channel.write(ByteBuffer.wrap(data));
    }

    public static void writeMessage(SocketChannel channel, String str) throws IOException {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(str);
        byte[] data = baos.toByteArray();
        channel.write(ByteBuffer.wrap(data));
    }

    public static void closeChannel() throws IOException {
        channel.close();
    }
}