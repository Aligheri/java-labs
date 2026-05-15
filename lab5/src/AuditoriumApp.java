import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

public class AuditoriumApp extends JFrame {

    private final List<Auditorium> rooms = new ArrayList<>();
    private DefaultTableModel tableModel;
    private JTable table;
    private JTextField tfNumber, tfCapacity, tfFloor, tfEquipment, tfComputers;
    private JComboBox<String> cbType;
    private JTextArea logArea;
    private JLabel statsLabel;
    private javax.swing.Timer autoTimer;
    private int timerTick = 0;

    public AuditoriumApp() {
        setTitle("Auditorium Manager - Lab 5");
        setSize(980, 680);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout(5, 5));
        initSampleData();
        buildUI();
        refreshTable();
        refreshStats();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void initSampleData() {
        rooms.add(new Auditorium(101, 30,  "practical",  1));
        rooms.add(new Laboratory(102, 25,  1, "computers",   15));
        rooms.add(new Auditorium(201, 120, "lecture",    2));
        rooms.add(new Auditorium(202, 40,  "practical",  2));
        rooms.add(new Laboratory(303, 18,  3, "networking",  20));
    }

    private void buildUI() {
        add(buildToolbar(),   BorderLayout.NORTH);
        add(buildTable(),     BorderLayout.CENTER);
        add(buildForm(),      BorderLayout.EAST);
        add(buildSouthPanel(),BorderLayout.SOUTH);
    }

