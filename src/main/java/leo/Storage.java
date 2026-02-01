package leo;

import leo.task.Deadline;
import leo.task.Event;
import leo.task.Task;
import leo.task.Todo;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.io.FileWriter;
import java.io.File;
import java.util.Scanner;


public class Storage {
    private String filePath;
    static ArrayList<Task> data = new ArrayList<>();
    public Storage(String filePath) {
        this.filePath = filePath;
    }

    public ArrayList<Task> load() throws IOException {
        File file = new File(filePath);

        if (!file.exists()) {
            file.getParentFile().mkdirs();
            file.createNewFile();
            //leo.Leo.LeoReply("No File Detected, created new file");
            return data;
        }

        Scanner s = new Scanner(file);

        while (s.hasNext()) {
            data.add(fromSaveState(s.nextLine()));
        }

        s.close();
        return data;
    }

    public Task fromSaveState(String line) {
        String[] parts = line.split(" \\| ");
        String type = parts[0];
        boolean marked = parts[1].equals("X");
        String task = parts[2];

        return switch (type) {
            case "T" -> new Todo(task, marked);
            case "D" -> new Deadline(task, marked, LocalDateTime.parse(parts[3]));
            case "E" -> new Event(task, marked, parts[3], parts[4]);
            default -> throw new IllegalArgumentException("Unknown leo.task.Task Type");
        };

    }


    public void save (ArrayList<String> data) throws IOException{
        File f = new File(filePath);
        if (!f.exists()) {
            f.getParentFile().mkdirs();
            f.createNewFile();
        }


        FileWriter fw = new FileWriter(f);
        for (String line : data) {
            fw.write(line);
            fw.write(System.lineSeparator());
        }
        fw.close();


    }

    public ArrayList<Task> loadTasks() throws IOException {
        ArrayList<Task> tasks = new ArrayList<>();
        tasks = load();
        return tasks;
    }
}





