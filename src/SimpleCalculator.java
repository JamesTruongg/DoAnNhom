import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SimpleCalculator extends JFrame implements ActionListener {
    private JTextField displayField; // Đổi tên biến nhưng chưa sửa hết trong code
    private double number1, number2, output; // Đổi tên biến không nhất quán
    private String operation; // Đổi tên biến không nhất quán
    private boolean isNewInput = false; // Biến cờ không được cập nhật đúng

    public SimpleCalculator() {
        setTitle("Máy Tính Đơn Giản");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        displayField = new JTextField();
        displayField.setEditable(false);
        add(displayField, BorderLayout.NORTH);
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 4));

        String[] buttonLabels = {
                "7", "8", "9", "/",
                "4", "5", "6", "*",
                "1", "2", "3", "-",
                "0", "C", "=", "+"
        };

        for (String lbl : buttonLabels) {
            JButton btn = new JButton(lbl);
            btn.addActionListener(this);
            panel.add(btn);
        }

        add(panel, BorderLayout.CENTER);
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        String cmd = event.getActionCommand();

        if (cmd.charAt(0) >= '0' && cmd.charAt(0) <= '9') {
            if (isNewInput) {
                displayField.setText(""); // Xóa dữ liệu cũ nhưng không reset `isNewInput`
            }
            displayField.setText(displayField.getText() + cmd);
            isNewInput = false;
        } else if (cmd.equals("C")) {
            displayField.setText("");
            number1 = number2 = output = 0;
            operation = "";
            isNewInput = false;
        } else if (cmd.equals("=")) {
            try {
                number2 = Double.parseDouble(displayField.getText());
                switch (operation) {
                    case "+": output = number1 + number2; break;
                    case "-": output = number1 - number2; break;
                    case "*": output = number1 * number2; break;
                    case "/":
                        if (number2 == 0) {
                            displayField.setText("Error"); // Lỗi chia cho 0 nhưng không reset `number1`
                            return;
                        }
                        output = number1 / number2;
                        break;
                    default:
                        displayField.setText("Invalid Operation"); // Xử lý khi nhấn "=" mà không có phép toán nào
                        return;
                }
                displayField.setText(String.valueOf(output));
                number1 = output;
                isNewInput = true;
            } catch (NumberFormatException ex) {
                displayField.setText("Error"); // Xử lý ngoại lệ nhập sai
            }
        } else {
            if (!operation.isEmpty()) {
                return;
            }
            operation = cmd;
            try {
                number1 = Double.parseDouble(displayField.getText());
                displayField.setText("");
            } catch (NumberFormatException ex) {
                displayField.setText("Error"); // Lỗi khi nhấn toán tử mà không có số hợp lệ
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            SimpleCalculator calc = new SimpleCalculator();
            calc.setVisible(true);
        });
    }
}
