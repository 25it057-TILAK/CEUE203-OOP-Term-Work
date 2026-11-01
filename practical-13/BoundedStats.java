public class BoundedStats {

    // Generic method accepting only Number types
    public static <N extends Number> double average(N[] values) {

        if (values == null || values.length == 0) {
            throw new IllegalArgumentException(
                    "Array cannot be empty or null.");
        }

        double sum = 0;

        for (N value : values) {
            sum += value.doubleValue();
        }

        return sum / values.length;
    }

    public static void main(String[] args) {

        Integer[] integers = {10, 20, 30, 40, 50};

        Double[] doubles = {10.5, 20.5, 30.5};

        Long[] longs = {100L, 200L, 300L, 400L};

        System.out.println("Integer Average: "
                + average(integers));

        System.out.println("Double Average: "
                + average(doubles));

        System.out.println("Long Average: "
                + average(longs));

        // String[] is not allowed because String
        // does not extend the Number class.
    }
}