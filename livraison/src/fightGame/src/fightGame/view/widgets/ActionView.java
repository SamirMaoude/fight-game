package fightGame.view.widgets;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.ScrollPane;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import javax.swing.BoxLayout;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import fightGame.model.FightGameAction;
import fightGame.model.FightGameActionType;
import fightGame.view.InterfaceSetting;
import gamePlayers.util.ListenableModel;
import gamePlayers.util.ModelListener;

public class ActionView extends JFrame implements ActionListener, ListenableModel{
    private ArrayList<FightGameActionType> actionTypes = new ArrayList<>(Arrays.asList(FightGameActionType.values()));
    private TextField input;
    private GameButton submiButton;
    private int actionNumber;
    private FightGameAction action;
    protected List<ModelListener> listeners;


    public ActionView(JFrame parent) {
        this.listeners = new ArrayList<>();
        buildView();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(parent);
        this.setVisible(true);
    }

    private void buildView() {
        JPanel contentPanel = new JPanel();
        ScrollPane scrollPane = new ScrollPane();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        
        submiButton = new GameButton("Submit", 80, 40);
        submiButton.setFont(InterfaceSetting.TEXT_FONT);
        submiButton.addActionListener(this);

        input = new TextField("                           ");
        input.setMinimumSize(new Dimension(400, 90));

        JPanel inputJPanel = new JPanel();
        inputJPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 50, 20));
        inputJPanel.add(input);
        inputJPanel.add(submiButton);
        

        for (int i = 0; i < actionTypes.size(); i++) {
            JPanel panel = new JPanel();
            panel.setMinimumSize(new Dimension(100, 400));
            JLabel label = new JLabel(i + " : " + actionTypes.get(i).toString());
            label.setFont(InterfaceSetting.TEXT_FONT);
            panel.add(label);
            contentPanel.add(panel);
        }
        
        this.setMinimumSize(new Dimension(400, 500));
        scrollPane.add(contentPanel);
        this.getContentPane().setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));
        this.getContentPane().add(inputJPanel);
        this.getContentPane().add(scrollPane);
        this.revalidate(); 
        this.repaint();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
       if(e.getSource().equals(this.submiButton)){
            getActionNumber();
       }
    }

    private void getActionNumber(){
        String chaine = this.input.getText();
        if(isInteger(chaine)){
            this.actionNumber = Integer.valueOf(chaine);
            this.action = new FightGameAction(actionTypes.get(actionNumber));
            this.dispose();
            notifyModelListeners();
        }
    }

    public FightGameAction getAction(){
        return this.action;
    }

    public static boolean isInteger(String str) {
        Scanner scanner = new Scanner(str);
        return scanner.hasNextInt(); 
    }

   
    @Override
    public void addModelListener(ModelListener modelListener) {
        this.listeners.add(modelListener);
    }
   
    @Override
    public void removeModelListener(ModelListener modelListener) {
        this.listeners.remove(modelListener);
    }

    @Override
    public void notifyModelListeners() {
        for (ModelListener modelListener : this.listeners) {
            modelListener.update(this);
        }
    }
}
