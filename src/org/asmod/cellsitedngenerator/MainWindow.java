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
import java.util.Map;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import org.asmod.cellsitedngenerator.business.ExcelFileService;
import org.asmod.cellsitedngenerator.business.ExcelFileServiceImpl;

public class MainWindow extends JFrame {

    private JPanel contentPane;
    private JFileChooser fileChooser;
    private String currentDirectory = "";
    private ArrayList<String> fileList = new ArrayList<String>();
    private String saveFilePath = null;
    private JTextArea textAreaMarketSiteInfoFile = new JTextArea(8, 30);
    private JTextArea textAreaSiteAssignment = new JTextArea(8, 30);
    private JTextArea textAreaGeneratedDNListFiles = new JTextArea(4, 60);
    private ArrayList<String> marketSiteInfoFileList = new ArrayList<String>();
    private ArrayList<String> siteAssignmentExportedFilesList = new ArrayList<String>();

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
    public MainWindow() {
	setTitle("Powered by Java Swing, Apache POI");
	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	setSize(780, 450);
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

	JButton btnNewButton = new JButton("Browse Market Site Info File...");
	btnNewButton.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent e) {
		fileChooser(textAreaMarketSiteInfoFile);
	    }
	});
	panel_4.add(btnNewButton);

	JButton btnBrowseSiteAssignment = new JButton(
		"Browse Site Assignment Exported Files...");
	btnBrowseSiteAssignment.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent e) {
		fileChooser(textAreaSiteAssignment);
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

	JButton btnGenerateDNList = new JButton("Generate DN List File(s)");
	btnGenerateDNList.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent e) {
		// TODO: business logic

		ExcelFileService excelFileService = new ExcelFileServiceImpl();

		// List
		List<String> idList = excelFileService
			.getSiteIdList(Constants.SAMPLE_FILEPATH_ASSIGNMENT);

		// Map 2G
		Map<String, String> twoGMap = excelFileService
			.getBTSBCFNameBTSBCFDNMap(
				Constants.SAMPLE_FILEPATH_MARKET_SITE);
		// Map 3G
		Map<String, String> threeGMap = excelFileService
			.getWBTSDNMap(Constants.SAMPLE_FILEPATH_MARKET_SITE);

		// TODO: compare idList vs dnMap;
		// TODO: Save to excel aftr all other 2 added to map

		Map<String, String> gMap = threeGMap;

		int matchedId = 0;
		for (String id : idList) {

		    if (gMap.containsKey(id)) {
			matchedId++;
			System.out.println("Map contains: " + id + ""
				+ " with value: " + gMap.get(id));
		    }
		}
		System.out.println("total matched: " + matchedId);

	    }
	});
	panel_9.add(btnGenerateDNList);

	JPanel panel_7 = new JPanel();
	panel_2.add(panel_7, BorderLayout.CENTER);
	panel_7.setLayout(new BorderLayout(0, 0));

	JLabel lblGeneratedDnList = new JLabel("Generated DN list files(s):");
	lblGeneratedDnList.setVerticalAlignment(SwingConstants.TOP);
	lblGeneratedDnList.setHorizontalAlignment(SwingConstants.CENTER);
	lblGeneratedDnList.setFont(new Font("Tahoma", Font.BOLD, 11));
	lblGeneratedDnList.setAlignmentX(0.5f);
	panel_7.add(lblGeneratedDnList, BorderLayout.NORTH);

	JPanel panel_8 = new JPanel();
	panel_7.add(panel_8, BorderLayout.CENTER);

	JScrollPane scrollPane_2 = new JScrollPane();
	panel_8.add(scrollPane_2);
	textAreaGeneratedDNListFiles.setWrapStyleWord(true);

	scrollPane_2.setViewportView(textAreaGeneratedDNListFiles);
	textAreaGeneratedDNListFiles.setLineWrap(true);
	textAreaGeneratedDNListFiles.setEditable(false);
	textAreaGeneratedDNListFiles.setBackground(Color.GRAY);

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

	JLabel lblNewLabel_1 = new JLabel("New label");

    }

    private String getCurrentDirectory() {
	return currentDirectory;
    }

    private void setCurrentDirectory(String currentDirectory) {
	this.currentDirectory = currentDirectory;
    }

    private String getSaveFilePath() {
	return saveFilePath;
    }

    private void setSaveFilePath(String saveFilePath) {
	this.saveFilePath = saveFilePath;
    }

    private void fileChooser(JTextArea textArea) {

	fileChooser = new JFileChooser();
	fileChooser.setMultiSelectionEnabled(true);

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
		    if (counter == 1) {
			setSaveFilePath(file.getParent());
		    }

		}

		// Do it here
		if (fileList.size() > 0) {
		    setTextAreaText(fileList, textArea);
		    setList(fileList, textArea);
		}

	    } catch (Exception ex) {
		System.err.println("I/O error occurred " + ex.getMessage());
	    }
	}
    }

    private void setTextAreaText(ArrayList<String> files, JTextArea textArea) {
	if (files.size() > 0) {
	    String selectedFiles = "";

	    for (String filePath : files) {
		selectedFiles += filePath + "\n";

	    }
	    textArea.setText(selectedFiles);
	}
    }

    private void setList(ArrayList<String> fileList, JTextArea textArea) {

	if (textArea.equals(textAreaMarketSiteInfoFile)) {
	    marketSiteInfoFileList = fileList;
	} else if (textArea.equals(textAreaSiteAssignment)) {
	    siteAssignmentExportedFilesList = fileList;
	}

    }

}
