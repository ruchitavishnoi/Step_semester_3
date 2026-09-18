class AccessRuleEngine {

    public static String classifyAccess(String fieldModifier, String accessorContext) {
        if ("private".equals(fieldModifier)) {
            return "SAME_CLASS".equals(accessorContext) ? "ALLOWED" : "DENIED";
        }
        if ("default".equals(fieldModifier) || "protected".equals(fieldModifier)) {
            return ("SAME_CLASS".equals(accessorContext) || "SAME_PACKAGE".equals(accessorContext)) ? "ALLOWED" : "DENIED";
        }
        if ("public".equals(fieldModifier)) {
            return "ALLOWED";
        }
        return "DENIED";
    }

    public static String summarizeBatch(String[][] attempts) {
        int allowed = 0;
        int denied = 0;

        if (attempts != null) {
            for (String[] attempt : attempts) {
                if (attempt != null && attempt.length >= 2) {
                    if ("ALLOWED".equals(classifyAccess(attempt[0], attempt[1]))) {
                        allowed++;
                    } else {
                        denied++;
                    }
                }
            }
        }

        return "Allowed: " + allowed + " | Denied: " + denied;
    }
}

class PatientRecord {
    private String patientId;
    private String wardCode;
    private double vitalsScore;
    private String facilityName;

    public PatientRecord(String patientId, String wardCode, double vitalsScore, String facilityName) {
        if (patientId == null || patientId.trim().length() < 4) {
            throw new IllegalArgumentException("construction rejected");
        }
        this.patientId = patientId;
        this.wardCode = wardCode;
        this.vitalsScore = vitalsScore;
        this.facilityName = facilityName;
    }

    public String getPatientId() {
        return patientId;
    }
}

public class Main {
    public static void main(String[] args) {
        System.out.println(AccessRuleEngine.classifyAccess("private", "SAME_CLASS"));
        System.out.println(AccessRuleEngine.classifyAccess("default", "DIFFERENT_PACKAGE"));

        String[][] batch = {
            {"protected", "SAME_PACKAGE"},
            {"protected", "DIFFERENT_PACKAGE"},
            {"public", "DIFFERENT_PACKAGE"}
        };
        System.out.println(AccessRuleEngine.summarizeBatch(batch));

        try {
            new PatientRecord("MT9", "W3", 98.2, "MediTrack Central");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }
}
