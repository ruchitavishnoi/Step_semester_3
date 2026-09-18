class DischargeSummary {
    private final String patientId;
    private final String[] medicationCodes;

    public DischargeSummary(String patientId, String[] medicationCodes) {
        if (medicationCodes == null) {
            throw new IllegalArgumentException("construction rejected");
        }
        this.patientId = patientId;
        this.medicationCodes = medicationCodes.clone();
        for (String code : this.medicationCodes) {
            if (!isValidCode(code)) {
                throw new IllegalArgumentException("construction rejected");
            }
        }
    }

    private boolean isValidCode(String code) {
        return code != null && code.length() == 5 && code.startsWith("MED-") && code.charAt(4) >= 'A' && code.charAt(4) <= 'Z';
    }

    public String[] getMedicationCodes() {
        return medicationCodes.clone();
    }

    public String getPatientId() {
        return patientId;
    }

    public DischargeSummary withCorrectedMedication(int index, String newCode) {
        String[] updatedCodes = this.medicationCodes.clone();
        if (index >= 0 && index < updatedCodes.length) {
            updatedCodes[index] = newCode;
        }
        return new DischargeSummary(this.patientId, updatedCodes);
    }

    public static String processNightlyBatch(DischargeSummary[] summaries) {
        int processed = 0, nullSkipped = 0, criticalCare = 0, routine = 0;
        if (summaries != null) {
            for (DischargeSummary summary : summaries) {
                if (summary == null) {
                    nullSkipped++;
                } else {
                    processed++;
                    if (summary instanceof CriticalCareDischargeSummary) {
                        criticalCare++;
                    } else {
                        routine++;
                    }
                }
            }
        }
        return processed + " processed | " + nullSkipped + " null skipped | " + criticalCare + " critical-care | " + routine + " routine";
    }
}

class CriticalCareDischargeSummary extends DischargeSummary {
    private final int icuDays;

    public CriticalCareDischargeSummary(String patientId, String[] medicationCodes, int icuDays) {
        super(patientId, medicationCodes);
        this.icuDays = icuDays;
    }

    public int getIcuDays() {
        return icuDays;
    }
}

public class Main {
    public static void main(String[] args) {
        try {
            new DischargeSummary("MT2026-0142", new String[]{"MED-A", "bad"});
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }

        DischargeSummary d = new DischargeSummary("MT2026-0142", new String[]{"MED-A", "MED-B"});
        String[] codes = d.getMedicationCodes();
        codes[0] = "TAMPERED";
        System.out.println(d.getMedicationCodes()[0]);

        String batchResult = DischargeSummary.processNightlyBatch(new DischargeSummary[]{
            new CriticalCareDischargeSummary("MT001", new String[]{"MED-X"}, 4),
            null,
            new DischargeSummary("MT002", new String[]{"MED-Y"})
        });
        System.out.println(batchResult);
    }
}
