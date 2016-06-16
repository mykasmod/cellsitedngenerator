package org.asmod.cellsitedngenerator;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

import org.apache.tools.ant.Main;
import org.asmod.cellsitedngenerator.Constants;
import org.asmod.cellsitedngenerator.business.ExcelFileService;
import org.asmod.cellsitedngenerator.business.ExcelFileServiceImpl;
import javax.swing.JList;

public class MainWindow extends JFrame {

    private JPanel contentPane;
    private JFileChooser fileChooser;
    private String currentDirectory = "";

    private JTextArea textAreaMarketSiteInfoFile = new JTextArea(8, 30);
    private JTextArea textAreaSiteAssignment = new JTextArea(8, 30);
    private JTextArea textAreaGeneratedDNListFiles = new JTextArea(4, 60);
    private List<String> marketSiteInfoFileList = new ArrayList<String>();
    private List<String> siteAssignmentExportedFilesList = new ArrayList<String>();

    private JProgressBar progressBar = new JProgressBar();
    private Task task;
    private JButton btnGenerateDNList = new JButton("Generate DN List File(s)");
    private static JTextArea textAreaLogger = new JTextArea();
    
    /**
     * Launch the application.
     */
    public static void main(String[] args) {
	EventQueue.invokeLater(new Runnable() {
	    public void run() {
		try {
		    MainWindow frame = new MainWindow();
		    frame.setVisible(true);
		} catch (Exception e) {
		    e.printStackTrace();
		}
	    }
	});
    }

