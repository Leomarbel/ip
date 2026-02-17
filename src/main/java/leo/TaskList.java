package leo;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;

import leo.task.Interview;
import leo.task.Task;

/** Manages a collection of tasks and provides operations on them */
public class TaskList {
    private ArrayList<Task> tasks;

    /**
     * Creates TaskList with existing task collection.
     * @param tasks The list of tasks
     */
    TaskList(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    /** Creates empty TaskList. */
    TaskList() {
        this.tasks = new ArrayList<>();
    }

    /** Adds a task to the list. */
    public void addTask(Task task) {
        this.tasks.add(task);
    }

    /** Adds a task to the list. */
    public void addInterviewDate(Interview i) throws LeoException {
        LocalDateTime date = i.getInterviewTime();

        // Check if any existing task has the same date (ignoring time)
        Optional<Interview> conflictingTask = tasks.stream()
                .filter(t -> t instanceof Interview)
                .map(t -> (Interview) t)
                .filter(interview -> interview.getInterviewTime().toLocalDate()
                        .equals(date.toLocalDate()))
                .findFirst();

        if (conflictingTask.isPresent()) {
            Task existingTask = conflictingTask.get();
            throw new LeoException("Conflicting Schedule! Your interview "
                    + i.getTask() + " conflicts with " + existingTask.getTask()
                    + " on " + date.toLocalDate());
        }

        this.tasks.add(i);
    }

    /**
     * Deletes task at specified index.
     *
     * @param index The position of task to delete.
     * @return The deleted task.
     * @throws LeoException if index is invalid.
     */
    public Task deleteTask(int index) throws LeoException {
        if (index > tasks.size() - 1 || index < 0) {
            throw new LeoException("Error!!! Index outside of list bounds :(");
        }

        return tasks.remove(index);
    }

    /**
     * Marks task at specified index as done.
     *
     * @param index The position of task to mark.
     * @throws LeoException if index is invalid.
     */
    public void markTask(int index) throws LeoException {
        if (index > tasks.size() - 1 || index < 0) {
            throw new LeoException("Error!!! Index outside of list bounds :(");
        }
        tasks.get(index).mark();
    }

    /**
     * Unmarks task at specified index.
     *
     * @param index The position of task to unmark.
     * @throws LeoException if index is invalid.
     */
    public void unmarkTask(int index) throws LeoException {
        if (index > tasks.size() - 1 || index < 0) {
            throw new LeoException("Error!!! Index outside of list bounds :(");
        }
        tasks.get(index).unmark();
    }

    public int size() {
        return tasks.size();
    }

    /** Converts all tasks to save format strings. */
    public ArrayList<String> toSaveFormat() {
        ArrayList<String> data = new ArrayList<>();
        for (leo.task.Task t : tasks) {
            assert t != null : "Null task found in tasks list";
            String saveState = t.toSaveState();
            assert saveState != null
                    : "Task returned null save state: ";
            data.add(saveState);

        }
        assert data.size() == tasks.size()
                : "Save data size doesn't match tasks size: " + data.size() + " vs " + tasks.size();
        return data;
    }


    public boolean isEmpty() {
        return tasks.isEmpty();
    }

    /**
     * Gets task at specified index.
     *
     * @param i The position of task to retrieve.
     * @return The task at position i.
     */
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
    public ArrayList<Task> find(String taskDesc) throws LeoException {
        assert taskDesc != null
               : "Parser Class did not handle null case for command using find()";
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
