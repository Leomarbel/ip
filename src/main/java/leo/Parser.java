package leo;

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

        String[] parts = line.trim().split("\\s+", 2);
        CommandType command = parseCommand(parts[0]);

        switch (command) {
            case LIST, BYE:
                break;
            default:
                if (parts.length < 2) {
                    throw new LeoException("Missing description or index.");
                }
        }

        switch (command) {
        case LIST:
            return new ListCommand();

        case BYE:
            return new ExitCommand();

        case MARK:
            try {
                int index = Integer.parseInt(parts[1]) - 1;

                return new MarkCommand(index);
            } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
                throw new LeoException("Please provide a valid task number.");
            }

        case UNMARK:
            try {
                int index = Integer.parseInt(parts[1]) - 1;
                return new UnmarkCommand(index);
            } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
                throw new LeoException("Please provide a valid task number.");
            }

        case TODO:
            return new TodoCommand(parts[1]);

        case DEADLINE:
            return new DeadlineCommand(parts[1]);

        case EVENT:
            return new EventCommand(parts[1]);

        case DELETE:
            try {
                int index = Integer.parseInt(parts[1]) - 1;
                return new DeleteCommand(index);
            } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
                throw new LeoException("Please provide a valid task number.");
            }

        case FIND:
            return new FindCommand(parts[1]);

        case UNKNOWN:

        default:
            throw new LeoException("Unknown command: " + parts[0]);
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

}
