
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GraphicsConfiguration;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import java.util.*;

public class Notepad implements ActionListener {
	JFrame frame = new JFrame();
	Clipboard clip = Toolkit.getDefaultToolkit().getSystemClipboard();
	String fontfam = "Times New Roman";
	int size = 14;
	int style = Font.PLAIN;

	// creating menu_bar items
	JMenuBar menu = new JMenuBar();
	JMenu filemenu = new JMenu("File");
	JMenu editmenu = new JMenu("Edit");
	JMenu fontmenu = new JMenu("Font");
	JMenu aboutmenu = new JMenu("About");

	// creating file menu_items
	JMenuItem fnew = new JMenuItem("New");
	JMenuItem fopen = new JMenuItem("Open");
	JMenuItem fsave = new JMenuItem("Save");
	JMenuItem fsaveas = new JMenuItem("Save As");
	// JMenuItem fprint = new JMenuItem("Print");
	JMenuItem fexit = new JMenuItem("Exit");

	// creating edit menu_items
//	JMenuItem eundo = new JMenuItem("Undo*	             WIP");
	JMenuItem ecut = new JMenuItem("Cut");
	JMenuItem ecopy = new JMenuItem("Copy");
	JMenuItem epaste = new JMenuItem("Paste");
	JMenuItem eclear = new JMenuItem("Clear");
//	JMenuItem efind = new JMenuItem("Find");
//	JMenuItem efindnext = new JMenuItem("Find Next");
//	JMenuItem ereplace = new JMenuItem("Replace");
	JMenuItem egoto = new JMenuItem("Goto");
	JMenuItem edate = new JMenuItem("Date/Time");

	// creating about menu_items
	JMenuItem aaboutnotepad = new JMenuItem("About Notepad");

	// creating font menu_items
	JMenuItem ffont = new JMenuItem("Font");

	// creating text_area and scrollable_pane
	JTextArea txtarea = new JTextArea();
	JScrollPane scroll = new JScrollPane(txtarea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
			JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

	// creating font
	Font f = new Font(fontfam, style, size);

	public Notepad() {

		frame.setSize(600, 400);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("Notepad");
		frame.setResizable(true);
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);

		frame.setJMenuBar(menu); // adding menu_bar to frame

		frame.add(scroll); // adding scroll_pane

		// adding items to menu_bar
		menu.add(filemenu);
		menu.add(editmenu);
		menu.add(fontmenu);
		menu.add(aboutmenu);

		// adding file menu_items
		filemenu.add(fnew);
		filemenu.add(fopen);
		// filemenu.add(fsave);
		filemenu.add(fsaveas);
		// filemenu.add(fprint);
		filemenu.add(fexit);
		fnew.addActionListener(this);
		fopen.addActionListener(this);
		// fsave.addActionListener(this);
		fsaveas.addActionListener(this);
		// fprint.addActionListener(this);
		fexit.addActionListener(this);

//		editmenu.add(eundo);
		editmenu.add(ecut);
		editmenu.add(ecopy);
		editmenu.add(epaste);
		editmenu.add(eclear);
		// editmenu.add(efind);
		// editmenu.add(efindnext);
		editmenu.add(edate);
//		eundo.addActionListener(this);
		ecut.addActionListener(this);
		epaste.addActionListener(this);
		eclear.addActionListener(this);
		// efind.addActionListener(this);
		// efindnext.addActionListener(this);
		edate.addActionListener(this);

		aboutmenu.add(aaboutnotepad); // adding about menu_items
		aaboutnotepad.addActionListener(this);
		fontmenu.add(ffont); // adding font menu_items
		ffont.addActionListener(this);

		scroll.setSize(585, 365); // adding scroll_pane
		txtarea.setFont(f);
		txtarea.setLineWrap(true);
		txtarea.setWrapStyleWord(true);

		// disable the copy/paste actions
		// txtarea.setTransferHandler(null);

		// disable cut/paste/edit but copy is still available
		// txtarea.setEditable(false);

		// keyboard shortcuts
		// for menuitems setaccelerator is used(ctrl)
		// first method
		fnew.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, KeyEvent.CTRL_DOWN_MASK));
		fopen.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, KeyEvent.CTRL_DOWN_MASK));
		fsaveas.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.CTRL_DOWN_MASK));
		aaboutnotepad.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A,
				KeyEvent.CTRL_DOWN_MASK + KeyEvent.SHIFT_DOWN_MASK + KeyEvent.ALT_DOWN_MASK));
		// fexit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F4,KeyEvent.CTRL_DOWN_MASK));

		// fnew.setAccelerator(KeyStroke.getKeyStroke('N', Toolkit.getDefaultToolkit
		// ().getMenuShortcutKeyMask())); //second method

		// for menubar setmnemonic is used(alt)
		filemenu.setMnemonic(KeyEvent.VK_F);
		editmenu.setMnemonic(KeyEvent.VK_E);
		aboutmenu.setMnemonic(KeyEvent.VK_A);

	}

	@SuppressWarnings("static-access")
	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource().equals(fopen)) {
			JFileChooser openfilechooser = new JFileChooser(); // Jfilechooser open the file chooser box to browse a
																// file
			// filechooser.showopendialog returns two values i.e approve_option and
			// cancel_operation
			int option = openfilechooser.showOpenDialog(frame);// showopendialog is to open a file
			// NOTE: because we are OPENing a file, we call showOpenDialog~
			// if the user clicked OK, we have "APPROVE_OPTION"
			// so we want to open the file
			if (option == openfilechooser.APPROVE_OPTION) {
				txtarea.setText(""); // clearing out the text area
				try {
					// selecting file from given filechooser path
					Scanner sc = new Scanner(new FileReader(openfilechooser.getSelectedFile().getPath())); 

					while (sc.hasNext()) {
						txtarea.append(sc.nextLine() + "\n");
					}
					sc.close();
				} catch (Exception e1) {
					System.out.println(e1.getMessage());
					e1.printStackTrace();
				}
			}

		}

		else if (e.getSource().equals(fsaveas)) {

			JFileChooser savefilechooser = new JFileChooser(); // open a file chooser box to browse a file

			int option = savefilechooser.showSaveDialog(frame); // showsavedialog is to save a file
			if (option == savefilechooser.APPROVE_OPTION) {
				try {
					// create buffered writer to write to a file
					BufferedWriter bw = new BufferedWriter(new FileWriter(savefilechooser.getSelectedFile().getPath()));
					bw.write(txtarea.getText()); // write the contents of the TextArea to the file
					bw.close();

				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		}

		else if (e.getSource().equals(ecopy)) {
			String copytext = txtarea.getSelectedText();
			StringSelection ss = new StringSelection(copytext);
			clip.setContents(ss, ss);
		}

		else if (e.getSource().equals(ecut)) {
			// here first the text is selected and stored in a variable.
			// then it is passed through string selection which transfer the selected text
			// to clipboard.
			String sel = txtarea.getSelectedText();
			StringSelection ss = new StringSelection(sel);
			clip.setContents(ss, ss); // copying text to clipboard and the two parameters indicate the starting point
										// and ending point of selected text.
			txtarea.replaceRange("", txtarea.getSelectionStart(), txtarea.getSelectionEnd()); // when the text is cut
																								// then it is replaced
																								// by ""
		}

		else if (e.getSource().equals(epaste)) {

			try {
				// getting text from clipboard using transferable interface in a variable
				// pasteText.
				Transferable pasteText = clip.getContents(Notepad.this);
				// transferred text is converted to string type and is stored in variable sel.
				String sel = (String) pasteText.getTransferData(DataFlavor.stringFlavor);
				// paste the text in text area
				txtarea.replaceRange(sel, txtarea.getSelectionStart(), txtarea.getSelectionStart());
			} catch (Exception e2) {

			}
		}


		else if (e.getSource().equals(edate)) {
			Date date = new Date();
			JOptionPane.showMessageDialog(null, date);
		}

		else if (e.getSource().equals(eclear)) {
			txtarea.setText("");

		}

		else if (e.getSource().equals(aaboutnotepad)) {
			// show the dialog box in frame.
			JOptionPane.showMessageDialog(null, "@author:\nAman Shah\nCopyright Protected 2018", null,
					JOptionPane.PLAIN_MESSAGE); // plain_message removes the icon from dialog box
		}

		else if (e.getSource().equals(ffont)) {

			JOptionPane.showMessageDialog(null, "Work In Progress");

		}

		else if (e.getSource().equals(fexit)) {
			System.exit(0);
			// frame.dispose();

		}

		else if (e.getSource().equals(fnew)) {
			new Notepad();
			frame.dispose();
		}
	}

	public static void main(String[] args) {

		new Notepad();

	}
}

