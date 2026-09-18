import java.util.Arrays;

class PatientVitals {
    private double[] readings = new double[500];
    private int size = 0;

    public PatientVitals(double[] initialReadings) {
        if (initialReadings != null) {
            for (double reading : initialReadings) {
                recordReading(reading);
            }
        }
    }

    public void recordReading(double reading) {
        if (reading <= 0 || reading > 45.0) {
            return;
        }
        if (size < readings.length) {
            readings[size++] = reading;
        }
    }

    public double getAverage() {
        if (size == 0) {
            return 0.0;
        }
        double sum = 0;
        for (int i = 0; i < size; i++) {
            sum += readings[i];
        }
        return sum / size;
    }

    public double[] getAllReadings() {
        return Arrays.copyOf(readings, size);
    }
}

public class Main3 {
    public static void main(String[] args) {
        PatientVitals v = new PatientVitals(new double[]{36.5, -2, 37.1});
        System.out.println(Arrays.toString(v.getAllReadings()));

        double[] copy = v.getAllReadings();
        copy[0] = 999;
        System.out.println(v.getAllReadings()[0]);
    }
}
