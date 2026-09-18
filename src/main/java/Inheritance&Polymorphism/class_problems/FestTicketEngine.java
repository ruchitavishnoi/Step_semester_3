public class FestTicketEngine {

    public static class EventTicket {
        private static int ticketsIssued = 0;
        public final String ticketId;
        private double basePrice;
        private double paidAmount;

        public EventTicket(double basePrice) {
            ticketsIssued++;
            this.ticketId = "TCK-" + (1000 + ticketsIssued);
            this.basePrice = basePrice;
            this.paidAmount = 0.0;
        }

        public static int getTicketsIssued() {
            return ticketsIssued;
        }

        public double getBalanceDue() {
            return basePrice - paidAmount;
        }

        public void pay(double amount) {
            this.paidAmount += amount;
        }

        public void pay(double amount, String mode) {
            pay(amount);
        }

        public static boolean isValidPromoCode(String code) {
            if (code == null || code.length() != 5) {
                return false;
            }
            if (code.charAt(0) != 'F') {
                return false;
            }
            if (!Character.isDigit(code.charAt(1)) || 
                !Character.isDigit(code.charAt(2)) || 
                !Character.isDigit(code.charAt(3))) {
                return false;
            }
            return Character.isUpperCase(code.charAt(4));
        }

        public static String processNightlySettlement(EventTicket[] tickets) {
            int processed = 0;
            int nullSkipped = 0;
            int groupCount = 0;
            int individualCount = 0;

            if (tickets != null) {
                for (EventTicket ticket : tickets) {
                    if (ticket == null) {
                        nullSkipped++;
                    } else {
                        processed++;
                        if (ticket instanceof GroupTicket) {
                            groupCount++;
                        } else {
                            individualCount++;
                        }
                    }
                }
            }

            return processed + " processed | " + nullSkipped + " null skipped | " + 
                   groupCount + " group | " + individualCount + " individual";
        }
    }

    public static class GroupTicket extends EventTicket {
        private int groupSize;

        public GroupTicket(double basePrice, int groupSize) {
            super(basePrice);
            this.groupSize = groupSize;
        }

        public int getGroupSize() {
            return groupSize;
        }
    }

    public static void main(String[] args) {
        EventTicket t1 = new EventTicket(500);
        System.out.println(t1.ticketId);
        System.out.println(EventTicket.getTicketsIssued());

        System.out.println(EventTicket.isValidPromoCode("F123A"));
        System.out.println(EventTicket.isValidPromoCode("F12A"));
        System.out.println(EventTicket.isValidPromoCode("X123A"));

        t1.pay(200);
        t1.pay(200, "UPI");
        System.out.println(t1.getBalanceDue());

        String settlement = EventTicket.processNightlySettlement(new EventTicket[]{
            new GroupTicket(2000, 5),
            null,
            new EventTicket(500)
        });
        System.out.println(settlement);
    }
}
