package com.Minesweeper.Engine;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class GUI extends JComponent implements MouseListener {
    private JFrame mainFrame = new JFrame("Minesweeper");
    private JPanel Field = new JPanel();
    private JPanel Score = new JPanel(new FlowLayout(FlowLayout.CENTER, 50, 0));
    private JButton[][] Buttonfield = new JButton[10][10];
    private JButton RestartButton = new JButton("Restart");
    private JLabel GameTimerLabel = new JLabel("0");
    private Items field = new Items();
    private Timer Timer = new Timer(1000, new TimerListener());
    private int NumOfFlags;
    private Icon EmptyIcon = new ImageIcon(getClass().getResource("/com/Minesweeper/Resource/EmptyItem.png"));
    private Icon Flag = new ImageIcon(getClass().getResource("/com/Minesweeper/Resource/Flag.png"));
    private Icon Bomb = new ImageIcon(getClass().getResource("/com/Minesweeper/Resource/Bomb.png"));
    private Icon NoBomb = new ImageIcon(getClass().getResource("/com/Minesweeper/Resource/NoBomb.png"));
    private Icon One = new ImageIcon(getClass().getResource("/com/Minesweeper/Resource/One.png"));
    private Icon Two = new ImageIcon(getClass().getResource("/com/Minesweeper/Resource/Two.png"));
    private Icon Three = new ImageIcon(getClass().getResource("/com/Minesweeper/Resource/Three.png"));
    private Icon Four = new ImageIcon(getClass().getResource("/com/Minesweeper/Resource/Four.png"));
    private Icon Five = new ImageIcon(getClass().getResource("/com/Minesweeper/Resource/Five.png"));
    private Icon Six = new ImageIcon(getClass().getResource("/com/Minesweeper/Resource/Six.png"));
    private Icon Seven = new ImageIcon(getClass().getResource("/com/Minesweeper/Resource/Seven.png"));
    private Icon Eight = new ImageIcon(getClass().getResource("/com/Minesweeper/Resource/Eight.png"));


    public void GUIGenerator() {        //создание игрового поля
        mainFrame.setSize(600, 600);
        mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        mainFrame.getContentPane().add(BorderLayout.NORTH, Score);
        mainFrame.getContentPane().add(BorderLayout.CENTER, Field);
        field.ItemGenerator();
        field.BombSet();
        GameplayFrame();
        ScorePanelSetter();
        Timer.start();
        mainFrame.setVisible(true);
        mainFrame.pack();
    }

    private void ScorePanelSetter(){
        JLabel Label1 = new JLabel("Timer:");
        Score.setBackground(new Color(255, 201, 14));
        Label1.setForeground(new Color(255, 127, 39));
        Label1.setFont(new Font("Impact",Font.PLAIN,14 ));
        GameTimerLabel.setForeground(new Color(255, 127, 39));
        RestartButton.setForeground(new Color(255, 127, 39));
        RestartButton.setFont(new Font("Impact",Font.PLAIN,14 ));
        GameTimerLabel.setFont(new Font("Impact",Font.PLAIN,14 ));;
        RestartButton.addActionListener(new RestartButtonListener());
        Score.add(Label1);
        Score.add(GameTimerLabel);
        Score.add(RestartButton);
        Score.setBorder(BorderFactory.createDashedBorder(new Color(255, 127, 39), 10, 10, 0, false));
    }

    class TimerListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            int TextField = Integer.parseInt(GameTimerLabel.getText())+1;
            GameTimerLabel.setText(String.valueOf(TextField));
        }
    }

    class RestartButtonListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            Field.removeAll();
            repaint();
            field.BombSet();
            GameTimerLabel.setText("0");
            Timer.restart();
            GameplayFrame();
        }
    }



    private void GameplayFrame(){       //создание кнопок
        Field.setLayout(new GridLayout(10, 10));
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                Buttonfield[i][j]  = new JButton();
                    Buttonfield[i][j].setBorder(BorderFactory.createEmptyBorder());
                    Buttonfield[i][j].setContentAreaFilled(false);
                    Buttonfield[i][j].setIcon(EmptyIcon);
                    Buttonfield[i][j].addMouseListener(this);
                Field.add(Buttonfield[i][j]);
            }
        }
    }


    @Override
    public void mouseClicked(MouseEvent e) {        //обработка событий нажатия каждой кнопки
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (e.getSource() == Buttonfield[i][j]) {
                    field.GotCheckedSetter(i, j);
                    if (e.getButton() == MouseEvent.BUTTON3){
                        if (!field.FlagGetter(i, j)) {
                            Buttonfield[i][j].setIcon(Flag);
                            field.FlagSetter(i, j, true);
                            NumOfFlags++;
                            if(NumOfFlags==20)
                                Timer.stop();
                        } else {
                            Buttonfield[i][j].setIcon(EmptyIcon);
                            field.FlagSetter(i, j, false);
                            NumOfFlags--;
                        }
                    }
                    else if (field.ItemsBombGetter(i, j) && !field.FlagGetter(i, j)){
                        Buttonfield[i][j].setIcon(Bomb);

                        for (int ii = 0; ii < 10; ii++) {
                            for (int jj = 0; jj < 10; jj++) {
                                if(!field.GotCheckedGetter(ii, jj) || field.ItemsBombGetter(ii, jj) != field.FlagGetter(ii, jj))
                                    IconSwitch(ii, jj);
                            }
                        }
                        Timer.stop();
                        break;
                    }
                    else if(field.ItemsNumberGetter(i, j) == 0){
                        Buttonfield[i][j].setIcon(NoBomb);
                        OpenClosedItems(i, j);
                    }
                    else if(!field.FlagGetter(i, j))
                        IconSwitch(i, j);

                    break;
                }
            }
        }
    }

    private void IconSwitch(int i, int j ){
        if(field.ItemsBombGetter(i, j))
            Buttonfield[i][j].setIcon(Bomb);
        else
            switch (field.ItemsNumberGetter(i, j)) {
            case 0:
                Buttonfield[i][j].setIcon(NoBomb);
                break;

            case 1:
                Buttonfield[i][j].setIcon(One);
                break;

            case 2:
                Buttonfield[i][j].setIcon(Two);
                break;

            case 3:
                Buttonfield[i][j].setIcon(Three);
                break;

            case 4:
                Buttonfield[i][j].setIcon(Four);
                break;
            case 5:
                Buttonfield[i][j].setIcon(Five);
                break;
            case 6:
                Buttonfield[i][j].setIcon(Six);
                break;
            case 7:
                Buttonfield[i][j].setIcon(Seven);
                break;
            case 8:
                Buttonfield[i][j].setIcon(Eight);
                break;
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    private void OpenClosedItems(int X,int Y ) {        //чуть не родил, пока это делал. Эта штука
        if (X == 0 && Y == 0) {                         // освобождает сосдение клетки, если нажимаешь на одну пустую
            Ritem(X, Y);
            DRitem(X, Y);
            Ditem(X, Y);
        } else if (X == 9 && Y == 0) {
            Litem(X, Y);
            DLitem(X, Y);
            Ditem(X, Y);
        } else if (X == 9 && Y == 9) {
            Uitem(X, Y);
            ULitem(X, Y);
            Litem(X, Y);
        } else if (X == 0 && Y == 9) {
            Uitem(X, Y);
            URitem(X, Y);
            Ritem(X, Y);
        } else if (X == 0) {
            Uitem(X, Y);
            URitem(X, Y);
            Ritem(X, Y);
            DRitem(X, Y);
            Ditem(X, Y);
        } else if (Y == 0) {
            Litem(X, Y);
            DLitem(X, Y);
            Ditem(X, Y);
            DRitem(X, Y);
            Ritem(X, Y);
        } else if (X == 9) {
            Uitem(X, Y);
            ULitem(X, Y);
            Litem(X, Y);
            DLitem(X, Y);
            Ditem(X, Y);
        } else if (Y == 9) {
            Litem(X, Y);
            ULitem(X, Y);
            Uitem(X, Y);
            URitem(X, Y);
            Ritem(X, Y);
        } else {
            Uitem(X, Y);
            URitem(X, Y);
            Ritem(X, Y);
            DRitem(X, Y);
            Ditem(X, Y);
            DLitem(X, Y);
            Litem(X, Y);
            ULitem(X, Y);
        }
    }

    private void Uitem(int X, int Y){
        Y--;
        IconSwitch(X, Y);
        if(field.ItemsNumberGetter(X, Y) == 0 && !field.GotCheckedGetter(X, Y) && !field.FlagGetter(X, Y)){
            field.GotCheckedSetter(X, Y);
            OpenClosedItems(X, Y);
        }
    }
    private void URitem(int X, int Y){
        Y--;
        X++;
        IconSwitch(X, Y);
        if(field.ItemsNumberGetter(X, Y) == 0 && !field.GotCheckedGetter(X, Y) && !field.FlagGetter(X, Y)){
            field.GotCheckedSetter(X, Y);
            OpenClosedItems(X, Y);
        }
    }
    private void Ritem(int X, int Y){
        X++;
        IconSwitch(X, Y);
        if(field.ItemsNumberGetter(X, Y) == 0 && !field.GotCheckedGetter(X, Y) && !field.FlagGetter(X, Y)){
            field.GotCheckedSetter(X, Y);
            OpenClosedItems(X, Y);
        }
    }

    private void DRitem(int X, int Y){
        X++;
        Y++;
        IconSwitch(X, Y);
        if(field.ItemsNumberGetter(X, Y) == 0 && !field.GotCheckedGetter(X, Y) && !field.FlagGetter(X, Y)){
            field.GotCheckedSetter(X, Y);
            OpenClosedItems(X, Y);
        }
    }
    private void Ditem(int X, int Y){
        Y++;
        IconSwitch(X, Y);
        if(field.ItemsNumberGetter(X, Y) == 0 && !field.GotCheckedGetter(X, Y) && !field.FlagGetter(X, Y)){
            field.GotCheckedSetter(X, Y);
            OpenClosedItems(X, Y);
        }
    }
    private void DLitem(int X, int Y){
        Y++;
        X--;
        IconSwitch(X, Y);
        if(field.ItemsNumberGetter(X, Y) == 0 && !field.GotCheckedGetter(X, Y) && !field.FlagGetter(X, Y)){
            field.GotCheckedSetter(X, Y);
            OpenClosedItems(X, Y);
        }
    }
    private void Litem(int X, int Y){
        X--;
        IconSwitch(X, Y);
        if(field.ItemsNumberGetter(X, Y) == 0 && !field.GotCheckedGetter(X, Y) && !field.FlagGetter(X, Y)){
            field.GotCheckedSetter(X, Y);
            OpenClosedItems(X, Y);
        }
    }
    private void ULitem(int X, int Y){
        Y--;
        X--;
        IconSwitch(X, Y);
        if(field.ItemsNumberGetter(X, Y) == 0 && !field.GotCheckedGetter(X, Y) && !field.FlagGetter(X, Y)){
            field.GotCheckedSetter(X, Y);
            OpenClosedItems(X, Y);
        }
    }
}
