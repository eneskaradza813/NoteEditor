package newnoteeditor;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.StyledEditorKit;

public class NewNoteEditor extends JFrame{
    
    JMenuBar editorBar = new JMenuBar();
    JMenu fileMenu = new JMenu("File");
    JMenuItem newMenuItem = new JMenuItem("New");
    JMenuItem openMenuItem = new JMenuItem("Open");
    JMenuItem saveMenuItem = new JMenuItem("Save");
    JMenuItem exitMenuItem = new JMenuItem("Exit");
    JMenu formatMenu = new JMenu("Format");
    JMenu sizeMenu = new JMenu("Size");
    JMenu helpMenu = new JMenu("Help");
    JMenuItem aboutMenuItem = new JMenuItem("About Note Editor");
    JScrollPane editorPane = new JScrollPane();
    JTextPane editorTextPane = new JTextPane();
    JFileChooser myChooser = new JFileChooser();
    

    public static void main(String[] args) {
      
       new NewNoteEditor().show();
    }

    public NewNoteEditor() throws HeadlessException {
        setTitle("Note Editor");
        setResizable(false);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                exitForm(e);
            }
});
        setJMenuBar(editorBar);
        fileMenu.setMnemonic('F');
        formatMenu.setMnemonic('O');
        helpMenu.setMnemonic('H');
        newMenuItem.setAccelerator(KeyStroke.getKeyStroke('N', Event.CTRL_MASK));
        editorBar.add(fileMenu);
        fileMenu.add(newMenuItem);
        fileMenu.add(openMenuItem);
        fileMenu.add(saveMenuItem);
        fileMenu.addSeparator();
        fileMenu.add(exitMenuItem);
        editorBar.add(formatMenu);
        Action boldAction = new StyledEditorKit.BoldAction();
        boldAction.putValue(Action.NAME, "BOLD");
        boldAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke('B', Event.CTRL_MASK));
        formatMenu.add(boldAction);
        Action italicAction = new StyledEditorKit.ItalicAction();
        italicAction.putValue(Action.NAME, "Italic");
        italicAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke('I', Event.CTRL_MASK));
        formatMenu.add(italicAction);
        Action underlineAction = new StyledEditorKit.UnderlineAction();
        underlineAction.putValue(Action.NAME, "Underline");
        underlineAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke('U', Event.CTRL_MASK));
        formatMenu.add(underlineAction);
        formatMenu.add(sizeMenu);
        Action smallAction = new StyledEditorKit.FontSizeAction("Small", 12);
        smallAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke('S', Event.CTRL_MASK));
        sizeMenu.add(smallAction);
        Action mediumAction = new StyledEditorKit.FontSizeAction("Medium", 18);
        mediumAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke('M', Event.CTRL_MASK));
        sizeMenu.add(mediumAction);
        Action largeAction = new StyledEditorKit.FontSizeAction("Large", 24);
        largeAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke('L', Event.CTRL_MASK));
        sizeMenu.add(largeAction);
        editorBar.add(helpMenu);
        helpMenu.add(aboutMenuItem);
        newMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                newMenuItemActionPerformed(e);
            }
        });
        openMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openMenuItemActionPerformed(e);
            }
        });
        saveMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveMenuItemActionPerformed(e);
            }
        });
        exitMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                exitMenuItemActionPerformed(e);
            }
        });
        aboutMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                aboutMenuItemActionPerformed(e);
            }
        });
        getContentPane().setLayout(new GridBagLayout());
        GridBagConstraints gridConstraints = new GridBagConstraints();
        editorPane.setPreferredSize(new Dimension(300, 150));
        editorPane.setViewportView(editorTextPane);
        editorTextPane.setFont(new Font("Arial", Font.PLAIN, 12));
        gridConstraints.gridx = 0;
        gridConstraints.gridy = 0;
        getContentPane().add(editorPane, gridConstraints);
        pack();
        
    }
    void  newMenuItemActionPerformed(ActionEvent e){
        if(JOptionPane.showConfirmDialog(null, "Are you sure you want to start new file?", "New File", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE)==JOptionPane.YES_OPTION){
            editorTextPane.setText("");
        }
    }
    void openMenuItemActionPerformed(ActionEvent e){
        String myLine;
        myChooser.setDialogType(JFileChooser.OPEN_DIALOG);
        myChooser.setDialogTitle("Open Text File");
        myChooser.addChoosableFileFilter(new FileNameExtensionFilter("Text Files", "txt"));
        if(myChooser.showOpenDialog(this)==JFileChooser.APPROVE_OPTION){
            try{
                FileReader inputFile = new FileReader(myChooser.getSelectedFile().toString());
                editorTextPane.read(inputFile, null);
                inputFile.close();
            }catch(IOException ex){
                JOptionPane.showConfirmDialog(null, ex.getMessage(), "Error opening file", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
                
            }
        }
    }
    void saveMenuItemActionPerformed(ActionEvent e){
        myChooser.setDialogType(JFileChooser.SAVE_DIALOG);
        myChooser.setDialogTitle("Save Text File");
        myChooser.addChoosableFileFilter(new FileNameExtensionFilter("Text Files", "txt"));
        int fp, lp;
        if(myChooser.showSaveDialog(this)==JFileChooser.APPROVE_OPTION){
            if(myChooser.getSelectedFile().exists()){
                int response;
                response = JOptionPane.showConfirmDialog(null, myChooser.getSelectedFile().toString() + " exists. Overwrite?", "Confirm Save", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                if(response==JOptionPane.NO_OPTION){
                    return;
                }
            }
            String fileNAme = myChooser.getSelectedFile().toString();
            int dotlocation = fileNAme.indexOf(".");
            if(dotlocation == -1){
                fileNAme += ".txt";
            }else{
                fileNAme = fileNAme.substring(0, dotlocation)+ "txt.";
            }
            try{
                FileWriter outputFile = new FileWriter(fileNAme);
                editorTextPane.write(outputFile);
                outputFile.flush();
                outputFile.close();
            }catch(IOException ex){
                JOptionPane.showConfirmDialog(null, ex.getMessage(), "Error Writing File", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
                
            }
        }
    }
    void exitMenuItemActionPerformed(ActionEvent e){
        exitForm(null);
    }
    void aboutMenuItemActionPerformed(ActionEvent e){
        JOptionPane.showConfirmDialog(null, "About Note Editor\nCopyright 2003", "Note Editor", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE);
        
    }
    void exitForm(WindowEvent e){
        System.exit(0);
    }
}
