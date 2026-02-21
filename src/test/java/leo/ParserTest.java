package leo;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import leo.command.Command;
import leo.command.DeadlineCommand;
import leo.command.DeleteCommand;
import leo.command.EventCommand;
import leo.command.ExitCommand;
import leo.command.ListCommand;
import leo.command.MarkCommand;
import leo.command.TodoCommand;
import leo.command.UnmarkCommand;




class ParserTest {

    // Test valid command parsing
    @Test
    @DisplayName("Parse valid LIST command")
    void listCommand_success() throws LeoException {
        Command command = Parser.parse("list");
        assertInstanceOf(ListCommand.class, command);
    }

    @Test
    @DisplayName("Parse valid BYE command")
    void byeCommand_success() throws LeoException {
        Command command = Parser.parse("bye");
        assertInstanceOf(ExitCommand.class, command);
    }

    @Test
    @DisplayName("Parse valid MARK command with valid index")
    void markCommand_validIndex() throws LeoException {
        Command command = Parser.parse("mark 5");
        assertInstanceOf(MarkCommand.class, command);
    }

    @Test
    @DisplayName("Parse valid UNMARK command with valid index")
    void unmarkCommand_validIndex() throws LeoException {
        Command command = Parser.parse("unmark 3");
        assertInstanceOf(UnmarkCommand.class, command);
    }

    @Test
    @DisplayName("Parse valid DELETE command with valid index")
    void deleteCommand_validIndex() throws LeoException {
        Command command = Parser.parse("delete 2");
        assertInstanceOf(DeleteCommand.class, command);
    }

    @Test
    @DisplayName("Parse valid TODO command with description")
    void todoCommand_validDescription() throws LeoException {
        Command command = Parser.parse("todo Read book");
        assertInstanceOf(TodoCommand.class, command);
    }

    @Test
    @DisplayName("Parse valid DEADLINE command with description")
    void deadlineCommand_validDescription() throws LeoException {
        Command command = Parser.parse("deadline Submit assignment /by 2024-12-25 2359");
        assertInstanceOf(DeadlineCommand.class, command);
    }

    @Test
    @DisplayName("Parse valid EVENT command with description")
    void eventCommand_validDescription() throws LeoException {
        Command command = Parser.parse("event Team meeting /from 2pm /to 4pm");
        assertInstanceOf(EventCommand.class, command);
    }

    // Test error cases for MARK/UNMARK/DELETE
    @Test
    @DisplayName("Parse MARK command missing index throws exception")
    void markCommand_missingIndex_throwsException() {
        LeoException exception = assertThrows(LeoException.class, () -> {
            Parser.parse("mark");
        });
        assertEquals("Command 'mark' requires a parameter.", exception.getMessage());
    }

    @Test
    @DisplayName("Parse MARK command with non-numeric index throws exception")
    void markCommand_invalidIndex_throwsException() {
        LeoException exception = assertThrows(LeoException.class, () -> {
            Parser.parse("mark abc");
        });
        assertEquals("Please provide a valid task number.", exception.getMessage());
    }

    @Test
    @DisplayName("Parse MARK command with negative index creates command")
    void markCommand_negativeIndex_createsCommand() throws LeoException {
        Command command = Parser.parse("mark -1");
        assertInstanceOf(MarkCommand.class, command);
    }

    @Test
    @DisplayName("Parse MARK command with zero index creates command")
    void markCommand_zeroIndex_createsCommand() throws LeoException {
        Command command = Parser.parse("mark 0");
        assertInstanceOf(MarkCommand.class, command);
    }

    @Test
    @DisplayName("Parse DELETE command missing index throws exception")
    void deleteCommand_missingIndex_throwsException() {
        LeoException exception = assertThrows(LeoException.class, () -> {
            Parser.parse("delete");
        });
        assertEquals("Command 'delete' requires a parameter.", exception.getMessage());
    }

    // Test error cases for TODO/DEADLINE/EVENT
    @Test
    @DisplayName("Parse TODO command missing description throws exception")
    void todoCommand_missingDescription_throwsException() {
        LeoException exception = assertThrows(LeoException.class, () -> {
            Parser.parse("todo");
        });
        assertEquals("Command 'todo' requires a parameter.", exception.getMessage());
    }

