import java.util.Scanner;

public class FileExtension {

    public static String validateFileExtension(String filename) {
        if (filename == null) {
            return "Rejected — invalid file type";
        }

        int lastDotIndex = filename.lastIndexOf('.');
        if (lastDotIndex == -1 || lastDotIndex == filename.length() - 1) {
            return "Rejected — invalid file type";
        }

        String extension = filename.substring(lastDotIndex + 1).toLowerCase();

        if (extension.equals("pdf") || extension.equals("docx") || extension.equals("zip")) {
            return "Accepted";
        } else {
            return "Rejected — invalid file type";
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter filename: ");
        String filename = sc.nextLine();

        String result = validateFileExtension(filename);
        System.out.println(result);

        sc.close();
    }
}
