import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PlayerInterface extends JFrame{
    public PlayerInterface(){
        setTitle("Interface Joueurs");
        this.setZize(800,400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
    
        Jpanel toPanel = new JPanel(new BorderLayout());
        JButton closeButton = new JButton("close");
        closeButton.setPreferredSize(new Dimension(100,40));
        toPanel.add(closeButton,BorderLayout.EAST);
        add(toPanel,BorderLayout.NORTH);
    
        JPanel bottomPanel = new JPanel(new BorderLayout());
        JButton nextButton = new JButton("Next");
        JButton saveButton = new JButton("Save");
        nextButton.setPreferredSize(new Dimension(100,40));
        saveButton.setPreferredSize(new Dimension(100,40));
        bottomPanel.add(nextButton,BorderLayout.WEST);
        bottomPanel.add(saveButton,BorderLayout.EAST);
        add(bottomPanel,BorderLayout.SOUTH);
    
        JPanel playerPanel = new JPanel();
        playerPanel.setLayout(new BoxLayout((playerPanel,BoxLayout.Y_AXIS)));
        playerPanel.setBorder(BorderFactory.createTitleBorder("Joueurs"));
    
        for(String playerName : players)
        {
            JPanel playerInfoPanel = new JPanel();
            playerInfoPanel.setLayout(new BoxLayout((playerInfoPanel,BoxLayout.X_AXIS)));
            playerInfoPanel.setBorder(BorderFactory.createLineBOrder(Color.BLACK));
               
    
            JPanel namPanel = new JPanel();
            namPanel.setBorder(BorderFactory.createBorder(Color.BLACK));
            JLabel playerLabel = new JLabel(playerName);
            namPanel.add(playerLabel);
            namPanel.setPreferredSize(new Dimension(100,40) );
    
            JPanel energyPanel = new JPanel();
            energyPanel.setBorder(BorderFactory.createLineBorder((Color.BLACK)));
            JLabel energyLabel = new JLabel()("Niveau d'energie");
            energyPanel.add(energyLabel);
            energyPanel.setPreferredSize(new Dimension(150,40));
            playerInfoPanel.add(namPanel);
            playerInfoPanel.add(Box.createHorizontalStrut(10));
            playerInfoPanel.add(energyPanel);
    
            player.add(playerInfoPanel);
        }
    
        JScrollPane playerScrollPane = new JScrollPane(playerPanel);
        playerScrollPane.setPreferredSize(new Dimension(300,0));
        playerScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        add(playerScrollPane,BorderLayout.WEST);
    
        JTable emptyTable = new JTable(0,0);
        emptyTable.setShowGrid(false);
        emptyTable.getTableHeader(null);
        emptyTable.setFillsViewportHeight(true);
    
        JScrollPane tableScrollPane = new JScrollPane(emptyTable);
        add(tableScrollPane,BorderLayout.CENTER);
    
        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                System.out.println("Prochain Tour");
            }
            
        });
    
    
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                System.out.println("Etat sauvergardé");
            }
            
        });
    
    
        closeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
               dispose();
            }
            
        });
     }
    
     public static void main(String[] args){
        SwingUtilities.invokeLater(new Runnable()){
            @Override
            public void run(){
                new PlayerInterface.setVisible(true);
            }
        }
     
    }
}

    


