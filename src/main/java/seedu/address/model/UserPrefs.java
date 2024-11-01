package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

import seedu.address.commons.core.GuiSettings;

/**
 * Represents User's preferences.
 */
public class UserPrefs implements ReadOnlyUserPrefs {

    private GuiSettings guiSettings = new GuiSettings();
    private Path socialBookFilePath = Paths.get("data" , "socialbook.json");
    private Path archivedSocialBookFilePath = Paths.get("data", "socialbook-archived.json");
    private Path appointmentFilePath = Paths.get("data" , "appointments.json");

    /**
     * Creates a {@code UserPrefs} with default values.
     */
    public UserPrefs() {}

    /**
     * Creates a {@code UserPrefs} with the prefs in {@code userPrefs}.
     */
    public UserPrefs(ReadOnlyUserPrefs userPrefs) {
        this();
        resetData(userPrefs);
    }

    /**
     * Resets the existing data of this {@code UserPrefs} with {@code newUserPrefs}.
     */
    public void resetData(ReadOnlyUserPrefs newUserPrefs) {
        requireNonNull(newUserPrefs);
        setGuiSettings(newUserPrefs.getGuiSettings());
        setSocialBookFilePath(newUserPrefs.getSocialBookFilePath());
        setArchivedSocialBookFilePath(newUserPrefs.getArchivedSocialBookFilePath());
        setAppointmentFilePath(newUserPrefs.getAppointmentFilePath());
    }

    public GuiSettings getGuiSettings() {
        return guiSettings;
    }

    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        this.guiSettings = guiSettings;
    }

    public Path getSocialBookFilePath() {
        return socialBookFilePath;
    }

    public void setSocialBookFilePath(Path socialBookFilePath) {
        requireNonNull(socialBookFilePath);
        this.socialBookFilePath = socialBookFilePath;
    }

    public Path getArchivedSocialBookFilePath() {
        return archivedSocialBookFilePath;
    }

    public void setArchivedSocialBookFilePath(Path archivedSocialBookFilePath) {
        requireNonNull(archivedSocialBookFilePath);
        this.archivedSocialBookFilePath = archivedSocialBookFilePath;
    }

    public Path getAppointmentFilePath() {
        return appointmentFilePath;
    }

    public void setAppointmentFilePath(Path appointmentFilePath) {
        requireNonNull(appointmentFilePath);
        this.appointmentFilePath = appointmentFilePath;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof UserPrefs otherUserPrefs)) {
            return false;
        }

        return guiSettings.equals(otherUserPrefs.guiSettings)
                && socialBookFilePath.equals(otherUserPrefs.socialBookFilePath)
                && archivedSocialBookFilePath.equals(otherUserPrefs.archivedSocialBookFilePath)
                && appointmentFilePath.equals(otherUserPrefs.appointmentFilePath);
    }

    @Override
    public int hashCode() {
        return Objects.hash(guiSettings, socialBookFilePath, archivedSocialBookFilePath);
    }

    @Override
    public String toString() {
        return "Gui Settings : " + guiSettings
                + "\nLocal socialbook data file location : " + socialBookFilePath
                + "\nLocal archived socialbook data file location : " + archivedSocialBookFilePath
                + "\nLocal appointment data file location : " + appointmentFilePath;
    }
}
