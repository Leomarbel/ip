package leo;

import leo.task.Task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

public class TaskList {
    public ArrayList<Task> tasks;

    TaskList(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }
    TaskList() {
        this.tasks = new ArrayList<>();
    }

    public void addTask(Task task) {
        this.tasks.add(task);
    }

    public Task deleteTask(int index) throws LeoException {
        if (index > tasks.size() - 1 || index < 0) {
            throw new LeoException("Error!!! Index outside of list bounds :(");
        }
        return tasks.remove(index);
    }

    public void markTask(int index) throws LeoException {
        if (index > tasks.size() - 1 || index < 0) {
            throw new LeoException("Error!!! Index outside of list bounds :(");
        }
        tasks.get(index).mark();
        //saveTasks(list);
        //LeoReply(t.toString());
    }

    public void unmarkTask(int index) throws LeoException {
        if (index > tasks.size() - 1 || index < 0) {
            throw new LeoException("Error!!! Index outside of list bounds :(");
        }
        //saveTasks(list);

        tasks.get(index).unmark();
        //LeoReply(t.toString());
    }

    public int size() {
        return tasks.size();
    }

    public ArrayList<String> toSaveFormat() {
        ArrayList<String> data = new ArrayList<>();
        for (leo.task.Task t : tasks) {
            data.add(t.toSaveState());
        }
        return data;
    }


    public boolean isEmpty() {
        return tasks.isEmpty();
    }

    public Task get(int i) {
        return tasks.get(i);
    }
}