    /**
     * Create the frame.
     */
    //public MainWindow() {
    private MainWindow() {
	setTitle("Powered by Java Swing, Apache POI");
	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	setSize(780, 540);
	setLocationRelativeTo(null);

	contentPane = new JPanel();
	contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
	setContentPane(contentPane);
	contentPane.setLayout(new BorderLayout(0, 0));

	JPanel nortPanel = new JPanel();
	contentPane.add(nortPanel, BorderLayout.NORTH);
	nortPanel.setLayout(new BoxLayout(nortPanel, BoxLayout.Y_AXIS));

	JPanel panel_3 = new JPanel();
	nortPanel.add(panel_3);

	JLabel lblNewLabel_3 = new JLabel("Cell Site DN Generator");
	lblNewLabel_3.setFont(new Font("Tahoma", Font.BOLD, 14));
	panel_3.add(lblNewLabel_3);
	lblNewLabel_3.setHorizontalAlignment(SwingConstants.CENTER);

	JPanel panel_4 = new JPanel();
	nortPanel.add(panel_4);
	panel_4.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

	JButton btnBrowseMarketSiteInfo = new JButton(
		"Browse Market Site Info File...");
	btnBrowseMarketSiteInfo.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent e) {
		fileChooser(textAreaMarketSiteInfoFile, Constants.MARKET);
	    }
	});
	panel_4.add(btnBrowseMarketSiteInfo);

	JButton btnBrowseSiteAssignment = new JButton(
		"Browse Site Assignment Exported Files...");
	btnBrowseSiteAssignment.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent e) {
		fileChooser(textAreaSiteAssignment, Constants.ASSIGNMENT);
	    }
	});
	panel_4.add(btnBrowseSiteAssignment);

	JPanel midPanel = new JPanel();
	contentPane.add(midPanel, BorderLayout.CENTER);
	midPanel.setLayout(new BorderLayout(0, 0));

	JPanel panel_1 = new JPanel();
	midPanel.add(panel_1, BorderLayout.NORTH);
	panel_1.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
	panel_1.setAutoscrolls(true);

	JPanel panel_5 = new JPanel();
	panel_1.add(panel_5);
	panel_5.setLayout(new BoxLayout(panel_5, BoxLayout.Y_AXIS));

	JLabel lblNewLabel = new JLabel("Market Site Info File:");
	lblNewLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
	lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 11));
	lblNewLabel.setVerticalAlignment(SwingConstants.TOP);
	panel_5.add(lblNewLabel);
	lblNewLabel.setHorizontalAlignment(SwingConstants.LEFT);
	lblNewLabel.setAlignmentY(Component.TOP_ALIGNMENT);

	JScrollPane scrollPane = new JScrollPane();

	panel_5.add(scrollPane);
	textAreaMarketSiteInfoFile.setWrapStyleWord(true);

	scrollPane.setViewportView(textAreaMarketSiteInfoFile);
	textAreaMarketSiteInfoFile.setLineWrap(true);
	textAreaMarketSiteInfoFile.setEditable(false);
	textAreaMarketSiteInfoFile.setBackground(Color.GRAY);

	JPanel panel_6 = new JPanel();
	panel_1.add(panel_6);
	panel_6.setLayout(new BoxLayout(panel_6, BoxLayout.Y_AXIS));

	JLabel lblNewLabel_2 = new JLabel("Site Assignment Exported Files:");
	lblNewLabel_2.setAlignmentX(Component.CENTER_ALIGNMENT);
	lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD, 11));
	lblNewLabel_2.setVerticalAlignment(SwingConstants.TOP);
	panel_6.add(lblNewLabel_2);
	lblNewLabel_2.setHorizontalAlignment(SwingConstants.LEFT);

	JScrollPane scrollPane_1 = new JScrollPane();
	panel_6.add(scrollPane_1);
	textAreaSiteAssignment.setWrapStyleWord(true);
	scrollPane_1.setViewportView(textAreaSiteAssignment);

	textAreaSiteAssignment.setLineWrap(true);
	textAreaSiteAssignment.setEditable(false);
	textAreaSiteAssignment.setBackground(Color.GRAY);

	JPanel panel_2 = new JPanel();
	midPanel.add(panel_2, BorderLayout.CENTER);
	panel_2.setLayout(new BorderLayout(0, 0));

	JPanel panel_9 = new JPanel();
	panel_2.add(panel_9, BorderLayout.NORTH);

	btnGenerateDNList.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent e) {
		// TODO: business logic
		// TODO: Improve progress and thread using Swingworker
	        
	        
	        try {
	            btnGenerateDNList.setText("Generating DN List...");

	            progressBar.setValue(0);
	            task = new Task();
	            task.start();

            } catch (Exception e2) {
               List<String> loggerList = new ArrayList<String>();
               btnGenerateDNList.setText("Generate DN List File(s)");
               loggerList.add(e2.getMessage());               
               setTextAreaLoggerText(loggerList);
               
            }
		
	    }
	});
	panel_9.add(btnGenerateDNList);

	JPanel panel_7 = new JPanel();
	panel_2.add(panel_7, BorderLayout.CENTER);
	panel_7.setLayout(new BorderLayout(0, 0));

	JLabel lblGeneratedDnList = new JLabel("Generated DN list files(s):");
	panel_7.add(lblGeneratedDnList, BorderLayout.NORTH);
	lblGeneratedDnList.setVerticalAlignment(SwingConstants.TOP);
	lblGeneratedDnList.setHorizontalAlignment(SwingConstants.CENTER);
	lblGeneratedDnList.setFont(new Font("Tahoma", Font.BOLD, 11));
	lblGeneratedDnList.setAlignmentX(0.5f);

	JPanel panel_8 = new JPanel();
	panel_7.add(panel_8, BorderLayout.CENTER);

	JScrollPane scrollPane_2 = new JScrollPane();
	panel_8.add(scrollPane_2);
	textAreaGeneratedDNListFiles.setWrapStyleWord(true);

	scrollPane_2.setViewportView(textAreaGeneratedDNListFiles);
	textAreaGeneratedDNListFiles.setLineWrap(true);
	textAreaGeneratedDNListFiles.setEditable(false);
	textAreaGeneratedDNListFiles.setBackground(Color.GRAY);

	JPanel panel_10 = new JPanel();
	panel_7.add(panel_10, BorderLayout.SOUTH);
	panel_10.setLayout(new BorderLayout(0, 0));
	
	JPanel panel_11 = new JPanel();
	panel_10.add(panel_11, BorderLayout.NORTH);
	panel_11.setLayout(new BorderLayout(0, 0));
	
	JScrollPane scrollPane_3 = new JScrollPane();
	panel_11.add(scrollPane_3, BorderLayout.NORTH);
	
	textAreaLogger.setRows(4);
	scrollPane_3.setViewportView(textAreaLogger);
		
	progressBar.setValue(0);
	progressBar.setStringPainted(true);
	panel_10.add(progressBar, BorderLayout.SOUTH);
	
	
	
	JTextArea txtrThisApplicationWill = new JTextArea();
	midPanel.add(txtrThisApplicationWill, BorderLayout.SOUTH);
	txtrThisApplicationWill.setText(
		"This application will generate DN list Output file(s) for each \"Site Assignment Exported Files\" gathered from \"Market Site Info File\" source");
	txtrThisApplicationWill.setBackground(Color.LIGHT_GRAY);
	txtrThisApplicationWill.setFont(new Font("Tahoma", Font.PLAIN, 13));
	txtrThisApplicationWill.setWrapStyleWord(true);
	txtrThisApplicationWill.setLineWrap(true);

	JPanel panel = new JPanel();
	contentPane.add(panel, BorderLayout.SOUTH);
	panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

	JLabel label_1 = new JLabel(
		"Cell Site DN Generator  Copyright Michael Asmod 2016");
	label_1.setFont(new Font("Tahoma", Font.PLAIN, 10));
	panel.add(label_1);

    }

    private void setCurrentDirectory(String currentDirectory) {
	this.currentDirectory = currentDirectory;
    }

    private void setTextAreaText(List<String> files, JTextArea textArea) {
	if (files.size() > 0) {
	    String selectedFiles = "";

	    for (String filePath : files) {
		selectedFiles += filePath + "\n";

	    }
	    textArea.setText(selectedFiles);
	}
    }
    
    


    private void setList(List<String> fileList, String siteTypeFlag) {

	if (Constants.MARKET.equals(siteTypeFlag)) {
	    marketSiteInfoFileList = fileList;
	}

	if (Constants.ASSIGNMENT.equals(siteTypeFlag)) {
	    siteAssignmentExportedFilesList = fileList;
	}

    }

    private void fileChooser(JTextArea textArea, String fileTypeFlag) {

	fileChooser = new JFileChooser();
	fileChooser.setMultiSelectionEnabled(true);
	List<String> fileList = new ArrayList<String>();

	if (!(currentDirectory.equals(null)) && currentDirectory.length() > 1) {
	    File dir = new File(currentDirectory);
	    fileChooser.setCurrentDirectory(dir);
	}
	if (fileChooser.showOpenDialog(
		MainWindow.this) == JFileChooser.APPROVE_OPTION) {
	    try {
		setCurrentDirectory(
			fileChooser.getCurrentDirectory().toString());
		fileList.clear();
		Integer counter = 0;
		for (File file : fileChooser.getSelectedFiles()) {
		    counter++;
		    fileList.add(file.getAbsolutePath());
		    /*
		     * if (counter == 1) { setSaveFilePath(file.getParent()); }
		     */

		}

		// Do it here
		if (fileList.size() > 0) {
		    setTextAreaText(fileList, textArea);
		    setList(fileList, fileTypeFlag);
		}

	    } catch (Exception ex) {
		System.err.println("I/O error occurred " + ex.getMessage());
	    }
	}
    }

    private class Task extends Thread {
	public Task() {

	}

	public void run() {

	    if ((marketSiteInfoFileList.size() > 0)
		    && (siteAssignmentExportedFilesList.size() > 0)) {

		ExcelFileService excelFileService = new ExcelFileServiceImpl();
		List<String> generatedFileList = new ArrayList<String>();
		String generatedFile = null;

		for (String marketSiteFile : marketSiteInfoFileList) {
		    for (String siteAssignFile : siteAssignmentExportedFilesList) {
			generatedFile = excelFileService
				.generateDNFile(siteAssignFile, marketSiteFile);
			generatedFileList.add(generatedFile);

		    }

		}

		setTextAreaText(generatedFileList,
			textAreaGeneratedDNListFiles);

		btnGenerateDNList.setText("Generate DN List File(s)");
	    }

	    for (int i = 0; i <= 100; i += 10) {
		final int progress = i;
		SwingUtilities.invokeLater(new Runnable() {
		    public void run() {
			progressBar.setValue(progress);
			/*
			 * outputTextArea.setText(outputTextArea.getText() +
			 * String.format("Completed %d%% of task.\n",
			 * progress));
			 */

		    }
		});

		try {
		    Thread.sleep(100);
		} catch (InterruptedException e) {
		}

	    }

	}

    }
    
    /*
     * Set TextArea Logger 
     */
    public static void setTextAreaLoggerText(List<String> files) {
        if (files.size() > 0) {
            String selectedFiles = "";
            selectedFiles = textAreaLogger.getText();
            for (String filePath : files) {
                selectedFiles += filePath + "\n";
            }
            textAreaLogger.setText(selectedFiles);
        }
    }

    
}
