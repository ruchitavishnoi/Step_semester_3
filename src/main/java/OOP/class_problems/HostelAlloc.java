import java.util.Scanner;

class HostelRoom {
    String roomNo;
    int beds;
    int occupied;

    public HostelRoom(String roomNo, int beds, int occupied) {
        this.roomNo = roomNo;
        this.beds = beds;
        this.occupied = occupied;
    }

    public void allot(String name) {
        if (occupied < beds) {
            occupied++;
            System.out.println(name + " allotted to room " + roomNo);
        }
    }
}

public class HostelAlloc {

    public static HostelRoom findAvailableRoom(HostelRoom[] rooms) {
        if (rooms == null) return null;
        for (HostelRoom room : rooms) {
            if (room != null && room.occupied < room.beds) {
                return room;
            }
        }
        return null;
    }

    public static void safeAllot(HostelRoom[] rooms, String studentName) {
        HostelRoom room = findAvailableRoom(rooms);
        if (room != null) {
            room.allot(studentName);
        } else {
            System.out.println("No rooms available for " + studentName);
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter number of rooms: ");
        int n = sc.nextInt();

        HostelRoom[] rooms = new HostelRoom[n];

        for (int i = 0; i < n; i++) {
            System.out.print("Enter room number, total beds, and occupied beds for room " + (i + 1) + ": ");
            String roomNo = sc.next();
            int beds = sc.nextInt();
            int occupied = sc.nextInt();
            rooms[i] = new HostelRoom(roomNo, beds, occupied);
        }

        System.out.print("Enter student name to allot: ");
        String studentName = sc.next();

        safeAllot(rooms, studentName);

        sc.close();
    }
}
