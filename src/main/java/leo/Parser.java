package leo;

import leo.command.Command;
import leo.command.DeadlineCommand;
import leo.command.DeleteCommand;
import leo.command.EventCommand;
import leo.command.ExitCommand;
import leo.command.FindCommand;
import leo.command.InterviewCommand;
import leo.command.ListCommand;
import leo.command.MarkCommand;
import leo.command.TodoCommand;
import leo.command.UnmarkCommand;


/** Parses user input strings into executable Command objects. */
public class Parser {
    enum CommandType {
        BYE, DEADLINE, DELETE, LIST, MARK, UNMARK, TODO,
        EVENT, FIND, INTERVIEW, UNKNOWN
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
        switch (command) {
        case LIST:
            return new ListCommand();

        case BYE:
            return new ExitCommand();

        case MARK:
            assertHasParameters(parts, command);
            try {
                int index = Integer.parseInt(parts[1]) - 1;

                return new MarkCommand(index);
            } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
                throw new LeoException("Please provide a valid task number.");
            }

        case UNMARK:
            assertHasParameters(parts, command);
            try {
                int index = Integer.parseInt(parts[1]) - 1;
                return new UnmarkCommand(index);
            } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
                throw new LeoException("Please provide a valid task number.");
            }

        case TODO:
            assertHasParameters(parts, command);
            return new TodoCommand(parts[1]);

        case DEADLINE:
            assertHasParameters(parts, command);
            return new DeadlineCommand(parts[1]);

        case EVENT:
            assertHasParameters(parts, command);
            return new EventCommand(parts[1]);

        case DELETE:
            assertHasParameters(parts, command);
            try {
                int index = Integer.parseInt(parts[1]) - 1;
                return new DeleteCommand(index);
            } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
                throw new LeoException("Please provide a valid task number.");
            }

        case FIND:
            assertHasParameters(parts, command);
            return new FindCommand(parts[1]);
        case INTERVIEW:
            assertHasParameters(parts, command);
            return new InterviewCommand(parts[1]);

        case UNKNOWN:

        default:
            throw new LeoException("Unknown command: " + parts[0]);
        }
    }

    private static void validateCommandParams(CommandType command, String[] parts) throws LeoException {
        switch (command) {
        case LIST, BYE:
            break;
        default:
            if (parts.length < 2) {
                throw new LeoException(String.format(
                        "Command '%s' requires a parameter.",
                        command.toString().toLowerCase()));
            }
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


    private static void assertHasParameters(String[] parts, CommandType command) {
        assert parts.length >= 2
                : command + " command missing parameters. Possibly wrongly included "
                        + command + " command in single parameter commands.";
    }
}
