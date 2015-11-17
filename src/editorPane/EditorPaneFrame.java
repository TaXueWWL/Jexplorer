package editorPane;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Stack;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;

@SuppressWarnings("serial")
public class EditorPaneFrame extends JFrame {
	
	private static final int DEFAULT_WIDTH = 600;
	private static final int DEFAULT_HEIGHT = 400;
	
	EditorPaneFrame() {
		init();
	}
	
	
	
	public void init() {
		JFrame frame = new JFrame("JExplorer");
		frame.setBounds(200, 200, 900, 600);
		frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
		
		final Stack<String> urlStack = new Stack<>();
		final JEditorPane editorPane = new JEditorPane();
		final JTextField url = new JTextField(30);
		url.setText("http://");
		
		//设置超链接监听器
		editorPane.setEditable(false);
		editorPane.addHyperlinkListener(new HyperlinkListener() {
			
			@Override
			public void hyperlinkUpdate(HyperlinkEvent e) {
				if (e.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
					try {
						//为返回键记住链接
						urlStack.push(e.getURL().toString());
						url.setText(e.getURL().toString());
						editorPane.setPage(e.getURL());
					} catch (IOException ex) {
						editorPane.setText("The Exception is:" + ex);
					}
				}
			}
		});
		//设置一个checkbox
		final JCheckBox editable = new JCheckBox();
		editable.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				editorPane.setEditable(editable.isSelected());
			
			}
		});
		//设置一个load按钮，载入URL
		ActionListener listener = new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				try {
					urlStack.push(url.getText());
					editorPane.setPage(url.getText());
				} catch (IOException e2) {
					editorPane.setText("The Exception is:" + e2);
				}
			}
		};
		
		JButton loadButton = new JButton("载入");
		loadButton.addActionListener(listener);
		url.addActionListener(listener);
		//建立back按钮添加按钮行为
		JButton backButton = new JButton("返回");
		backButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if (urlStack.size() <= 1) {
					return;
				} try {
					urlStack.pop();
					String urlString = urlStack.peek();
					url.setText(urlString);
					editorPane.setPage(urlString);
				} catch (IOException e3) {
					editorPane.setText("The Exception is:" + e3);
				}
			}
		});
		
		frame.add(new JScrollPane(editorPane), BorderLayout.CENTER);
		
		JPanel panel = new JPanel();
		panel.add(new JLabel("URL"));
		panel.add(url);
		panel.add(loadButton);
		panel.add(backButton);
		panel.add(new JLabel("Editable"));
		panel.add(editable);
		
		frame.add(panel, BorderLayout.NORTH);
		frame.setVisible(true);
	}
	
	public static void main(String[] args) {
		new EditorPaneFrame();
	}
}