    @Test
    @DisplayName("Parse DEADLINE command missing description throws exception")
    void deadlineCommand_missingDescription_throwsException() {
        LeoException exception = assertThrows(LeoException.class, () -> {
            Parser.parse("deadline");
        });
        assertEquals("Command 'deadline' requires a parameter.", exception.getMessage());
    }

    @Test
    @DisplayName("Parse EVENT command missing description throws exception")
    void eventCommand_missingDescription_throwsException() {
        LeoException exception = assertThrows(LeoException.class, () -> {
            Parser.parse("event");
        });
        assertEquals("Command 'event' requires a parameter.", exception.getMessage());
    }

    // Test unknown commands
    @Test
    @DisplayName("Parse unknown command throws exception")
    void unknownCommand_throwsException() {
        LeoException exception = assertThrows(LeoException.class, () -> {
            Parser.parse("randomcommand 1");
        });
        assertEquals("Unknown command: randomcommand", exception.getMessage());
    }

    @Test
    @DisplayName("Parse gibberish command throws exception")
    void gibberishCommand_throwsException() {
        LeoException exception = assertThrows(LeoException.class, () -> {
            Parser.parse("!@#$% dsa");
        });
        assertEquals("Unknown command: !@#$%", exception.getMessage());
    }


    // Test commands with extra whitespace
    @Test
    @DisplayName("Parse command with leading/trailing whitespace")
    void command_withExtraWhitespace() throws LeoException {
        Command command = Parser.parse("  todo   Read book  ");
        assertInstanceOf(TodoCommand.class, command);
    }

    @Test
    @DisplayName("Parse MARK command with extra whitespace")
    void markCommand_withExtraWhitespace() throws LeoException {
        Command command = Parser.parse("  mark   5  ");
        assertInstanceOf(MarkCommand.class, command);
    }

    // Test command with multiple spaces in description
    @Test
    @DisplayName("Parse TODO command with multiple words")
    void todoCommand_withMultipleWords() throws LeoException {
        Command command = Parser.parse("todo Read book and write summary");
        assertInstanceOf(TodoCommand.class, command);
    }

    // Test boundary: maximum integer
    @Test
    @DisplayName("Parse command with maximum integer index")
    void command_withMaxIntegerIndex() throws LeoException {
        String maxInt = String.valueOf(Integer.MAX_VALUE);
        Command command = Parser.parse("mark " + maxInt);
        assertInstanceOf(MarkCommand.class, command);
    }

    // Test ParseCommand private method indirectly
    @Test
    @DisplayName("Command parsing handles mixed case")
    void parseCommand_handlesMixedCase() throws LeoException {
        // Test that ParseCommand method (called via parse) handles case correctly
        Command command1 = Parser.parse("ToDo read book");
        assertInstanceOf(TodoCommand.class, command1);

        Command command2 = Parser.parse("DeAdLiNe task /by tomorrow");
        assertInstanceOf(DeadlineCommand.class, command2);

        Command command3 = Parser.parse("EvEnT meeting /from now /to later");
        assertInstanceOf(EventCommand.class, command3);
    }

    // Test that commands without parameters work correctly
    @Test
    @DisplayName("Commands without parameters (LIST, BYE) work with extra text")
    void noParamCommands_withExtraText_throwsException() {
        // LIST and BYE should ignore extra text or throw exception
        // Depends on your implementation - currently they ignore extra text
        assertDoesNotThrow(() -> {
            Parser.parse("list extra");
            Parser.parse("bye now");
        });
    }

    // Test integration: multiple commands in sequence
    @Test
    @DisplayName("Parse multiple valid commands in sequence")
    void multipleCommands_integration() throws LeoException {
        Command[] commands = new Command[]{
                Parser.parse("todo Task 1"),
                Parser.parse("deadline Task 2 /by tomorrow"),
                Parser.parse("mark 1"),
                Parser.parse("unmark 1"),
                Parser.parse("delete 1"),
                Parser.parse("list"),
                Parser.parse("bye")
        };

        assertInstanceOf(TodoCommand.class, commands[0]);
        assertInstanceOf(DeadlineCommand.class, commands[1]);
        assertInstanceOf(MarkCommand.class, commands[2]);
        assertInstanceOf(UnmarkCommand.class, commands[3]);
        assertInstanceOf(DeleteCommand.class, commands[4]);
        assertInstanceOf(ListCommand.class, commands[5]);
        assertInstanceOf(ExitCommand.class, commands[6]);
    }
}