    private JPanel buildToolbar() {
        JPanel bar = new JPanel(new FlowLayout(FlowLayout.LEFT, 6, 4));

        JButton btnOccupy = new JButton("Occupy");
        JButton btnFree   = new JButton("Free");
        JButton btnRemove = new JButton("Remove");
        JButton btnStats  = new JButton("Statistics");
        JToggleButton btnTimer = new JToggleButton("Auto-Update: OFF");

        // 4a. Anonymous class
        btnOccupy.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                occupySelected();
            }
        });

        // 4b. Lambda
        btnFree.addActionListener(e -> freeSelected());

        // 4c. Method reference
        btnRemove.addActionListener(this::removeSelected);

        btnStats.addActionListener(e -> showStatsDialog());

        btnTimer.addActionListener(e -> {
            if (btnTimer.isSelected()) {
                startAutoTimer(btnTimer);
            } else {
                stopAutoTimer(btnTimer);
            }
        });

        bar.add(btnOccupy);
        bar.add(btnFree);
        bar.add(btnRemove);
        bar.add(new JSeparator(SwingConstants.VERTICAL));
        bar.add(btnStats);
        bar.add(btnTimer);
        return bar;
    }

    private JScrollPane buildTable() {
        String[] cols = {"#", "Capacity", "Type", "Floor", "Status", "Equipment / Computers"};
        tableModel = new DefaultTableModel(cols, 0) {
            @Override
            public boolean isCellEditable(int r, int c) { return false; }
        };
        table = new JTable(tableModel);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setRowHeight(22);
        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) loadSelectedToForm();
        });
        return new JScrollPane(table);
    }

    private JPanel buildForm() {
        JPanel form = new JPanel(new GridBagLayout());
        form.setBorder(BorderFactory.createTitledBorder("Room Details"));
        form.setPreferredSize(new Dimension(230, 0));

        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(4, 6, 4, 6);
        c.fill = GridBagConstraints.HORIZONTAL;

        tfNumber    = new JTextField(8);
        tfCapacity  = new JTextField(8);
        tfFloor     = new JTextField(8);
        tfEquipment = new JTextField(8);
        tfComputers = new JTextField(8);
        cbType = new JComboBox<>(new String[]{"lecture", "practical", "laboratory"});

        Object[][] fields = {
            {"Number:",    tfNumber},
            {"Capacity:",  tfCapacity},
            {"Type:",      cbType},
            {"Floor:",     tfFloor},
            {"Equipment:", tfEquipment},
            {"Computers:", tfComputers},
        };

        for (int i = 0; i < fields.length; i++) {
            c.gridx = 0; c.gridy = i; c.weightx = 0;
            form.add(new JLabel((String) fields[i][0]), c);
            c.gridx = 1; c.weightx = 1;
            form.add((Component) fields[i][1], c);
        }

        JButton btnAdd    = new JButton("Add");
        JButton btnUpdate = new JButton("Update");
        JButton btnClear  = new JButton("Clear");

        btnAdd.addActionListener(e -> addRoom());
        btnUpdate.addActionListener(e -> updateRoom());
        btnClear.addActionListener(e -> clearForm());

        JPanel btns = new JPanel(new GridLayout(1, 3, 4, 0));
        btns.add(btnAdd); btns.add(btnUpdate); btns.add(btnClear);

        c.gridx = 0; c.gridy = fields.length; c.gridwidth = 2; c.weightx = 1;
        c.insets = new Insets(10, 6, 4, 6);
        form.add(btns, c);

        return form;
    }

    private JPanel buildSouthPanel() {
        JPanel south = new JPanel(new BorderLayout(3, 3));
        south.setBorder(BorderFactory.createEmptyBorder(2, 4, 4, 4));

        statsLabel = new JLabel("Stats: ...");
        statsLabel.setFont(statsLabel.getFont().deriveFont(Font.BOLD));

        logArea = new JTextArea(6, 0);
        logArea.setEditable(false);
        logArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 11));

        south.add(statsLabel, BorderLayout.NORTH);
        south.add(new JScrollPane(logArea), BorderLayout.CENTER);
        return south;
    }

    private void refreshTable() {
        tableModel.setRowCount(0);
        for (Auditorium a : rooms) {
            String extra = "";
            if (a instanceof Laboratory) {
                Laboratory l = (Laboratory) a;
                extra = l.getEquipmentType() + " / " + l.getComputerCount() + " PC";
            }
            tableModel.addRow(new Object[]{
                a.getNumber(), a.getCapacity(), a.getType(),
                a.getFloor(), a.isOccupied() ? "OCCUPIED" : "FREE", extra
            });
        }
    }

    private void refreshStats() {
        long free   = rooms.stream().filter(a -> !a.isOccupied()).count();
        int  totCap = rooms.stream().mapToInt(Auditorium::getCapacity).sum();
        statsLabel.setText(String.format(
            "Total: %d  |  Free: %d  |  Occupied: %d  |  Total capacity: %d",
            rooms.size(), free, rooms.size() - free, totCap));
    }

    private void log(String msg) {
        logArea.append(msg + "\n");
        logArea.setCaretPosition(logArea.getDocument().getLength());
    }

    private int selectedIndex() { return table.getSelectedRow(); }

    private void occupySelected() {
        int idx = selectedIndex();
        if (idx < 0) { warn("Select a room first."); return; }
        Auditorium a = rooms.get(idx);
        if (a.isOccupied()) {
            warn("Room #" + a.getNumber() + " is already OCCUPIED.");
        } else {
            a.occupy();
            log("[Occupy]  Room #" + a.getNumber() + " is now OCCUPIED.");
            refreshTable();
            refreshStats();
        }
    }

    private void freeSelected() {
        int idx = selectedIndex();
        if (idx < 0) { warn("Select a room first."); return; }
        Auditorium a = rooms.get(idx);
        if (!a.isOccupied()) {
            warn("Room #" + a.getNumber() + " is already FREE.");
        } else {
            a.free();
            log("[Free]    Room #" + a.getNumber() + " is now FREE.");
            refreshTable();
            refreshStats();
        }
    }

    private void removeSelected(ActionEvent e) {
        int idx = selectedIndex();
        if (idx < 0) { warn("Select a room first."); return; }
        int num = rooms.get(idx).getNumber();
        rooms.remove(idx);
        log("[Remove]  Room #" + num + " removed.");
        refreshTable();
        refreshStats();
        clearForm();
    }

    private void addRoom() {
        try {
            int number   = validateInt(tfNumber.getText(),   "Number",    1, 999);
            int capacity = validateInt(tfCapacity.getText(), "Capacity",  1, 500);
            int floor    = validateInt(tfFloor.getText(),    "Floor",     1,  20);
            String type  = (String) cbType.getSelectedItem();

            boolean exists = rooms.stream().anyMatch(a -> a.getNumber() == number);
            if (exists) throw new IllegalArgumentException("Room #" + number + " already exists.");

            Auditorium room;
            String eq = tfEquipment.getText().trim();
            if ("laboratory".equals(type) && !eq.isEmpty()) {
                int pc = tfComputers.getText().trim().isEmpty() ? 0
                        : validateInt(tfComputers.getText(), "Computers", 0, 300);
                room = new Laboratory(number, capacity, floor, eq, pc);
            } else {
                room = new Auditorium(number, capacity, type, floor);
            }
            rooms.add(room);
            log("[Add]     Room #" + number + " added (" + type + ", floor " + floor + ").");
            refreshTable();
            refreshStats();
            clearForm();
        } catch (IllegalArgumentException ex) {
            validationError(ex.getMessage());
        }
    }

    private void updateRoom() {
        int idx = selectedIndex();
        if (idx < 0) { warn("Select a room to update."); return; }
        try {
            int capacity = validateInt(tfCapacity.getText(), "Capacity", 1, 500);
            int floor    = validateInt(tfFloor.getText(),    "Floor",    1,  20);
            Auditorium a = rooms.get(idx);
            a.setCapacity(capacity);
            a.setFloor(floor);
            a.setType((String) cbType.getSelectedItem());
            if (a instanceof Laboratory) {
                String eq = tfEquipment.getText().trim();
                if (!eq.isEmpty()) ((Laboratory) a).setEquipmentType(eq);
                String pc = tfComputers.getText().trim();
                if (!pc.isEmpty()) ((Laboratory) a).setComputerCount(
                        validateInt(pc, "Computers", 0, 300));
            }
            log("[Update]  Room #" + a.getNumber() + " updated.");
            refreshTable();
            refreshStats();
        } catch (IllegalArgumentException ex) {
            validationError(ex.getMessage());
        }
    }

    private void loadSelectedToForm() {
        int idx = selectedIndex();
        if (idx < 0) return;
        Auditorium a = rooms.get(idx);
        tfNumber.setText(String.valueOf(a.getNumber()));
        tfCapacity.setText(String.valueOf(a.getCapacity()));
        tfFloor.setText(String.valueOf(a.getFloor()));
        cbType.setSelectedItem(a.getType());
        if (a instanceof Laboratory) {
            tfEquipment.setText(((Laboratory) a).getEquipmentType());
            tfComputers.setText(String.valueOf(((Laboratory) a).getComputerCount()));
        } else {
            tfEquipment.setText("");
            tfComputers.setText("");
        }
    }

    private void clearForm() {
        tfNumber.setText(""); tfCapacity.setText(""); tfFloor.setText("");
        tfEquipment.setText(""); tfComputers.setText("");
        cbType.setSelectedIndex(0);
        table.clearSelection();
    }

    private void showStatsDialog() {
        long labs  = rooms.stream().filter(a -> a instanceof Laboratory).count();
        long free  = rooms.stream().filter(a -> !a.isOccupied()).count();
        int  maxC  = rooms.stream().mapToInt(Auditorium::getCapacity).max().orElse(0);
        double avg = rooms.stream().mapToInt(Auditorium::getCapacity).average().orElse(0);
        String msg = String.format(
            "Total rooms:    %d%n" +
            "Laboratories:   %d%n" +
            "Free:           %d%n" +
            "Occupied:       %d%n" +
            "Max capacity:   %d%n" +
            "Avg capacity:   %.1f",
            rooms.size(), labs, free, rooms.size() - free, maxC, avg);
        JOptionPane.showMessageDialog(this, msg, "Statistics", JOptionPane.INFORMATION_MESSAGE);
        log("[Stats]   Dialog shown.");
    }

    private void startAutoTimer(JToggleButton btn) {
        autoTimer = new javax.swing.Timer(2000, e -> {
            timerTick++;
            log("[Timer #" + timerTick + "] Auto-refresh stats.");
            refreshStats();
        });
        autoTimer.start();
        btn.setText("Auto-Update: ON");
        log("[Timer]   Started (every 2 s).");
    }

    private void stopAutoTimer(JToggleButton btn) {
        if (autoTimer != null) autoTimer.stop();
        btn.setText("Auto-Update: OFF");
        log("[Timer]   Stopped after " + timerTick + " ticks.");
        timerTick = 0;
    }

    private int validateInt(String value, String field, int min, int max) {
        try {
            int v = Integer.parseInt(value.trim());
            if (v < min || v > max)
                throw new IllegalArgumentException(field + " must be " + min + "–" + max + ".");
            return v;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(field + " must be a valid integer.");
        }
    }

    private void warn(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Warning", JOptionPane.WARNING_MESSAGE);
    }

    private void validationError(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Validation Error", JOptionPane.ERROR_MESSAGE);
    }
}
