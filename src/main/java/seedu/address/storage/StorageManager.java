package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataLoadingException;
import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.UserPrefs;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.person.Person;

/**
 * Manages storage of AddressBook data in local storage.
 */
public class StorageManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private final AddressBookStorage addressBookStorage;
    private final AddressBookStorage archivedAddressBookStorage;
    private final AppointmentStorage appointmentStorage;
    private final UserPrefsStorage userPrefsStorage;

    /**
     * Creates a {@code StorageManager} with the given {@code AddressBookStorage} and {@code UserPrefStorage}.
     */
    public StorageManager(AddressBookStorage addressBookStorage,
                          AddressBookStorage archivedAddressBookStorage,
                          AppointmentStorage appointmentStorage,
                          UserPrefsStorage userPrefsStorage) {
        this.addressBookStorage = addressBookStorage;
        this.archivedAddressBookStorage = archivedAddressBookStorage;
        this.appointmentStorage = appointmentStorage;
        this.userPrefsStorage = userPrefsStorage;
    }

    // ================ UserPrefs methods ==============================

    @Override
    public Path getUserPrefsFilePath() {
        return userPrefsStorage.getUserPrefsFilePath();
    }

    @Override
    public Optional<UserPrefs> readUserPrefs() throws DataLoadingException {
        return userPrefsStorage.readUserPrefs();
    }

    @Override
    public void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException {
        userPrefsStorage.saveUserPrefs(userPrefs);
    }

    // ================ AddressBook methods ==============================

    @Override
    public Path getAddressBookFilePath() {
        return addressBookStorage.getAddressBookFilePath();
    }

    @Override
    public Optional<ReadOnlyAddressBook> readAddressBook() throws DataLoadingException {
        return combineAddressBooks(List.of(
                readAddressBook(addressBookStorage, addressBookStorage.getAddressBookFilePath()),
                readAddressBook(archivedAddressBookStorage, archivedAddressBookStorage.getAddressBookFilePath())
        ));
    }

    @Override
    public Optional<ReadOnlyAddressBook> readAddressBook(Path filePath) throws DataLoadingException {
        return readAddressBook(addressBookStorage, filePath);
    }

    private Optional<ReadOnlyAddressBook> readAddressBook(AddressBookStorage storage,
                                                          Path filePath) throws DataLoadingException {
        logger.fine("Attempting to read data from file: " + filePath);
        return storage.readAddressBook(filePath);
    }

    @Override
    public void saveAddressBook(ReadOnlyAddressBook addressBook) throws IOException {
        saveAddressBook(addressBookStorage,
                filterAddressBook(addressBook, person -> !person.isArchived()),
                addressBookStorage.getAddressBookFilePath());

        saveAddressBook(archivedAddressBookStorage,
                filterAddressBook(addressBook, Person::isArchived),
                archivedAddressBookStorage.getAddressBookFilePath());
    }

    @Override
    public void saveAddressBook(ReadOnlyAddressBook addressBook, Path filePath) throws IOException {
        saveAddressBook(addressBookStorage, addressBook, filePath);
    }

    private void saveAddressBook(AddressBookStorage storage, ReadOnlyAddressBook addressBook,
                                 Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        storage.saveAddressBook(addressBook, filePath);
    }

    @Override
    public Path getAppointmentFilePath() {
        return appointmentStorage.getAppointmentFilePath();
    }

    @Override
    public Optional<List<Appointment>> readAppointments() throws DataLoadingException {
        return appointmentStorage.readAppointments();
    }

    @Override
    public Optional<List<Appointment>> readAppointments(Path filePath) throws DataLoadingException {
        logger.fine("Attempting to read data from file: " + filePath);
        return appointmentStorage.readAppointments(filePath);
    }

    @Override
    public void saveAppointments(List<Appointment> appointments) throws IOException {
        appointmentStorage.saveAppointments(appointments);
    }

    @Override
    public void saveAppointments(List<Appointment> appointments, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        appointmentStorage.saveAppointments(appointments, filePath);
    }

    private Optional<ReadOnlyAddressBook> combineAddressBooks(
            List<Optional<? extends ReadOnlyAddressBook>> addressBooks) {
        AddressBook combinedAddressBook = new AddressBook();

        addressBooks.stream()
                .flatMap(Optional::stream)
                .flatMap(addressBook -> addressBook.getPersonList().stream())
                .forEach(combinedAddressBook::addPerson);

        return Optional.<ReadOnlyAddressBook>of(combinedAddressBook)
                .filter(addressBook -> !addressBook.getPersonList().isEmpty());
    }

    private ReadOnlyAddressBook filterAddressBook(ReadOnlyAddressBook addressBook, Predicate<Person> predicate) {
        AddressBook filteredAddressBook = new AddressBook();

        addressBook.getPersonList()
                .stream()
                .filter(predicate)
                .forEach(filteredAddressBook::addPerson);

        return filteredAddressBook;
    }
}
