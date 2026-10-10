import java.util.ArrayList;
import java.util.List;

interface Reservable {
    void reserve(String memberId);
}

interface Downloadable {
    void download();
}

abstract class Resource {
    private String id;

    public Resource(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}

class Book extends Resource implements Reservable {
    public Book(String id) {
        super(id);
    }

    @Override
    public void reserve(String memberId) {
        System.out.println(getId() + " reserved for " + memberId);
    }
}

class EBook extends Resource implements Reservable, Downloadable {
    public EBook(String id) {
        super(id);
    }

    @Override
    public void reserve(String memberId) {
        System.out.println(getId() + " reserved for " + memberId);
    }

    @Override
    public void download() {
        System.out.println(getId() + " downloaded");
    }
}

class LibraryPlatform {
    private List<Resource> resources = new ArrayList<>();

    private int findInsertIndex(String id) {
        int low = 0;
        int high = resources.size() - 1;
        while (low <= high) {
            int mid = low + (high - low) / 2;
            int cmp = resources.get(mid).getId().compareTo(id);
            if (cmp == 0) {
                return mid;
            } else if (cmp < 0) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }
        return low;
    }

    public int binarySearch(String id) {
        int low = 0;
        int high = resources.size() - 1;
        while (low <= high) {
            int mid = low + (high - low) / 2;
            int cmp = resources.get(mid).getId().compareTo(id);
            if (cmp == 0) {
                return mid;
            } else if (cmp < 0) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }
        return -1;
    }

    public void addResource(String type, String id) {
        int idx = binarySearch(id);
        if (idx != -1) {
            System.out.println("duplicate rejected");
            return;
        }

        Resource resource = null;
        if (type.equalsIgnoreCase("Book")) {
            resource = new Book(id);
        } else if (type.equalsIgnoreCase("EBook")) {
            resource = new EBook(id);
        }

        if (resource != null) {
            int insertPos = findInsertIndex(id);
            resources.add(insertPos, resource);
            System.out.println(id + " added");
        }
    }

    public void reserveResource(String id, String memberId) {
        int idx = binarySearch(id);
        if (idx == -1) {
            System.out.println(id + " Not Found");
            return;
        }
        Resource r = resources.get(idx);
        if (r instanceof Reservable) {
            ((Reservable) r).reserve(memberId);
        } else {
            System.out.println(id + " rejected: reserve unsupported");
        }
    }

    public void downloadResource(String id) {
        int idx = binarySearch(id);
        if (idx == -1) {
            System.out.println(id + " Not Found");
            return;
        }
        Resource r = resources.get(idx);
        if (r instanceof Downloadable) {
            ((Downloadable) r).download();
        } else {
            System.out.println(id + " rejected: download unsupported");
        }
    }

    public void findResource(String id) {
        int idx = binarySearch(id);
        if (idx != -1) {
            System.out.println(id + " found at index " + idx);
        } else {
            System.out.println(id + " Not Found");
        }
    }
}

public class LibraryPlatform {
    public static void main(String[] args) {
        LibraryPlatform platform = new LibraryPlatform();

        platform.addResource("Book", "B1");
        platform.addResource("Book", "B1");
        platform.addResource("EBook", "E1");
        platform.reserveResource("B1", "M1");
        platform.downloadResource("B1");
        platform.downloadResource("E1");
        platform.findResource("E1");
    }
}
