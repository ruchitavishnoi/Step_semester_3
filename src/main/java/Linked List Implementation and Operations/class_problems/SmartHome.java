import java.util.HashMap;
import java.util.Map;

interface Dimmable {
    void dim(int level);
}

interface Schedulable {
    void schedule(String time);
}

interface EnergyMonitored {
    int getEnergy();
}

abstract class Device {
    private String id;
    private boolean on;

    public Device(String id) {
        this.id = id;
        this.on = false;
    }

    public String getId() {
        return id;
    }

    public boolean isOn() {
        return on;
    }

    public void turnOn() {
        this.on = true;
        System.out.println(id + " is ON");
    }

    public void turnOff() {
        this.on = false;
        System.out.println(id + " is OFF");
    }
}

class Light extends Device implements Dimmable, Schedulable {
    private int dimLevel;

    public Light(String id) {
        super(id);
    }

    @Override
    public void dim(int level) {
        this.dimLevel = level;
        System.out.println(getId() + " dimmed to " + level);
    }

    @Override
    public void schedule(String time) {
        System.out.println(getId() + " scheduled " + time);
    }
}

class Fan extends Device implements Schedulable {
    public Fan(String id) {
        super(id);
    }

    @Override
    public void schedule(String time) {
        System.out.println(getId() + " scheduled " + time);
    }
}

class Plug extends Device implements EnergyMonitored {
    private int energyKwh;

    public Plug(String id, int energyKwh) {
        super(id);
        this.energyKwh = energyKwh;
    }

    @Override
    public int getEnergy() {
        return energyKwh;
    }
}

class SmartHomeSystem {
    private Map<String, Device> devices = new HashMap<>();

    public void registerDevice(Device device) {
        devices.put(device.getId(), device);
    }

    public void processCommand(String command) {
        String[] parts = command.trim().split("\\s+");
        if (parts.length == 0) return;

        String action = parts[0];

        if (action.equals("ON") && parts.length >= 2) {
            String id = parts[1];
            Device d = devices.get(id);
            if (d != null) {
                d.turnOn();
            }
        } else if (action.equals("OFF") && parts.length >= 2) {
            String id = parts[1];
            Device d = devices.get(id);
            if (d != null) {
                d.turnOff();
            }
        } else if (action.equals("DIM") && parts.length >= 3) {
            String id = parts[1];
            int level = Integer.parseInt(parts[2]);
            Device d = devices.get(id);
            if (d instanceof Dimmable) {
                ((Dimmable) d).dim(level);
            } else {
                System.out.println(id + " rejected: DIM unsupported");
            }
        } else if (action.equals("SCHEDULE") && parts.length >= 3) {
            String id = parts[1];
            String time = parts[2];
            Device d = devices.get(id);
            if (d instanceof Schedulable) {
                ((Schedulable) d).schedule(time);
            } else {
                System.out.println(id + " rejected: SCHEDULE unsupported");
            }
        } else if (action.equals("ENERGY") && parts.length >= 2) {
            String id = parts[1];
            Device d = devices.get(id);
            if (d instanceof EnergyMonitored) {
                System.out.println(id + " energy " + ((EnergyMonitored) d).getEnergy() + " kWh");
            } else {
                System.out.println(id + " rejected: ENERGY unsupported");
            }
        }
    }
}

public class SmartHome {
    public static void main(String[] args) {
        SmartHomeSystem system = new SmartHomeSystem();

        system.registerDevice(new Light("L1"));
        system.registerDevice(new Fan("F1"));
        system.registerDevice(new Plug("P1", 12));

        system.processCommand("ON L1");
        system.processCommand("DIM L1 40");
        system.processCommand("DIM F1 30");
        system.processCommand("SCHEDULE F1 22:00");
        system.processCommand("ENERGY P1");
        system.processCommand("ENERGY L1");
    }
}
