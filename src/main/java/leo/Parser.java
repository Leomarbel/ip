package leo;

import leo.command.*;

/** Parses user input strings into executable Command objects. */
public class Parser {
    enum Command_Enum {
        BYE, LIST, MARK, UNMARK, TODO, DEADLINE, EVENT, DELETE, UNKNOWN
    }

    /**
     * Parses user command string and returns corresponding Command.
     * Uses a switch and enum Command_Enum to call the command
     * @param commands The user input command string.
     * @return The corresponding Command object.
     * @throws LeoException if command is invalid or has missing parameters.
     */
    public static Command parse(String commands) throws LeoException {

        String[] parts = commands.trim().split("\\s+", 2);
        Command_Enum command = ParseCommand(parts[0]);

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

        case UNKNOWN:

        default:
            throw new LeoException("Unknown command: " + command);
        }
    }

    /**
     * Converts string input to corresponding Command_Enum value.
     * @param input The command string to parse.
     * @return The corresponding Command_Enum value
     */
    private static Command_Enum ParseCommand(String input) {
        try {
            return Command_Enum.valueOf(input.trim().toUpperCase());
        } catch (IllegalArgumentException e) {
            return Command_Enum.UNKNOWN;
        }
    }

}
