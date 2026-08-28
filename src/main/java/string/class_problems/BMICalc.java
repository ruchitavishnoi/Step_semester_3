public class BMICalc {

    public static String getBmiStatus(double bmi) {
        if (bmi < 18.5) {
            return "Underweight";
        } else if (bmi < 25.0) {
            return "Normal";
        } else if (bmi < 30.0) {
            return "Overweight";
        } else {
            return "Obese";
        }
    }

    public static void printWellnessReport(double[] heights, double[] weights) {
        System.out.printf("%-10s | %-10s | %-11s | %-6s | %-12s\n", 
                "Person", "Height (m)", "Weight (kg)", "BMI", "Status");
        System.out.println("---------------------------------------------------------------");
        
        for (int i = 0; i < heights.length; i++) {
            double bmi = weights[i] / (heights[i] * heights[i]);
            String status = getBmiStatus(bmi);
            
            System.out.printf("Person %-3d | %-10.2f | %-11.2f | %-6.2f | %-12s\n", 
                    (i + 1), heights[i], weights[i], bmi, status);
        }
    }

    public static void main(String[] args) {
        int numPeople = 10;
        
        double[] heights = new double[numPeople];
        double[] weights = new double[numPeople];
        
        for (int i = 0; i < numPeople; i++) {
            heights[i] = 1.5 + (Math.random() * 0.5);
            weights[i] = 50.0 + (Math.random() * 60.0);
        }
        
        printWellnessReport(heights, weights);
    }
}
