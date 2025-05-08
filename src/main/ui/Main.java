package ui;

// import java.io.FileNotFoundException;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import com.formdev.flatlaf.FlatLightLaf;

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
        try {
        UIManager.setLookAndFeel(new FlatLightLaf());
    } catch (Exception e) {
        System.err.println("Failed to initialize LaF");
    }
        SwingUtilities.invokeLater(JobTrackingGui::new);
    }
}
