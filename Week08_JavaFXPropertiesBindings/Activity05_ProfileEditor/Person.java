import javafx.beans.property.*;

public class Person {
    private final StringProperty name = new SimpleStringProperty("");
    private final IntegerProperty age = new SimpleIntegerProperty(0);

    public String getName() { return name.get(); }
    public void setName(String value) { name.set(value); }
    public int getAge() { return age.get(); }
    public void setAge(int value) { age.set(value); }

    // Property getters (needed for bindings and listeners)
    public StringProperty nameProperty() { return name; }
    public IntegerProperty ageProperty() { return age; }
}
