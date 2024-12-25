package com.mycompany.gpa;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class GPA {
    private JFrame frame;
    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField courseField;
    private JComboBox<String> gradeBox;
    private JTextField weightField;
    private JLabel gpaLabel;
    private ArrayList<String> history;

    public GPA() {
        // Initialize history
        history = new ArrayList<>();

        // Frame setup
        frame = new JFrame("GPA Calculator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);
        frame.setLayout(new BorderLayout());

        // Table setup
        tableModel = new DefaultTableModel(new String[]{"Course Name", "Grade", "Weight"}, 0);
        table = new JTable(tableModel);
        JScrollPane tableScrollPane = new JScrollPane(table);

        // Input panel setup
        JPanel inputPanel = new JPanel(new GridLayout(2, 4, 10, 10));

        courseField = new JTextField();
        gradeBox = new JComboBox<>(new String[]{"A", "B", "C", "D", "F"});
        weightField = new JTextField();
        JButton addButton = new JButton("Add Course");
        JButton clearButton = new JButton("Clear");

        inputPanel.add(new JLabel("Course Name:"));
        inputPanel.add(courseField);
        inputPanel.add(new JLabel("Grade:"));
        inputPanel.add(gradeBox);
        inputPanel.add(new JLabel("Weight (Credits):"));
        inputPanel.add(weightField);
        inputPanel.add(addButton);
        inputPanel.add(clearButton);

        // Bottom panel setup
        JPanel bottomPanel = new JPanel(new BorderLayout());
        gpaLabel = new JLabel("GPA: 0.00");
        JButton saveButton = new JButton("Save History");
        JButton showHistoryButton = new JButton("Show History");
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(saveButton);
        buttonPanel.add(showHistoryButton);

        bottomPanel.add(gpaLabel, BorderLayout.WEST);
        bottomPanel.add(buttonPanel, BorderLayout.EAST);

        // Add components to frame
        frame.add(tableScrollPane, BorderLayout.CENTER);
        frame.add(inputPanel, BorderLayout.NORTH);
        frame.add(bottomPanel, BorderLayout.SOUTH);

        // Add button action listeners
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addCourse();
                updateGPA(); // Update GPA immediately after adding a course
            }
        });

        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearFields();
            }
        });

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveHistory();
            }
        });

        showHistoryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showHistory();
            }
        });

        frame.setVisible(true);
    }

    private void addCourse() {
        String courseName = courseField.getText();
        String grade = (String) gradeBox.getSelectedItem();
        String weightText = weightField.getText();

        if (courseName.isEmpty() || weightText.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Please fill in all fields.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            double weight = Double.parseDouble(weightText);
            tableModel.addRow(new Object[]{courseName, grade, weight});
            courseField.setText("");
            weightField.setText("");
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(frame, "Weight must be a number.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void clearFields() {
        courseField.setText("");
        weightField.setText("");
        gradeBox.setSelectedIndex(0);
    }

    private void updateGPA() {
        double totalPoints = 0;
        double totalWeights = 0;

        for (int i = 0; i < tableModel.getRowCount(); i++) {
            String grade = (String) tableModel.getValueAt(i, 1);
            double weight = Double.parseDouble(tableModel.getValueAt(i, 2).toString());

            totalPoints += getGradePoints(grade) * weight;
            totalWeights += weight;
        }

        double gpa = totalWeights > 0 ? totalPoints / totalWeights : 0;
        gpaLabel.setText(String.format("GPA: %.2f", gpa));
    }

    private double getGradePoints(String grade) {
        switch (grade) {
            case "A": return 4.0;
            case "B": return 3.0;
            case "C": return 2.0;
            case "D": return 1.0;
            case "F": return 0.0;
            default: return 0.0;
        }
    }

    private void saveHistory() {
        StringBuilder sb = new StringBuilder();
        sb.append("GPA: ").append(gpaLabel.getText()).append("\n");
        for (int i = 0; i < tableModel.getRowCount(); i++) {
            sb.append(tableModel.getValueAt(i, 0)).append(" - ")
              .append(tableModel.getValueAt(i, 1)).append(" - ")
              .append(tableModel.getValueAt(i, 2)).append("\n");
        }
        history.add(sb.toString());
        JOptionPane.showMessageDialog(frame, "History saved!", "Info", JOptionPane.INFORMATION_MESSAGE);
    }

    private void showHistory() {
        if (history.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "No history available.", "Info", JOptionPane.INFORMATION_MESSAGE);
        } else {
            StringBuilder sb = new StringBuilder();
            for (String record : history) {
                sb.append(record).append("\n---\n");
            }
            JTextArea textArea = new JTextArea(sb.toString());
            textArea.setEditable(false);
            JScrollPane scrollPane = new JScrollPane(textArea);
            scrollPane.setPreferredSize(new Dimension(400, 300));
            JOptionPane.showMessageDialog(frame, scrollPane, "History", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(GPA::new);
    }

    void setVisible(boolean b) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}