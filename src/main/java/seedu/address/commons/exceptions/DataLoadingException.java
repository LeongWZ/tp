package seedu.address.commons.exceptions;

import java.nio.file.Path;
import java.util.Optional;

/**
 * Represents an error during loading of data from a file.
 */
public class DataLoadingException extends Exception {

    private final Path filePath;

    /**
     * Constructs a {@code DataLoadingException} without a specified file path.
     */
    public DataLoadingException(Exception cause) {
        super(cause);
        this.filePath = null;
    }

    /**
     * Constructs a {@code DataLoadingException} with a specified file path.
     */
    public DataLoadingException(Exception cause, Path filePath) {
        super(cause);
        this.filePath = filePath;
    }

    public Optional<Path> getFilePath() {
        return Optional.ofNullable(filePath);
    }
}
