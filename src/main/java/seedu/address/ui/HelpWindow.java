package seedu.address.ui;

import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.stage.Stage;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.GetCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.SchemeCommand;
import seedu.address.logic.commands.SortCommand;
import seedu.address.logic.commands.StatisticsCommand;

/**
 * Controller for a help page
 */
public class HelpWindow extends UiPart<Stage> {

    public static final String USERGUIDE_URL = "https://ay2425s1-cs2103t-f14b-1.github.io/tp/UserGuide.html";
    public static final String HELP_MESSAGE = "Refer to the user guide: " + USERGUIDE_URL + "\n";

    private static final Logger logger = LogsCenter.getLogger(HelpWindow.class);
    private static final String FXML = "HelpWindow.fxml";

    private static final String TITLE = "Below are the commands you can use in the application:\n";

    private static final String ADD_COMMAND = AddCommand.MESSAGE_USAGE + "\n";

    private static final String LIST_COMMAND = ListCommand.MESSAGE_USAGE + "\n";

    private static final String EDIT_COMMAND = EditCommand.MESSAGE_USAGE + "\n";

    private static final String FIND_COMMAND = FindCommand.MESSAGE_USAGE + "\n";

    private static final String GET_COMMAND = GetCommand.MESSAGE_USAGE + "\n";

    private static final String DELETE_COMMAND = DeleteCommand.MESSAGE_USAGE + "\n";

    private static final String CLEAR_COMMAND = ClearCommand.MESSAGE_USAGE + "\n";

    private static final String EXIT_COMMAND = ExitCommand.MESSAGE_USAGE + "\n";

    private static final String STATISTICS_COMMAND = StatisticsCommand.MESSAGE_USAGE + "\n";

    private static final String SCHEME_COMMAND = SchemeCommand.MESSAGE_USAGE + "\n";

    private static final String SORT_COMMAND = SortCommand.MESSAGE_USAGE + "\n";

    private static final String HELP_COMMAND = HelpCommand.MESSAGE_USAGE + "\n";
    private static final String[] COMMANDS = {ADD_COMMAND, CLEAR_COMMAND, DELETE_COMMAND, EDIT_COMMAND,
        FIND_COMMAND, GET_COMMAND, LIST_COMMAND, STATISTICS_COMMAND, SCHEME_COMMAND, SORT_COMMAND,
        HELP_COMMAND, EXIT_COMMAND};

    @FXML
    private Button copyButton;

    @FXML
    private Label helpMessage;

    /**
     * Creates a new HelpWindow.
     *
     * @param root Stage to use as the root of the HelpWindow.
     */
    public HelpWindow(Stage root) {
        super(FXML, root);
        StringBuilder helpMessageBuilder = new StringBuilder(TITLE);
        for (String command : COMMANDS) {
            helpMessageBuilder.append(command);
        }
        helpMessageBuilder.append(HELP_MESSAGE);
        helpMessage.setText(helpMessageBuilder.toString());
    }
    /**
     * Creates a new HelpWindow.
     */
    public HelpWindow() {
        this(new Stage());
    }

    /**
     * Shows the help window.
     * @throws IllegalStateException
     *     <ul>
     *         <li>
     *             if this method is called on a thread other than the JavaFX Application Thread.
     *         </li>
     *         <li>
     *             if this method is called during animation or layout processing.
     *         </li>
     *         <li>
     *             if this method is called on the primary stage.
     *         </li>
     *         <li>
     *             if {@code dialogStage} is already showing.
     *         </li>
     *     </ul>
     */
    public void show() {
        logger.fine("Showing help page about the application.");
        getRoot().show();
        getRoot().centerOnScreen();
    }

    /**
     * Returns true if the help window is currently being shown.
     */
    public boolean isShowing() {
        return getRoot().isShowing();
    }

    /**
     * Hides the help window.
     */
    public void hide() {
        getRoot().hide();
    }

    /**
     * Focuses on the help window.
     */
    public void focus() {
        getRoot().requestFocus();
    }

    /**
     * Copies the URL to the user guide to the clipboard.
     */
    @FXML
    private void copyUrl() {
        final Clipboard clipboard = Clipboard.getSystemClipboard();
        final ClipboardContent url = new ClipboardContent();
        url.putString(USERGUIDE_URL);
        clipboard.setContent(url);
    }
}
