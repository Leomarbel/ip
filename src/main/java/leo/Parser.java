package leo;

import java.util.function.Function;

import leo.command.Command;
import leo.command.DeadlineCommand;
import leo.command.DeleteCommand;
import leo.command.EventCommand;
import leo.command.ExitCommand;
import leo.command.FindCommand;
import leo.command.ListCommand;
import leo.command.MarkCommand;
import leo.command.TodoCommand;
import leo.command.UnmarkCommand;



/** Parses user input strings into executable Command objects. */
public class Parser {
    enum CommandType {
        BYE, LIST, MARK, UNMARK, TODO, DEADLINE, EVENT, DELETE, FIND, UNKNOWN
    }

    /**
     * Parses user command string and returns corresponding Command.
     * Uses a switch and enum Command_Enum to call the command
     * @param line The user input command string.
     * @return The corresponding Command object.
     * @throws LeoException if command is invalid or has missing parameters.
     */
    public static Command parse(String line) throws LeoException {
        if (line == null || line.trim().isEmpty()) {
            throw new LeoException("Command cannot be empty.");
        }
        String[] parts = getLineParts(line);
        //Convert input command into CommandType
        CommandType command = parseCommand(parts[0]);
        //Check command type matches number of parameters
        validateCommandParams(command, parts);
        return createCommand(command, parts);
    }
    /**
     * Converts user input line by spaces
     * @param line The user input command string.
     * @return the parsed String[].
     */
    private static String[] getLineParts(String line) {
        return line.trim().split("\\s+", 2);
    }
    /**
     * Parses user input to the corresponding command class.
     * @param command The command from user input. (First element of user input string)
     * @param parts The arguments given to the command class.
     * @return The corresponding Command object.
     * @throws LeoException if command is invalid or has missing parameters.
     */
    private static Command createCommand(CommandType command, String[] parts) throws LeoException {
        return switch (command) {
        case LIST -> new ListCommand();
        case BYE -> new ExitCommand();
        case MARK -> createIndexCommand(parts, MarkCommand::new);
        case UNMARK -> createIndexCommand(parts, UnmarkCommand::new);
        case DELETE -> createIndexCommand(parts, DeleteCommand::new);
        case TODO -> new TodoCommand(parts[1]);
        case DEADLINE -> new DeadlineCommand(parts[1]);
        case EVENT -> new EventCommand(parts[1]);
        case FIND -> new FindCommand(parts[1]);
        case UNKNOWN -> throw new LeoException("Unknown command: " + parts[0]);
        };
    }
    private static void validateCommandParams(CommandType command, String[] parts) throws LeoException {
        switch (command) {
        case LIST, BYE:
            break;
        default:
            if (parts.length < 2) {
                throw new LeoException("Missing description or index.");
                assertHasParameters
            }
        }
    }


    /**
     * Validates the index to be used in executing the Command by checking if
     * the index is within bounds.
     * @param line the index (String form) to be parsed
     * @param constructor the command to be instantiated by calling
     * @return The Command to be executed
     */
    private static Command createIndexCommand(String[] line,
                                              Function<Integer, Command> constructor) throws LeoException {
        try {
            int index = Integer.parseInt(line[1]) - 1;
            return constructor.apply(index);
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
            throw new LeoException("Please provide a valid task number.");
        }
    }


    /**
     * Converts string input to corresponding Command_Enum value.
     * @param input The command string to parse.
     * @return The corresponding Command_Enum value
     */
    private static CommandType parseCommand(String input) {
        try {
            return CommandType.valueOf(input.trim().toUpperCase());
        } catch (IllegalArgumentException e) {
            return CommandType.UNKNOWN;
        }
    }


    private static void assertHasParameters(String[] parts, CommandType command){
        assert parts.length >= 2 :
                command + " command missing parameters. Possibly wrongly included "
                        + command + " command in single parameter commands.";
    }
}
