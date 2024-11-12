import javax.swing.*;
import java.awt.*;
import java.util.concurrent.TimeUnit;

public class ProgressBarDemo {
    public static void main(String[] args) {
        // Création de la fenêtre
        JFrame frame = new JFrame("Exemple de JProgressBar");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 150);

        // Création d'une barre de progression
        JProgressBar progressBar = new JProgressBar(0, 100); // Min 0, Max 100
        progressBar.setValue(0);
        // Affiche le pourcentage de progression

        // Ajout de la barre de progression à la fenêtre
        frame.setLayout(new BorderLayout());
        frame.add(progressBar, BorderLayout.CENTER);

        // Création d'une tâche en arrière-plan avec SwingWorker
        SwingWorker<Void, Void> worker = new SwingWorker<>() {
            @Override
            protected Void doInBackground() throws Exception {
                // Simulation d'une tâche qui prend du temps (par exemple 10 secondes)
                for (int i = 0; i <= 100; i++) {
                    TimeUnit.MILLISECONDS.sleep(100); // Pause pour simuler le temps de traitement
                    progressBar.setValue(i); // Met à jour la barre de progression
                }
                return null;
            }

            @Override
            protected void done() {
                JOptionPane.showMessageDialog(frame, "Tâche terminée!");
            }
        };

        // Lancement de la tâche
        worker.execute();

        // Affichage de la fenêtre
        frame.setVisible(true);
    }
}
