import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

interface Capability {
    String getName();
    boolean setValue(Object value, String deviceName);
}

class PowerCapability implements Capability {
    private boolean on = false;

    @Override
    public String getName() {
        return "Power";
    }

    @Override
    public boolean setValue(Object value, String deviceName) {
        if (value instanceof Boolean) {
            this.on = (Boolean) value;
            System.out.print(deviceName + ": " + (on ? "ON" : "OFF") + ". ");
            return true;
        } else if (value instanceof String) {
            String str = ((String) value).toUpperCase();
            if (str.equals("ON") || str.equals("OFF")) {
                this.on = str.equals("ON");
                System.out.print(deviceName + ": " + (on ? "ON" : "OFF") + ". ");
                return true;
            }
        }
        return false;
    }
}

class BrightnessCapability implements Capability {
    private int brightness = 0;

    @Override
    public String getName() {
        return "Brightness";
    }

    @Override
    public boolean setValue(Object value, String deviceName) {
        int level = (Integer) value;
        if (level < 0 || level > 100) {
            System.out.println("Rejected: " + deviceName + " brightness must be between 0% and 100%.");
            return false;
        }
        this.brightness = level;
        System.out.print(deviceName + ": brightness set to " + brightness + "%. ");
        return true;
    }
}

class TemperatureCapability implements Capability {
    private int temperature = 20;

    @Override
    public String getName() {
        return "Temperature";
    }

    @Override
    public boolean setValue(Object value, String deviceName) {
        int temp = (Integer) value;
        if (temp < 16 || temp > 30) {
            System.out.println("Rejected: " + deviceName + " temperature must be between 16°C and 30°C.");
            return false;
        }
        this.temperature = temp;
        System.out.print(deviceName + ": temperature set to " + temperature + "°C. ");
        return true;
    }
}

class Device {
    private String name;
    private Map<String, Capability> capabilities;

    public Device(String name) {
        this.name = name;
        this.capabilities = new LinkedHashMap<>();
    }

    public String getName() {
        return name;
    }

    public void addCapability(Capability capability) {
        capabilities.put(capability.getName(), capability);
    }

    public boolean hasCapability(String capabilityName) {
        return capabilities.containsKey(capabilityName);
    }

    public boolean applyCapability(String capabilityName, Object value) {
        Capability cap = capabilities.get(capabilityName);
        if (cap != null) {
            return cap.setValue(value, name);
        }
        return false;
    }
}

class SceneStep {
    private String capabilityName;
    private Object targetValue;

    public SceneStep(String capabilityName, Object targetValue) {
        this.capabilityName = capabilityName;
        this.targetValue = targetValue;
    }

    public String getCapabilityName() {
        return capabilityName;
    }

    public Object getTargetValue() {
        return targetValue;
    }
}

class Scene {
    private String name;
    private List<SceneStep> steps;

    public Scene(String name) {
        this.name = name;
        this.steps = new ArrayList<>();
    }

    public void addStep(String capabilityName, Object value) {
        steps.add(new SceneStep(capabilityName, value));
    }

    public void execute(List<Device> devices) {
        System.out.print("Scene '" + name + "' started. ");
        int actionsApplied = 0;
        for (SceneStep step : steps) {
            for (Device device : devices) {
                if (device.hasCapability(step.getCapabilityName())) {
                    if (device.applyCapability(step.getCapabilityName(), step.getTargetValue())) {
                        actionsApplied++;
                    }
                }
            }
        }
        System.out.println("Scene '" + name + "' completed: " + actionsApplied + " actions applied.");
    }
}

public class SmartLab {
    public static void main(String[] args) {
        Device labAC = new Device("Lab AC");
        labAC.addCapability(new PowerCapability());
        labAC.addCapability(new TemperatureCapability());

        Device ceilingLights = new Device("Ceiling Lights");
        ceilingLights.addCapability(new PowerCapability());
        ceilingLights.addCapability(new BrightnessCapability());

        Device projector = new Device("Projector");
        projector.addCapability(new PowerCapability());

        List<Device> devices = new ArrayList<>();
        devices.add(labAC);
        devices.add(ceilingLights);
        devices.add(projector);

        Scene lectureMode = new Scene("Lecture Mode");
        lectureMode.addStep("Power", "ON");
        lectureMode.addStep("Brightness", 40);
        lectureMode.addStep("Temperature", 24);

        lectureMode.execute(devices);

        labAC.applyCapability("Temperature", 12);

        projector.addCapability(new BrightnessCapability());
        System.out.println("Projector: Brightness capability added.");

        projector.applyCapability("Brightness", 70);
        System.out.println();
    }
}