/*
class MyFont extends JFrame implements ListSelectionListener {
	public static final GraphicsConfiguration PLAIN = null;
	String fontvalue[] = { "Agency FB", "Antiqua", "Architect", "Arial", "Calibri", "Comic Sans", "Courier", "Cursive",
			"Impact", "Serif" };
	String sizevalue[] = { "5", "10", "15", "20", "25", "30", "35", "40", "45", "50", "55", "60", "65", "70" };
	String stylevalue[] = { "Font.PLAIN", "Font.BOLD", "Font.ITALIC" };
	JList list1, list2, list3;
	String fontfam, siz, style;
	JScrollPane scroll1, scroll2;

	public MyFont() {
		this.setSize(400, 300);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setTitle("Font Panel");
		this.setResizable(true);
		this.setVisible(true);
		this.setLocationRelativeTo(null);
		Container c = this.getContentPane();
		c.setLayout(new FlowLayout());

		list1 = new JList<>(fontvalue);
		list1.setBounds(30, 80, 80, 80);
		JScrollPane scroll1 = new JScrollPane(list1, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		list2 = new JList<>(sizevalue);
		JScrollPane scroll2 = new JScrollPane(list2, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		list2.setBounds(155, 80, 80, 80);
		list3 = new JList<>(stylevalue);
		JScrollPane scroll3 = new JScrollPane(list3, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		list3.setBounds(275, 80, 80, 80);

		list1.addListSelectionListener(this);
		list2.addListSelectionListener(this);
		list3.addListSelectionListener(this);
		c.add(scroll1);
		c.add(scroll2);
		c.add(scroll3);
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		if (e.getSource().equals(list1)) {
			String fontfam = (String) list1.getSelectedValue();

		} else if (e.getSource().equals(list2)) {
			siz = (String) list2.getSelectedValue();
			int size = Integer.parseInt(siz);

		} else if (e.getSource().equals(list3)) {
			String style = (String) list3.getSelectedValue();

		}

	}

}
*/