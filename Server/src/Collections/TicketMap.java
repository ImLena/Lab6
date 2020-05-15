package Collections;

import Requests.Server;

import java.io.IOException;
import java.io.Serializable;
import java.nio.channels.SocketChannel;
import java.util.Comparator;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Класс, определяющий поля коллекции
 */

public class TicketMap implements Comparable<Ticket>, Serializable {
    private static Long id = 0L; //Поле не может быть null, Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    private static Date creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private float price; //Значение поля должно быть больше 0
    private TicketType type; //Поле может быть null
    private Person person; //Поле не может быть null
    private Location location;


    public static LinkedHashMap<Long, Ticket> Tickets = new LinkedHashMap<>();

    public void parseToMap(String line){
        String cvsSplitBy = ", ";
        Ticket ticket = new Ticket();
        String[] infoTicket = line.split(cvsSplitBy);
        name = infoTicket[0];
        coordinates = new Coordinates();
        float x = Float.parseFloat(infoTicket[1]);
        Integer y = Integer.valueOf(infoTicket[2]);
        coordinates.setX(x);
        coordinates.setY(y);
        ticket.setCoordinates(coordinates);
        price = Float.parseFloat(infoTicket[3]);
        type = TicketType.valueOf(infoTicket[4]);
        Double height = Double.valueOf(infoTicket[5]);
        String locName = infoTicket[6];
        Long xx = Long.valueOf(infoTicket[7]);
        Float yy = Float.valueOf(infoTicket[8]);
        Float zz = Float.valueOf(infoTicket[9]);
        location = new Location();
        location.setName(locName);
        location.setX(xx);
        location.setY(yy);
        location.setZ(zz);
        person = new Person();
        person.setHeight(height);
        person.setLocation(location);
        creationDate = new Date();
        ticket.setCreationDate(creationDate);
        ticket.setPerson(person);
        ticket.setType(type);
        ticket.setName(name);
        ticket.setPrice(price);
        id += 1;
        setId(id);
        ticket.setId(id);
        //ticket.check();
        Tickets.put(id, ticket);
    }


    public static void setId(Long id) {
        id = id;
    }

    public static Long getId() {
        return id;
    }


    public static LinkedHashMap<Long, Ticket> getTickets() {
        return Tickets;
    }

    public static void putTickets(Long key, Ticket tickets) {
        Tickets.put(key, tickets);
    }

    public static void clear(SocketChannel channel) {
        Tickets.clear();
    }

    public static void info(SocketChannel channel) throws IOException {
        Server.answer(channel, "Collection type: " + Tickets.getClass().toString() + "\n" +
                "Date and time of initialization: " + creationDate.toString() + "\n" +
                "Number of items: " + Tickets.size() + "\n");
    }

    public static void insert(Long elemId, Ticket tic, SocketChannel channel) throws IOException {
        if (!Tickets.containsKey(elemId)) {
            Tickets.put(elemId, tic);
        } else {
            Server.answer(channel, "ELement with key " + elemId + " exist, to update this element use command update");
        }
    }

    public static void count_greater_than_price(Float price, SocketChannel channel) throws IOException {
        int count = 0;
        for(Map.Entry<Long, Ticket> e : Tickets.entrySet()) {
            Ticket tic = e.getValue();
            if (tic.getPrice() > price) {
                count++;
            }
        }
        Server.answer(channel,(count + " elements"));
    }

    public static void min_by_creation_date(SocketChannel channel) throws IOException {
        Map.Entry<Long, Ticket> tick = Tickets.entrySet().stream().min(Comparator.comparing(x -> x.getValue().getCreationDate())).get();
        String str = tick.toString();
        Server.answer(channel, str);
    }

    public static void print_descending(SocketChannel channel){
        StringBuilder sb = new StringBuilder();
        Tickets.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Comparator.naturalOrder()))
                .forEach(x -> sb.append(x.toString() + "\n"));
                    try {
                        Server.answer(channel, sb.toString());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

    }

    public static void remove(Long id, SocketChannel channel) throws IOException {
        Tickets.remove(id);
        Server.answer(channel,"Element" + id + "removed");

    }

    public static void remove_greater(Ticket tic){
        Tickets.entrySet().removeIf(x -> x.getValue().compareTo(tic) < 0);
    }

    public static void replace_if_greater(Long id, Ticket tic, SocketChannel channel) throws IOException {
        if (Tickets.get(id).compareTo(tic) > 0) {
           Tickets.put(id, tic);
           Server.answer(channel, "Element " + id + " replaced");
        } else {
            Server.answer(channel, "There's no element with such key. To add a new element use command insert");
        }
    }

    public static void show(SocketChannel channel) throws IOException {
        StringBuilder sb = new StringBuilder();
        if (!(Tickets.isEmpty())) {
            for (Map.Entry<Long, Ticket> entry : Tickets.entrySet()) {
                sb.append("Key: ").append(entry.getKey()).append("\n").append("Value: ").append(entry.getValue().toString()).append("\n");
            }
            Server.answer(channel, sb.toString());
        } else Server.answer(channel, "Collection is empty\n");
    }

    public static void update(Long id, Ticket tic, SocketChannel channel) throws IOException {
        if (Tickets.containsKey(id)) {
            Tickets.put(id, tic);
            Server.answer(channel, "Element " + id + " updated");
        } else {
            Server.answer(channel, "There's no element with such key. To add a new element use command insert");
        }
    }

    @Override
    public int compareTo(Ticket t) {
        if (t == null) {
            return -1;
        }
        return (int) (this.price - t.getPrice());
    }

    @Override
    public String toString(){
        return (name + ", " + coordinates.getX() + ", " + coordinates.getY() + ", " + price + ", " + type + ", " + person.getHeight() + ", " + location.getX() + ", " + location.getY() + ", " + location.getZ() + ", " + location.getName() + "\n");

    }
}
