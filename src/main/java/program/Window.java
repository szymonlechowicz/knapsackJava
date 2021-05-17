package program;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Window {
    final static boolean shouldFill = true;
    final static boolean shouldWeightX = true;
    final static boolean RIGHT_TO_LEFT = false;

    public static void addGrid(Container pane) {
        if (RIGHT_TO_LEFT) {
            pane.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        }

        pane.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        if (shouldFill) {
            //natural height, maximum width
            gbc.fill = GridBagConstraints.HORIZONTAL;
        }

        JLabel numberLabel = new JLabel("Number of items: ", SwingConstants.RIGHT);
        if (shouldWeightX) {
            gbc.weightx = 1;
        }
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10,0,0,0);
        pane.add(numberLabel, gbc);

        JTextField inputNumber = new JTextField(5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1;
        gbc.gridx = 1;
        gbc.gridy = 0;
        pane.add(inputNumber, gbc);

        JLabel capacityLabel = new JLabel("Capacity: ", SwingConstants.RIGHT);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1;
        gbc.gridx = 0;
        gbc.gridy = 1;
        pane.add(capacityLabel, gbc);

        JTextField inputCapacity = new JTextField(5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1;
        gbc.gridx = 1;
        gbc.gridy = 1;
        pane.add(inputCapacity, gbc);

        Button btnGenerate = new Button("Generate");
        btnGenerate.setForeground(Color.BLACK);
        btnGenerate.setBackground(Color.CYAN);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.ipady = 30;
        gbc.gridheight = 2;
        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.insets = new Insets(10,10,10,10);
        pane.add(btnGenerate, gbc);

        Button btnRun = new Button("Run");
        btnRun.setForeground(Color.BLACK);
        btnRun.setBackground(Color.CYAN);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridheight = 2;
        gbc.gridx = 3;
        gbc.gridy = 0;
        gbc.weightx = 2;
        gbc.ipady = 30;
        gbc.ipadx = 40;
        gbc.insets = new Insets(10,10,10,10);
        pane.add(btnRun, gbc);

        JLabel itemsLabel = new JLabel("Items to put in knapsack: (value TAB weight)");
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 0;
        gbc.gridwidth = 2;
        gbc.ipady = 0;
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.insets = new Insets(10,10,10,10);
        pane.add(itemsLabel, gbc);

        JTextArea itemsTextArea = new JTextArea(15, 20);
        itemsTextArea.setBorder(BorderFactory.createCompoundBorder(
                itemsTextArea.getBorder(),
                BorderFactory.createEmptyBorder(20,20,20,20)));
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.ipady = 0;
        gbc.weightx = 0;
        gbc.gridwidth = 2;
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.insets = new Insets(10,10,10,10);
        pane.add(itemsTextArea, gbc);

        JLabel knapsackLabel = new JLabel("Items in knapsack (value | weight)");
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 0;
        gbc.gridwidth = 2;
        gbc.ipady = 0;
        gbc.gridx = 2;
        gbc.gridy = 2;
        gbc.insets = new Insets(10,10,10,10);
        pane.add(knapsackLabel, gbc);

        JTextArea knapsackTextArea = new JTextArea(15, 20);
        knapsackTextArea.setBorder(BorderFactory.createCompoundBorder(
                knapsackTextArea.getBorder(),
                BorderFactory.createEmptyBorder(20,20,20,20)));
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.ipady = 0;
        gbc.weightx = 0;
        gbc.gridwidth = 2;
        gbc.gridx = 2;
        gbc.gridy = 4;
        gbc.insets = new Insets(10,10,10,10);
        pane.add(knapsackTextArea, gbc);

        btnGenerate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                itemsTextArea.setText("");
                RandomNumberGenerator rnd = new RandomNumberGenerator(2);
                int n = rnd.nextInt(5, 16);
                int c = rnd.nextInt(20,40);
                String nString = Integer.toString(n);
                String cString = Integer.toString(c);
                inputNumber.setText(nString);
                inputCapacity.setText(cString);

                for (int i = 0; i < n; i++) {
                    int value = rnd.nextInt(1,29);
                    int weight = rnd.nextInt(1,29);

                    String vString = Integer.toString(value);
                    String wString = Integer.toString(weight);
                    itemsTextArea.append(vString + "\t" + wString + "\n");
                }

            }
        });

        btnRun.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                knapsackTextArea.setText("");
                int n, c;
                n = Integer.parseInt(inputNumber.getText());
                c = Integer.parseInt(inputCapacity.getText());
                boolean[] inKnapsack = new boolean[n];
                Item[] objects = new Item[n];

                String getText = itemsTextArea.getText();
                String[] items = getText.split("\n");

                String input[][] = new String[items.length][items.length];
                for (int i = 0; i < items.length; i++) {
                    items[i] = items[i].trim();
                    String word[] = items[i].split("\t");
                    for (int j = 0; j < word.length; j++) {
                        input[i][j] = word[j];
                    }
                }
                for (int i = 0; i < n; i++) {
                    int value = Integer.parseInt(input[i][0]);
                    int weight = Integer.parseInt(input[i][1]);
                    objects[i] = new Item(weight,value);
                }

                var kp = new Knapsack();
                inKnapsack = kp.Greedy(objects, n, c);
                for (int i = 0; i < n; i++) {
                    if (inKnapsack[i] == true) {
                        String vs = Integer.toString(objects[i].value);
                        String ws = Integer.toString(objects[i].weight);

                        knapsackTextArea.append(vs + "\t" + ws + "\n");
                    }
                }
            }
        });
    }

    private static void createGUI() {
        JFrame frame = new JFrame("Knapsack problem");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        addGrid(frame.getContentPane());

        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String args[]) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createGUI();
            }
        });

    }
}
