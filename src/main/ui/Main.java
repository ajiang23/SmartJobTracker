package ui;

// import java.io.FileNotFoundException;
import javax.swing.SwingUtilities;

// public class Main {
//     public static void main(String[] args) {
//         try {
//             new JobTrackingApp();
//         } catch (FileNotFoundException e) {
//             System.out.println("Unable to run app: file not found.");
//         }
//     }
// }


public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(JobTrackingGui::new); // ✅ Launch GUI
    }
}
