package fightGame.view.widgets;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.*;
import javax.swing.*;
import fightGame.model.*;
import fightGame.view.InterfaceSetting;
import gamePlayers.util.Action;
import gamePlayers.util.ListenableModel;
import gamePlayers.util.ModelListener;
import java.util.List;

/**
 * The ActionView class represents a graphical interface for selecting and submitting actions 
 * during the game. It displays a list of available actions for the player and allows the 
 * player to input the action number to execute.
 */
public class ActionView extends JFrame implements ActionListener, ListenableModel, FocusListener {
    private List<Action> actionTypes; // List of available actions
    private JTextField input; // Input field for entering action number
    private GameButton submiButton; // Button to submit the chosen action
    private int actionNumber; // Number of the chosen action
    private FightGameAction action; // Selected action
    protected List<ModelListener> listeners; // List of model listeners

    /**
     * Constructs the ActionView for a specific player.
     * 
     * @param parent The parent JFrame from which this view is launched.
     * @param proxy  The GameBoardProxy providing access to available actions.
     */
    public ActionView(JFrame parent, GameBoardProxy proxy) {
        this.listeners = new ArrayList<>();
        this.actionTypes = proxy.getActions();
        buildView(proxy);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(parent);
        this.setVisible(true);
    }

    /**
     * Builds and initializes the graphical components of the ActionView.
     * 
     * @param proxy The GameBoardProxy providing player and action information.
     */
    private void buildView(GameBoardProxy proxy) {
        JPanel contentPanel = new JPanel();
        ScrollPane scrollPane = new ScrollPane();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        
        submiButton = new GameButton("Submit", 80, 40);
        submiButton.setFont(InterfaceSetting.TEXT_FONT);
        submiButton.addActionListener(this);

        input = new JTextField("Input your action number");
        input.setMinimumSize(new Dimension(600, 90));
        input.addFocusListener(this);

        JPanel inputJPanel = new JPanel();
        inputJPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 50, 20));
        inputJPanel.add(input);
        inputJPanel.add(submiButton);
        
        for (int i = 0; i < actionTypes.size(); i++) {
            JPanel panel = new JPanel();
            panel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
            panel.setMinimumSize(new Dimension(100, 400));
            JLabel label = new JLabel(i + " : " + actionTypes.get(i).screenDisplay());
            label.setFont(InterfaceSetting.TEXT_FONT);
            panel.add(label);
            contentPanel.add(panel);
        }
        JLabel playerNameLabel = new JLabel("Player : " + proxy.getPlayer().getName());
        playerNameLabel.setForeground(Color.RED);
        this.setMinimumSize(new Dimension(400, 500));
        scrollPane.add(contentPanel);
        this.getContentPane().setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));
        this.getContentPane().add(inputJPanel);
        this.getContentPane().add(playerNameLabel);
        this.getContentPane().add(scrollPane);
        this.revalidate(); 
        this.repaint();
    }


    /**
     * Handles button click events for submitting the chosen action.
     * 
     * @param e The action event triggered by user interaction.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
       if (e.getSource().equals(this.submiButton)) {
            getActionNumber();
       }
    }

    /**
     * Retrieves and validates the action number input by the player.
     */
    private void getActionNumber() {
        String chaine = this.input.getText();
        if (isInteger(chaine)) {
            this.actionNumber = Integer.valueOf(chaine);
            if(this.actionNumber>=0 && this.actionNumber < this.actionTypes.size()){
                this.action = (FightGameAction)this.actionTypes.get(actionNumber);
                this.dispose();
                notifyModelListeners();
            }else{
                new InfosView(this, "Invalid input", "Invalid action number", false);
            }
            
        }
    }

    /**
     * Gets the selected action after the player inputs the action number.
     * 
     * @return The selected FightGameAction.
     */
    public FightGameAction getAction() {
        return this.action;
    }

    /**
     * Checks if a given string is a valid integer.
     * 
     * @param str The string to validate.
     * @return True if the string is an integer; false otherwise.
     */
    public static boolean isInteger(String str) {
        Scanner scanner = new Scanner(str);
        return scanner.hasNextInt(); 
    }

    /**
     * Adds a listener to be notified of changes in the model.
     * 
     * @param modelListener The listener to add.
     */
    @Override
    public void addModelListener(ModelListener modelListener) {
        this.listeners.add(modelListener);
    }
   
    /**
     * Removes a listener from being notified of changes in the model.
     * 
     * @param modelListener The listener to remove.
     */
    @Override
    public void removeModelListener(ModelListener modelListener) {
        this.listeners.remove(modelListener);
    }

    /**
     * Notifies all registered listeners of changes in the model.
     */
    @Override
    public void notifyModelListeners() {
        for (ModelListener modelListener : this.listeners) {
            modelListener.update(this);
        }
    }

    @Override
    public void focusGained(FocusEvent e) {
        if(e.getSource().equals(this.input)){
            this.input.setText(null);
        }
    }

    @Override
    public void focusLost(FocusEvent e) {
       
    }
}
