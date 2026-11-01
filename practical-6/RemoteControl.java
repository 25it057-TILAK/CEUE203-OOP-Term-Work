interface Switchable {

    void on();

    void off();

    default void toggle() {
        on();
        off();
    }
}

class Fan implements Switchable {

    @Override
    public void on() {
        System.out.println("Fan is ON");
    }

    @Override
    public void off() {
        System.out.println("Fan is OFF");
    }
}

class Light implements Switchable {

    @Override
    public void on() {
        System.out.println("Light is ON");
    }

    @Override
    public void off() {
        System.out.println("Light is OFF");
    }
}

@FunctionalInterface
interface DevicePermission {
    boolean maySwitchOn(Switchable device, int hour);
}

public class RemoteControl {

    public static void main(String[] args) {

        Switchable[] devices = {
            new Fan(),
            new Light()
        };

        System.out.println("Remote Control:");

        for (Switchable device : devices) {
            device.toggle();
        }

        System.out.println("\nAnonymous Class:");

        DevicePermission permission1 = new DevicePermission() {

            @Override
            public boolean maySwitchOn(Switchable device, int hour) {
                return hour >= 6 && hour <= 22;
            }
        };

        System.out.println("Allowed: " +
                permission1.maySwitchOn(devices[0], 10));

        System.out.println("\nLambda Expression:");

        DevicePermission permission2 =
                (device, hour) -> hour >= 6 && hour <= 22;

        System.out.println("Allowed: " +
                permission2.maySwitchOn(devices[1], 23));
    }
}