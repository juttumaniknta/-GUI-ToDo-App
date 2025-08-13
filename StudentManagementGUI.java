import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

class Student {
    int id;
    String name;
    double marks;

    Student(int id, String name, double marks) {
        this.id = id;
        this.name = name;
        this.marks = marks;
    }
}

public class StudentManagementGUI extends JFrame {

    private JTextField txtId, txtName, txtMarks;
    private DefaultTableModel tableModel;
    private JTable table;
    private ArrayList<Student> students = new ArrayList<>();

    public StudentManagementGUI() {
        setTitle("Student Record Management");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // -------- Input Panel --------
        JPanel panelForm = new JPanel(new GridLayout(3, 2, 5, 5));
        panelForm.setBorder(BorderFactory.createTitledBorder("Student Details"));

        panelForm.add(new JLabel("ID:"));
        txtId = new JTextField();
        panelForm.add(txtId);

        panelForm.add(new JLabel("Name:"));
        txtName = new JTextField();
        panelForm.add(txtName);

        panelForm.add(new JLabel("Marks:"));
        txtMarks = new JTextField();
        panelForm.add(txtMarks);

        // -------- Buttons Panel --------
        JPanel panelButtons = new JPanel(new FlowLayout());
        JButton btnAdd = new JButton("Add");
        JButton btnUpdate = new JButton("Update");
        JButton btnDelete = new JButton("Delete");
        JButton btnView = new JButton("View All");

        panelButtons.add(btnAdd);
        panelButtons.add(btnUpdate);
        panelButtons.add(btnDelete);
        panelButtons.add(btnView);

        // Top container
        JPanel panelTop = new JPanel(new BorderLayout());
        panelTop.add(panelForm, BorderLayout.CENTER);
        panelTop.add(panelButtons, BorderLayout.SOUTH);

        add(panelTop, BorderLayout.NORTH);

        // -------- Table --------
        tableModel = new DefaultTableModel(new String[]{"ID", "Name", "Marks"}, 0);
        table = new JTable(tableModel);
        add(new JScrollPane(table), BorderLayout.CENTER);

        // -------- Actions --------
        btnAdd.addActionListener(e -> addStudent());
        btnUpdate.addActionListener(e -> updateStudent());
        btnDelete.addActionListener(e -> deleteStudent());
        btnView.addActionListener(e -> refreshTable());

        // Click-to-fill table rows
        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int row = table.getSelectedRow();
                if (row != -1) {
                    txtId.setText(table.getValueAt(row, 0).toString());
                    txtName.setText(table.getValueAt(row, 1).toString());
                    txtMarks.setText(table.getValueAt(row, 2).toString());
                }
            }
        });

        setVisible(true);
    }

    private void addStudent() {
        try {
            int id = Integer.parseInt(txtId.getText().trim());
            String name = txtName.getText().trim();
            double marks = Double.parseDouble(txtMarks.getText().trim());

            if (name.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Name cannot be empty!");
                return;
            }

            for (Student s : students) {
                if (s.id == id) {
                    JOptionPane.showMessageDialog(this, "ID already exists!");
                    return;
                }
            }

            students.add(new Student(id, name, marks));
            refreshTable();
            clearFields();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid input!");
        }
    }

    private void updateStudent() {
        try {
            int id = Integer.parseInt(txtId.getText().trim());
            String name = txtName.getText().trim();
            double marks = Double.parseDouble(txtMarks.getText().trim());

            for (Student s : students) {
                if (s.id == id) {
                    s.name = name;
                    s.marks = marks;
                    refreshTable();
                    clearFields();
                    return;
                }
            }
            JOptionPane.showMessageDialog(this, "Student not found!");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid input!");
        }
    }

    private void deleteStudent() {
        try {
            int id = Integer.parseInt(txtId.getText().trim());
            int confirm = JOptionPane.showConfirmDialog(this, "Delete this student?", "Confirm", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                students.removeIf(s -> s.id == id);
                refreshTable();
                clearFields();
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid ID!");
        }
    }

    private void refreshTable() {
        tableModel.setRowCount(0);
        for (Student s : students) {
            tableModel.addRow(new Object[]{s.id, s.name, s.marks});
        }
    }

    private void clearFields() {
        txtId.setText("");
        txtName.setText("");
        txtMarks.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(StudentManagementGUI::new);
    }
}
