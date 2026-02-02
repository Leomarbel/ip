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

    /**
     * Searches for tasks that contain the specified keyword in their description
     * They keyword is case-insensitive, and allows space characters.
     *
     * @param taskDesc The keyword to be found (User Input)
     * @return an ArrayList of Task objects that contain the keyword
     * @throws LeoException if no matching tasks found
     */
    public ArrayList<Task> find(String taskDesc) throws LeoException{
        ArrayList<Task> matchingTasks = new ArrayList<>();
        String keyword = taskDesc.trim();

        for (Task t : tasks) {
            if (t.getTask().toLowerCase().contains(keyword.toLowerCase())) {
                matchingTasks.add(t);
            }
        }

        if (matchingTasks.isEmpty()) {
            throw new LeoException("No matching tasks found!");
        }
        return matchingTasks;
    }
}
