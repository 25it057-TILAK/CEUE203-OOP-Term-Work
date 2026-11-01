public class GenericContainers {

    // Generic Box class
    static class Box<T> {

        private T value;

        public void set(T value) {
            this.value = value;
        }

        public T get() {
            return value;
        }
    }

    // Generic Pair class
    static class Pair<A, B> {

        private A first;
        private B second;

        Pair(A first, B second) {
            this.first = first;
            this.second = second;
        }

        public A getFirst() {
            return first;
        }

        public B getSecond() {
            return second;
        }
    }

    // Generic method to return first element
    public static <T> T getFirstElement(T[] array) {
        if (array == null || array.length == 0) {
            return null;
        }

        return array[0];
    }

    public static void main(String[] args) {

        Box<Integer> intBox = new Box<>();
        intBox.set(100);

        Box<String> stringBox = new Box<>();
        stringBox.set("Hello Java");

        System.out.println("Integer Box: " + intBox.get());
        System.out.println("String Box: " + stringBox.get());

        Pair<String, Integer> student =
                new Pair<>("Tilak", 85);

        System.out.println("\nPair Details:");
        System.out.println("Name: " + student.getFirst());
        System.out.println("Marks: " + student.getSecond());

        Integer[] numbers = {10, 20, 30};
        String[] names = {"Java", "Python", "C++"};

        System.out.println("\nFirst Number: "
                + getFirstElement(numbers));

        System.out.println("First Name: "
                + getFirstElement(names));
    }
}