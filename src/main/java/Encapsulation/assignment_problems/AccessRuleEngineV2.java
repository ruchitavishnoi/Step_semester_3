class AccessRuleEngineV2 {

    public static String classifyAccess(String fieldModifier, String accessorContext) {
        if ("public".equals(fieldModifier)) {
            return "ALLOWED";
        }

        if ("private".equals(fieldModifier)) {
            return "SAME_CLASS".equals(accessorContext) ? "ALLOWED" : "DENIED";
        }

        if ("default".equals(fieldModifier)) {
            return ("SAME_CLASS".equals(accessorContext) || "SAME_PACKAGE".equals(accessorContext)) ? "ALLOWED" : "DENIED";
        }

        if ("protected".equals(fieldModifier)) {
            if ("SAME_CLASS".equals(accessorContext) || 
                "SAME_PACKAGE".equals(accessorContext) || 
                "SUBCLASS_DIFFERENT_PACKAGE_OWN_TYPE".equals(accessorContext)) {
                return "ALLOWED";
            }
            return "DENIED";
        }

        return "DENIED";
    }

    public static String describeContext(String accessorContext) {
        if (accessorContext == null || accessorContext.isEmpty()) {
            return "";
        }

        String[] parts = accessorContext.split("_");
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < parts.length; i++) {
            if (!parts[i].isEmpty()) {
                String word = parts[i].substring(0, 1).toUpperCase() + parts[i].substring(1).toLowerCase();
                result.append(word);
                if (i < parts.length - 1) {
                    result.append(" ");
                }
            }
        }

        return result.toString();
    }
}

public class Main2 {
    public static void main(String[] args) {
        System.out.println(AccessRuleEngineV2.classifyAccess("protected", "SUBCLASS_DIFFERENT_PACKAGE_OWN_TYPE"));
        System.out.println(AccessRuleEngineV2.classifyAccess("protected", "SUBCLASS_DIFFERENT_PACKAGE_PARENT_TYPE"));
        System.out.println(AccessRuleEngineV2.describeContext("SUBCLASS_DIFFERENT_PACKAGE_PARENT_TYPE"));
    }
}
