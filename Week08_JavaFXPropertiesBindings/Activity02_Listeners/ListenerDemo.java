public class ListenerDemo {
    public static void main(String[] args) {
        Person person = new Person();

        // InvalidationListener: no old/new values
        person.nameProperty().addListener(observable ->
                System.out.println("[Invalidation] Name has changed!"));

        // ChangeListener: gets old and new values
        person.nameProperty().addListener((observable, oldValue, newValue) ->
                System.out.println("[Change] Name changed from '" + oldValue + "' to '" + newValue + "'"));

        System.out.println("-- set Alice --");
        person.setName("Alice");
        System.out.println("-- set Alice again (same value) --");
        person.setName("Alice");   // no listener fires
        System.out.println("-- set Bob --");
        person.setName("Bob");
    }
}
