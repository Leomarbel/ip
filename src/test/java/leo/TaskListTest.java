package leo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import leo.task.Deadline;
import leo.task.Event;
import leo.task.Task;
import leo.task.Todo;

public class TaskListTest {
    private TaskList taskList;
    private Task todoTask;
    private Task deadlineTask;
    private Task eventTask;

    @BeforeEach
    void setUp() {
        taskList = new TaskList();
        todoTask = new Todo("Read book", false);
        deadlineTask = new Deadline("Submit assignment", false,
                LocalDateTime.of(2024, 12, 25, 23, 59));
        eventTask = new Event("Team meeting", false, "Friday", "Sunday");
    }

    @Test
    @DisplayName("Test TaskList constructor with empty list")
    void testEmptyConstructor() {
        assertNotNull(taskList);
        assertTrue(taskList.isEmpty());
        assertEquals(0, taskList.size());
    }

    @Test
    @DisplayName("Test TaskList constructor with existing list")
    void testConstructorWithList() {
        ArrayList<Task> tasks = new ArrayList<>();
        tasks.add(todoTask);
        tasks.add(deadlineTask);

        TaskList populatedList = new TaskList(tasks);

        assertNotNull(populatedList);
        assertFalse(populatedList.isEmpty());
        assertEquals(2, populatedList.size());
    }

    @Test
    @DisplayName("Test Tasklist addTask method")
    public void add_oneTask_sizeIncreases() {
        TaskList list = new TaskList();
        Task task = new Todo("read book", false);

        list.addTask(task);

        assertEquals(1, list.size());
    }

    @Test
    @DisplayName("Test deleteTask with valid index")
    void testDeleteTask_validIndex() throws LeoException {
        taskList.addTask(todoTask);
        taskList.addTask(deadlineTask);
        taskList.addTask(eventTask);

        Task deleted = taskList.deleteTask(1);
        assertEquals(deadlineTask, deleted);
        assertEquals(2, taskList.size());
        assertEquals(todoTask, taskList.get(0));
        assertEquals(eventTask, taskList.get(1));
    }

    @DisplayName("Test deleteTask with invalid index - negative")
    void testDeleteTask_invalidIndexNegative() {
        taskList.addTask(todoTask);

        LeoException exception = assertThrows(LeoException.class, () -> {
            taskList.deleteTask(-1);
        });
        assertEquals("Error!!! Index outside of list bounds :(", exception.getMessage());
    }

    @Test
    @DisplayName("Test deleteTask with invalid index - out of bounds")
    void testDeleteTask_invalidIndexOutOfBounds() {
        taskList.addTask(todoTask);

        LeoException exception = assertThrows(LeoException.class, () -> {
            taskList.deleteTask(5);
        });
        assertEquals("Error!!! Index outside of list bounds :(", exception.getMessage());
    }

    @Test
    @DisplayName("Test deleteTask on empty list")
    void testDeleteTask_emptyList() {
        LeoException exception = assertThrows(LeoException.class, () -> {
            taskList.deleteTask(0);
        });
        assertEquals("Error!!! Index outside of list bounds :(", exception.getMessage());
    }

    @Test
    @DisplayName("Test markTask with valid index")
    void testMarkTask_validIndex() throws LeoException {
        taskList.addTask(todoTask);
        assertFalse(todoTask.isMarked());

        taskList.markTask(0);
        assertTrue(todoTask.isMarked());
    }

    @Test
    @DisplayName("Test markTask with invalid index")
    void testMarkTask_invalidIndex() {
        LeoException exception = assertThrows(LeoException.class, () -> {
            taskList.markTask(0);
        });
        assertEquals("Error!!! Index outside of list bounds :(", exception.getMessage());
    }

    @Test
    @DisplayName("Test unmarkTask with valid index")
    void testUnmarkTask_validIndex() throws LeoException {
        taskList.addTask(todoTask);
        todoTask.mark(); // First mark it
        assertTrue(todoTask.isMarked());

        taskList.unmarkTask(0);
        assertFalse(todoTask.isMarked());
    }

    @Test
    @DisplayName("Test size method")
    void testSize() {
        assertEquals(0, taskList.size());

        taskList.addTask(todoTask);
        assertEquals(1, taskList.size());

        taskList.addTask(deadlineTask);
        assertEquals(2, taskList.size());

        taskList.addTask(eventTask);
        assertEquals(3, taskList.size());
    }

    @Test
    @DisplayName("Test isEmpty method")
    void testIsEmpty() {
        assertTrue(taskList.isEmpty());

        taskList.addTask(todoTask);
        assertFalse(taskList.isEmpty());
    }

    @Test
    @DisplayName("Test unmarkTask with invalid index")
    void testUnmarkTask_invalidIndex() {
        LeoException exception = assertThrows(LeoException.class, () -> {
            taskList.unmarkTask(0);
        });
        assertEquals("Error!!! Index outside of list bounds :(", exception.getMessage());
    }

    @Test
    @DisplayName("Test get method with invalid index")
    void testGet_invalidIndex() {
        taskList.addTask(todoTask);

        // get() should throw IndexOutOfBoundsException for invalid index
        assertThrows(IndexOutOfBoundsException.class, () -> {
            taskList.get(5);
        });
    }

    @Test
    @DisplayName("Test toSaveFormat method")
    void testToSaveFormat() {
        taskList.addTask(todoTask);
        todoTask.mark();

        taskList.addTask(deadlineTask);

        ArrayList<String> saveData = taskList.toSaveFormat();

        assertEquals(2, saveData.size());
        assertTrue(saveData.get(0).contains("T"));
        assertTrue(saveData.get(0).contains("Read book"));
        assertTrue(saveData.get(1).contains("D"));
        assertTrue(saveData.get(1).contains("Submit assignment"));
    }

    @Test
    @DisplayName("Test toSaveFormat on empty list")
    void testToSaveFormat_emptyList() {
        ArrayList<String> saveData = taskList.toSaveFormat();
        assertNotNull(saveData);
        assertTrue(saveData.isEmpty());
    }

    @Test
    @DisplayName("Integration test: add, mark, unmark, delete sequence")
    void testIntegrationSequence() throws LeoException {
        // Initial state
        assertTrue(taskList.isEmpty());

        // Add tasks
        taskList.addTask(todoTask);
        taskList.addTask(deadlineTask);
        assertEquals(2, taskList.size());

        // Mark first task
        taskList.markTask(0);
        assertTrue(todoTask.isMarked());

        // Unmark first task
        taskList.unmarkTask(0);
        assertFalse(todoTask.isMarked());

        // Delete second task
        Task deleted = taskList.deleteTask(1);
        assertEquals(deadlineTask, deleted);
        assertEquals(1, taskList.size());

        // Verify remaining task
        assertEquals(todoTask, taskList.get(0));
    }
}

