class MyResource implements AutoCloseable {

    public MyResource() {
        System.out.println("Resource Opened");
    }

    public void process() throws Exception {

        System.out.println("Processing Resource");

        throw new Exception("Original Processing Error");
    }

    @Override
    public void close() {

        System.out.println("Resource Closed");
    }
}

public class ResourceDemo {

    public static void main(String[] args) {

        try (MyResource resource = new MyResource()) {

            resource.process();

        } catch (Exception e) {

            System.out.println("Caught Exception: " + e.getMessage());

            for (Throwable suppressed : e.getSuppressed()) {
                System.out.println(
                        "Suppressed Exception: " + suppressed.getMessage());
            }

        } finally {

            System.out.println("Program Finished");
        }
    }
}