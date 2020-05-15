package Other;

import Collections.Ticket;
import Collections.TicketMap;

import java.io.*;
import java.util.LinkedHashMap;

/**
 * Класс для записи коллекции в файл
 */

public class CSVToFile {
    public void csvtofile(LinkedHashMap<Long, Ticket> tm, String path)  {
        try {
                OutputStream outputStream = new FileOutputStream(path);
                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream);
                for (Long ticKey : tm.keySet()) {
                    Ticket tic = tm.get(ticKey);
                    outputStreamWriter.write(tic.toParse());
                }

                outputStreamWriter.close();
                System.out.println("Collection saved.");

        } catch (FileNotFoundException e) {
            System.out.println("This file doesn't exist");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}