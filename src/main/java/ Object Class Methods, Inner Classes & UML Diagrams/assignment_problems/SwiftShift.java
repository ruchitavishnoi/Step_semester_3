import java.util.ArrayList;
import java.util.List;

enum ParcelStatus {
    BOOKED,
    PICKED_UP,
    IN_TRANSIT,
    OUT_FOR_DELIVERY,
    DELIVERED,
    CANCELLED;

    public ParcelStatus getNextStatus() {
        switch (this) {
            case BOOKED: return PICKED_UP;
            case PICKED_UP: return IN_TRANSIT;
            case IN_TRANSIT: return OUT_FOR_DELIVERY;
            case OUT_FOR_DELIVERY: return DELIVERED;
            default: return null;
        }
    }
}

interface ShippingType {
    String getName();
    double calculateCharge(double weightKg);
}

class StandardShipping implements ShippingType {
    @Override
    public String getName() {
        return "Standard";
    }

    @Override
    public double calculateCharge(double weightKg) {
        return 40.0 + (10.0 * weightKg);
    }
}

class ExpressShipping implements ShippingType {
    @Override
    public String getName() {
        return "Express";
    }

    @Override
    public double calculateCharge(double weightKg) {
        return 80.0 + (15.0 * weightKg);
    }
}

class FragileShipping implements ShippingType {
    private StandardShipping baseShipping = new StandardShipping();

    @Override
    public String getName() {
        return "Fragile";
    }

    @Override
    public double calculateCharge(double weightKg) {
        return baseShipping.calculateCharge(weightKg) + 50.0;
    }
}

interface NotificationChannel {
    void notify(String parcelId, ParcelStatus status);
}

class SmsChannel implements NotificationChannel {
    @Override
    public void notify(String parcelId, ParcelStatus status) {
        System.out.print("[SMS] " + parcelId + " is now " + status + ". ");
    }
}

class EmailChannel implements NotificationChannel {
    @Override
    public void notify(String parcelId, ParcelStatus status) {
        System.out.print("[Email] " + parcelId + " is now " + status + ". ");
    }
}

class Customer {
    private String name;

    public Customer(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

class Parcel {
    private String parcelId;
    private double weightKg;
    private ShippingType shippingType;
    private ParcelStatus status;
    private List<NotificationChannel> channels;

    public Parcel(String parcelId, double weightKg, ShippingType shippingType) {
        this.parcelId = parcelId;
        this.weightKg = weightKg;
        this.shippingType = shippingType;
        this.status = ParcelStatus.BOOKED;
        this.channels = new ArrayList<>();
    }

    public String getParcelId() {
        return parcelId;
    }

    public double getWeightKg() {
        return weightKg;
    }

    public ShippingType getShippingType() {
        return shippingType;
    }

    public ParcelStatus getStatus() {
        return status;
    }

    public double getCharge() {
        return shippingType.calculateCharge(weightKg);
    }

    public void subscribe(NotificationChannel channel) {
        channels.add(channel);
    }

    public void notifyChannels() {
        for (NotificationChannel channel : channels) {
            channel.notify(parcelId, status);
        }
    }

    public boolean updateStatus(ParcelStatus nextStatus) {
        if (status == ParcelStatus.CANCELLED) {
            System.out.println("Cannot update status: Parcel " + parcelId + " is CANCELLED.");
            return false;
        }
        ParcelStatus expectedNext = status.getNextStatus();
        if (expectedNext == nextStatus) {
            this.status = nextStatus;
            notifyChannels();
            System.out.println();
            return true;
        } else {
            System.out.println("Invalid transition: " + status + " -> " + nextStatus + " is not allowed.");
            return false;
        }
    }

    public boolean cancel() {
        if (status == ParcelStatus.BOOKED) {
            this.status = ParcelStatus.CANCELLED;
            System.out.println("Parcel " + parcelId + " cancelled successfully.");
            return true;
        } else {
            System.out.println("Cancellation failed: " + parcelId + " can be cancelled only while BOOKED.");
            return false;
        }
    }
}

class ParcelService {
    public Parcel bookParcel(String parcelId, double weightKg, ShippingType shippingType, List<NotificationChannel> channels) {
        Parcel parcel = new Parcel(parcelId, weightKg, shippingType);
        for (NotificationChannel channel : channels) {
            parcel.subscribe(channel);
        }
        System.out.printf("Parcel %s booked (%s, %.0f kg). Charge: ₹%.2f. ",
                parcelId, shippingType.getName(), weightKg, parcel.getCharge());
        parcel.notifyChannels();
        System.out.println();
        return parcel;
    }
}

public class SwiftShift {
    public static void main(String[] args) {
        ParcelService service = new ParcelService();

        List<NotificationChannel> channels = new ArrayList<>();
        channels.add(new SmsChannel());
        channels.add(new EmailChannel());

        Parcel p101 = service.bookParcel("P101", 2.0, new ExpressShipping(), channels);

        p101.updateStatus(ParcelStatus.PICKED_UP);

        p101.cancel();

        p101.updateStatus(ParcelStatus.IN_TRANSIT);

        p101.updateStatus(ParcelStatus.DELIVERED);
    }
}
