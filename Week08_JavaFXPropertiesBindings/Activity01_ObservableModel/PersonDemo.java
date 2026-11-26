public class PersonDemo {
    public static void main(String[] args) {
        Person p = new Person();
        p.setName("Alice");
        p.setAge(20);
        System.out.println("Name: " + p.getName() + ", Age: " + p.getAge());

        // The property object itself can be observed
        p.nameProperty().addListener((obs, oldV, newV) ->
                System.out.println("Name changed from " + oldV + " to " + newV));
        p.setName("Bob");
        p.ageProperty().set(21);
        System.out.println("Age via property: " + p.ageProperty().get());
    }
}
