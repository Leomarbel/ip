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

/** Manages file storage operations for task data persistence */
public class Storage {
    private String filePath;
    static ArrayList<Task> datas = new ArrayList<>();

    /**
     * Creates a Storage instance with specified file path.
     *
     * @param filePath The path to the storage file.
     */
    public Storage(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Loads tasks from storage file.
     *
     * @return List of loaded tasks.
     * @throws IOException If file operations fail.
     */
    public ArrayList<Task> load() throws IOException {
        File file = new File(filePath);
        if (!file.exists()) {
            file.getParentFile().mkdirs();
            file.createNewFile();
            return datas;
        }
        Scanner s = new Scanner(file);
        while (s.hasNext()) {
            datas.add(fromSaveState(s.nextLine()));
        }
        s.close();
        return datas;
    }

    /**
     * Converts a saved string line back to a Task object.
     *
     * @param line The saved task string.
     * @return The reconstructed Task object.
     */
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

    /**
     * Saves task data to storage file.
     *
     * @param data List of task strings to save.
     * @throws IOException If file operations fail.
     */
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

    /**
     * Loads tasks from storage file (alias for load()).
     *
     * @return List of loaded tasks.
     * @throws IOException If file operations fail.
     */
    public ArrayList<Task> loadTasks() throws IOException {
        ArrayList<Task> tasks = load();
        return tasks;
    }
}





