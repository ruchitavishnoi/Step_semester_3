class DischargeSummaryV3 {
    private final String patientId;
    private final String[] medicationCodes;

    public DischargeSummaryV3(String patientId, String[] medicationCodes) {
        if (patientId == null || medicationCodes == null) {
            throw new IllegalArgumentException("construction rejected");
        }
        for (String code : medicationCodes) {
            if (!isValidCode(code)) {
                throw new IllegalArgumentException("construction rejected");
            }
        }
        this.patientId = patientId;
        this.medicationCodes = medicationCodes.clone();
    }

    private static boolean isValidCode(String code) {
        return code != null 
            && code.length() == 5 
            && code.startsWith("MED-") 
            && code.charAt(4) >= 'A' 
            && code.charAt(4) <= 'Z';
    }

    public String getPatientId() {
        return patientId;
    }

    public String[] getMedicationCodes() {
        return medicationCodes.clone();
    }

    public DischargeSummaryV3 withCorrectedMedication(int index, String newCode) {
        if (index < 0 || index >= this.medicationCodes.length) {
            throw new IllegalArgumentException("Invalid index");
        }
        String[] updatedCodes = this.medicationCodes.clone();
        updatedCodes[index] = newCode;
        return new DischargeSummaryV3(this.patientId, updatedCodes);
    }

    public static String processNightlyBatch(DischargeSummaryV3[] summaries) {
        int processed = 0, nullSkipped = 0, criticalCare = 0, routine = 0;
        if (summaries != null) {
            for (DischargeSummaryV3 summary : summaries) {
                if (summary == null) {
                    nullSkipped++;
                    continue;
                }
                processed++;
                if (summary instanceof CriticalCareDischargeSummaryV3) {
                    criticalCare++;
                } else {
                    routine++;
                }
            }
        }
        return String.format("%d processed | %d null skipped | %d critical-care | %d routine",
                processed, nullSkipped, criticalCare, routine);
    }
}

class CriticalCareDischargeSummaryV3 extends DischargeSummaryV3 {
    private final int icuDays;

    public CriticalCareDischargeSummaryV3(String patientId, String[] medicationCodes, int icuDays) {
        super(patientId, medicationCodes);
        this.icuDays = icuDays;
    }

    public int getIcuDays() {
        return icuDays;
    }

    @Override
    public DischargeSummaryV3 withCorrectedMedication(int index, String newCode) {
        String[] updatedCodes = getMedicationCodes();
        updatedCodes[index] = newCode;
        return new CriticalCareDischargeSummaryV3(getPatientId(), updatedCodes, this.icuDays);
    }
}

public class Main6 {
    public static void main(String[] args) {
        try {
            new DischargeSummaryV3("MT2026-0142", new String[]{"MED-A", "bad"});
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }

        DischargeSummaryV3 d = new DischargeSummaryV3("MT2026-0142", new String[]{"MED-A", "MED-B"});
        String[] codes = d.getMedicationCodes();
        codes[0] = "TAMPERED";
        System.out.println(d.getMedicationCodes()[0]);

        DischargeSummaryV3[] batch = {
            new CriticalCareDischargeSummaryV3("MT001", new String[]{"MED-X"}, 4),
            null,
            new DischargeSummaryV3("MT002", new String[]{"MED-Y"})
        };
        System.out.println(DischargeSummaryV3.processNightlyBatch(batch));
    }
}
