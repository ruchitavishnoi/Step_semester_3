import java.util.List;

class BookRecord {
    private String isbn;
    private String title;

    public BookRecord(String isbn, String title) {
        this.isbn = isbn;
        this.title = title;
    }

    public String getIsbn() {
        return isbn;
    }

    public String getTitle() {
        return title;
    }
}

public class LibraryLookup
  {
    public static String findBook(List<BookRecord> catalog, String targetIsbn) {
        int low = 0;
        int high = catalog.size() - 1;

        while (low <= high) {
            int mid = low + (high - low) / 2;
            BookRecord midBook = catalog.get(mid);
            int cmp = midBook.getIsbn().compareTo(targetIsbn);

            if (cmp == 0) {
                return midBook.getTitle();
            } else if (cmp < 0) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }
        return "Not Found";
    }

    public static void main(String[] args) {
        List<BookRecord> catalog = List.of(
            new BookRecord("0001112223", "Introduction to Algebra"),
            new BookRecord("0002223334", "Beginning Python"),
            new BookRecord("0003334445", "Classic Mythology"),
            new BookRecord("0004445556", "Data and Society"),
            new BookRecord("0005556667", "European History")
        );

        System.out.println(findBook(catalog, "0003334445"));
        System.out.println(findBook(catalog, "0009998887"));
    }
}
