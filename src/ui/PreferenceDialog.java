package ui;

import javax.swing.*;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;
import java.awt.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class PreferenceDialog extends JDialog {

    private JLabel usernameLabel;
    private JTextField usernameField;
    private JLabel passwordLabel;
    private JPasswordField passwordField;
    private JLabel portLabel;
    private JTextField portField;
    private JButton oKButton;
    private final PreferenceDialogListener preferenceDialogListener;
    private JButton cancel;
    private final GridBagConstraints gridBagConstraints = new GridBagConstraints();
    private final JPanel databasePreferences = new JPanel(new GridBagLayout());

    PreferenceDialog(PreferenceDialogListener preferenceDialogListener) {
        this.preferenceDialogListener = preferenceDialogListener;

        createComponentsForDatabasePreferencePanel();
        addComponentsToDatabasePreferencePanel();

        JTabbedPane preferences = new JTabbedPane();
        preferences.addTab("Database", databasePreferences);

        setSize(300, 300);
        add(preferences);
    }

    void setDefaults(String username, String password, int portNumber) {
        usernameField.setText(username);
        passwordField.setText(password);
        portField.setText(String.valueOf(portNumber));
    }

    private void createComponentsForDatabasePreferencePanel() {
        createUserNameLabelAndField();
        createPasswordLabelAndField();
        createPortLabelAndField();
        createOkButton();
        createCancelButton();
    }

    private void createUserNameLabelAndField() {
        usernameLabel = new JLabel("Username:");
        usernameField = new JTextField(10);
    }

    private void createPasswordLabelAndField() {
        passwordLabel = new JLabel("Password:");
        passwordField = new JPasswordField(10);
    }

    private void createPortLabelAndField() {
        portLabel = new JLabel("Port:");
        portField = new JTextField();
        LimitDigitsDocument document = new LimitDigitsDocument(4);
        portField.setDocument(document);
        portField.setColumns(4);
    }

    private void createOkButton() {
        oKButton = new JButton("OK");
        oKButton.addActionListener(e -> {
            try {
                int portNumber = Integer.parseInt(portField.getText());
                this.preferenceDialogListener.preferencesEmitted(usernameField.getText(), new String(passwordField.getPassword()), portNumber);
                setVisible(false);
            } catch (NumberFormatException e1) {
                JOptionPane.showMessageDialog(this, "Port number must be between 0 and 9999.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    private void createCancelButton() {
        cancel = new JButton("Cancel");
        cancel.addActionListener(e -> setVisible(false));
    }

    private void addComponentsToDatabasePreferencePanel() {

        setGridBagConstraints(0, 0, 1, 0, 0, 0, GridBagConstraints.NONE, GridBagConstraints.LINE_START);
        databasePreferences.add(usernameLabel, gridBagConstraints);

        setGridBagConstraints(0, 1, 1, 0, 0, 0, GridBagConstraints.NONE, GridBagConstraints.LINE_START);
        databasePreferences.add(usernameField, gridBagConstraints);

        setGridBagConstraints(1, 0, 1, 0, 0, 0, GridBagConstraints.NONE, GridBagConstraints.LINE_START);
        databasePreferences.add(passwordLabel, gridBagConstraints);

        setGridBagConstraints(1, 1, 1, 0, 0, 0, GridBagConstraints.NONE, GridBagConstraints.LINE_START);
        databasePreferences.add(passwordField, gridBagConstraints);

        setGridBagConstraints(2, 0, 1, 0, 0, 0, GridBagConstraints.NONE, GridBagConstraints.LINE_START);
        databasePreferences.add(portLabel, gridBagConstraints);

        setGridBagConstraints(2, 1, 1, 0, 0, 0, GridBagConstraints.NONE, GridBagConstraints.LINE_START);
        databasePreferences.add(portField, gridBagConstraints);

        setGridBagConstraints(3, 0, 1, 0, 0, 0, GridBagConstraints.NONE, GridBagConstraints.LINE_START);
        databasePreferences.add(oKButton, gridBagConstraints);

        setGridBagConstraints(3, 1, 1, 0, 0, 0, GridBagConstraints.NONE, GridBagConstraints.LINE_START);
        databasePreferences.add(cancel, gridBagConstraints);
    }

    private void setGridBagConstraints(int row, int column, int columnSpan, int insetValue, int columnWeight, int rowWeight, int fill, int anchor) {
        gridBagConstraints.gridx = column;
        gridBagConstraints.gridy = row;
        gridBagConstraints.gridwidth = columnSpan;
        gridBagConstraints.insets = new Insets(insetValue, insetValue, insetValue, insetValue);
        gridBagConstraints.weightx = columnWeight;
        gridBagConstraints.weighty = rowWeight;
        gridBagConstraints.fill = fill;
        gridBagConstraints.anchor = anchor;
    }
}

class LimitDigitsDocument extends PlainDocument {
    private final int limit;

    LimitDigitsDocument(int limit) {
        super();
        this.limit = limit;
    }

    public void insertString(int offset, String str, AttributeSet attr) throws BadLocationException {
        Pattern regEx = Pattern.compile("\\d+");

        if (str == null) return;

        Matcher matcher = regEx.matcher(str);
        if (!matcher.matches()) {
            return;
        }

        if ((getLength() + str.length()) <= limit) {
            super.insertString(offset, str, attr);
        }
    }
}