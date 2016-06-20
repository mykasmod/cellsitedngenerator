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
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.text.DefaultCaret;

import org.apache.log4j.Logger;
import org.asmod.cellsitedngenerator.business.ExcelFileService;
import org.asmod.cellsitedngenerator.business.ExcelFileServiceImpl;
import org.asmod.cellsitedngenerator.thread.ProgressBarPainter;
import org.asmod.cellsitedngenerator.thread.ProgressBarWorker;

public class MainWindow extends JFrame {
    final static Logger logger = Logger.getLogger(MainWindow.class);

    private JPanel contentPane;

    private JFileChooser fileChooser;

    private String currentDirectory = "";

    private JTextArea textAreaMarketSiteInfoFile = new JTextArea(8, 30);

    private JTextArea textAreaSiteAssignment = new JTextArea(8, 30);

    private JTextArea textAreaGeneratedDNListFiles = new JTextArea(4, 60);

    private List<String> marketSiteInfoFileList = new ArrayList<String>();

    private List<String> siteAssignmentExportedFilesList = new ArrayList<String>();

    private JProgressBar progressBar = new JProgressBar();

    private JButton btnGenerateDNList = new JButton("Generate DN List File(s)");

    private static JTextArea textAreaLogger = new JTextArea();

    private static ProgressBarWorker progressBarWorker = new ProgressBarWorker();

    private static JCheckBox chkbox3GRNC = new JCheckBox("Include 3G-RNC Data");

    private Thread previousThread = new Thread();

    private Thread previousProgressBarPainterThread = new Thread();

    private Thread previousprogressBarWorkerThread = new Thread();

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
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
    // public MainWindow() {
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
        lblNewLabel_3.setFont(new Font("Tahoma", Font.BOLD, 16));
        panel_3.add(lblNewLabel_3);
        lblNewLabel_3.setHorizontalAlignment(SwingConstants.CENTER);

        JPanel panel_4 = new JPanel();
        panel_4.setBackground(new Color(70, 130, 180));
        nortPanel.add(panel_4);
        panel_4.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

        JButton btnBrowseMarketSiteInfo = new JButton("Browse Market Site Info File...");
        btnBrowseMarketSiteInfo.setFont(new Font("Tahoma", Font.BOLD, 12));
        btnBrowseMarketSiteInfo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fileChooser(textAreaMarketSiteInfoFile, Constants.MARKET);
            }
        });
        panel_4.add(btnBrowseMarketSiteInfo);

        JButton btnBrowseSiteAssignment = new JButton("Browse Site Assignment Exported Files...");
        btnBrowseSiteAssignment.setFont(new Font("Tahoma", Font.BOLD, 12));
        btnBrowseSiteAssignment.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fileChooser(textAreaSiteAssignment, Constants.ASSIGNMENT);
            }
        });
        panel_4.add(btnBrowseSiteAssignment);

        JPanel midPanel = new JPanel();
        contentPane.add(midPanel, BorderLayout.CENTER);
        midPanel.setLayout(new BorderLayout(0, 0));

        JPanel panel_1 = new JPanel();
        panel_1.setBackground(new Color(70, 130, 180));
        midPanel.add(panel_1, BorderLayout.NORTH);
        panel_1.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        panel_1.setAutoscrolls(true);

        JPanel panel_5 = new JPanel();
        panel_1.add(panel_5);
        panel_5.setLayout(new BorderLayout(0, 0));

        JPanel panel_13 = new JPanel();
        panel_13.setBackground(new Color(70, 130, 180));
        panel_5.add(panel_13, BorderLayout.NORTH);
        panel_13.setLayout(new BoxLayout(panel_13, BoxLayout.X_AXIS));

        JLabel lblNewLabel = new JLabel("Market Site Info File:");
        panel_13.add(lblNewLabel);
        lblNewLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
        lblNewLabel.setVerticalAlignment(SwingConstants.TOP);
        lblNewLabel.setHorizontalAlignment(SwingConstants.LEFT);
        lblNewLabel.setAlignmentY(Component.TOP_ALIGNMENT);

        JScrollPane scrollPane = new JScrollPane();

        panel_5.add(scrollPane);
        textAreaMarketSiteInfoFile.setFont(new Font("Monospaced", Font.BOLD, 12));
        textAreaMarketSiteInfoFile.setWrapStyleWord(true);

        scrollPane.setViewportView(textAreaMarketSiteInfoFile);
        textAreaMarketSiteInfoFile.setLineWrap(true);
        textAreaMarketSiteInfoFile.setEditable(false);
        textAreaMarketSiteInfoFile.setBackground(Color.GRAY);

        JPanel panel_6 = new JPanel();
        panel_1.add(panel_6);
        panel_6.setLayout(new BorderLayout(0, 0));

        JPanel panel_14 = new JPanel();
        panel_14.setBackground(new Color(70, 130, 180));
        panel_6.add(panel_14, BorderLayout.NORTH);
        panel_14.setLayout(new BoxLayout(panel_14, BoxLayout.X_AXIS));

        JLabel lblNewLabel_2 = new JLabel("Site Assignment Exported Files:");
        lblNewLabel_2.setBackground(new Color(70, 130, 180));
        panel_14.add(lblNewLabel_2);
        lblNewLabel_2.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD, 14));
        lblNewLabel_2.setVerticalAlignment(SwingConstants.TOP);
        lblNewLabel_2.setHorizontalAlignment(SwingConstants.LEFT);

        JScrollPane scrollPane_1 = new JScrollPane();
        panel_6.add(scrollPane_1);
        textAreaSiteAssignment.setFont(new Font("Monospaced", Font.BOLD, 12));
        textAreaSiteAssignment.setWrapStyleWord(true);
        scrollPane_1.setViewportView(textAreaSiteAssignment);

        textAreaSiteAssignment.setLineWrap(true);
        textAreaSiteAssignment.setEditable(false);
        textAreaSiteAssignment.setBackground(Color.GRAY);

        JPanel panel_2 = new JPanel();
        midPanel.add(panel_2, BorderLayout.CENTER);
        panel_2.setLayout(new BorderLayout(0, 0));

        JPanel panel_9 = new JPanel();
        panel_9.setBackground(new Color(70, 130, 180));
        panel_2.add(panel_9, BorderLayout.NORTH);
        btnGenerateDNList.setFont(new Font("Tahoma", Font.BOLD, 12));

        btnGenerateDNList.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO: Improve progress
                doTheDirtyWork();
            }
        });

        panel_9.add(chkbox3GRNC);
        chkbox3GRNC.setVerticalAlignment(SwingConstants.BOTTOM);
        panel_9.add(btnGenerateDNList);

        JPanel panel_7 = new JPanel();
        panel_2.add(panel_7, BorderLayout.CENTER);
        panel_7.setLayout(new BorderLayout(0, 0));

        JPanel panel_8 = new JPanel();
        panel_7.add(panel_8, BorderLayout.CENTER);
        panel_8.setLayout(new BorderLayout(0, 0));

        JPanel panel_12 = new JPanel();
        panel_12.setBackground(new Color(70, 130, 180));
        panel_8.add(panel_12, BorderLayout.NORTH);
        panel_12.setLayout(new BoxLayout(panel_12, BoxLayout.X_AXIS));

        JLabel lblGeneratedDnList = new JLabel("Generated DN list files(s):");
        lblGeneratedDnList.setBackground(new Color(70, 130, 180));
        panel_12.add(lblGeneratedDnList);
        lblGeneratedDnList.setVerticalAlignment(SwingConstants.TOP);
        lblGeneratedDnList.setHorizontalAlignment(SwingConstants.LEFT);
        lblGeneratedDnList.setFont(new Font("Tahoma", Font.BOLD, 14));
        lblGeneratedDnList.setAlignmentX(0.5f);

        JScrollPane scrollPane_2 = new JScrollPane();
        panel_8.add(scrollPane_2);
        textAreaGeneratedDNListFiles.setFont(new Font("Monospaced", Font.BOLD, 12));
        textAreaGeneratedDNListFiles.setWrapStyleWord(true);

        scrollPane_2.setViewportView(textAreaGeneratedDNListFiles);
        textAreaGeneratedDNListFiles.setLineWrap(true);
        textAreaGeneratedDNListFiles.setEditable(false);
        textAreaGeneratedDNListFiles.setBackground(Color.GRAY);

        JPanel panel_10 = new JPanel();
        panel_10.setBackground(new Color(70, 130, 180));
        panel_7.add(panel_10, BorderLayout.SOUTH);
        panel_10.setLayout(new BorderLayout(0, 0));

        JPanel panel_11 = new JPanel();
        panel_11.setBackground(new Color(70, 130, 180));
        panel_10.add(panel_11, BorderLayout.NORTH);
        panel_11.setLayout(new BorderLayout(0, 0));

        JLabel lblNewLabel_1 = new JLabel("Log Events:");
        lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 14));
        lblNewLabel_1.setBackground(new Color(70, 130, 180));
        panel_11.add(lblNewLabel_1, BorderLayout.NORTH);

        JScrollPane scrollPane_3 = new JScrollPane();
        panel_11.add(scrollPane_3, BorderLayout.CENTER);
        textAreaLogger.setFont(new Font("Monospaced", Font.BOLD, 12));
        textAreaLogger.setForeground(new Color(0, 100, 0));
        textAreaLogger.setBackground(new Color(0, 0, 0));

        textAreaLogger.setRows(4);
        DefaultCaret caret = (DefaultCaret)textAreaLogger.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
        scrollPane_3.setViewportView(textAreaLogger);

        progressBar.setValue(0);
        progressBar.setStringPainted(true);
        panel_10.add(progressBar, BorderLayout.SOUTH);

        JTextArea txtrThisApplicationWill = new JTextArea();
        midPanel.add(txtrThisApplicationWill, BorderLayout.SOUTH);
        txtrThisApplicationWill.setText("This application will generate DN list file(s) for each \"Site Assignment\" from \"Market Site Info\" source");
        txtrThisApplicationWill.setBackground(new Color(70, 130, 180));
        txtrThisApplicationWill.setFont(new Font("Tahoma", Font.BOLD, 12));
        txtrThisApplicationWill.setWrapStyleWord(true);
        txtrThisApplicationWill.setLineWrap(true);

        JPanel panel = new JPanel();
        panel.setBackground(new Color(70, 130, 180));
        contentPane.add(panel, BorderLayout.SOUTH);
        panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

        JLabel label_1 = new JLabel("Cell Site DN Generator  Copyright Michael Asmod 2016");
        label_1.setFont(new Font("Tahoma", Font.BOLD, 10));
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
            marketSiteInfoFileList.clear();
            marketSiteInfoFileList = fileList;
        }

        if (Constants.ASSIGNMENT.equals(siteTypeFlag)) {
            siteAssignmentExportedFilesList.clear();
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
        if (fileChooser.showOpenDialog(MainWindow.this) == JFileChooser.APPROVE_OPTION) {
            try {
                setCurrentDirectory(fileChooser.getCurrentDirectory().toString());
                fileList.clear();
                Integer counter = 0;
                for (File file : fileChooser.getSelectedFiles()) {
                    counter++;
                    fileList.add(file.getAbsolutePath());
                    /*
                     * COULD BE USEFUL LATER if (counter == 1) {
                     * setSaveFilePath(file.getParent()); }
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

    private void doTheDirtyWork() {
        // Fill in with the bar you want painted

        Worker workerThread = new Worker();
        Thread workerThreadStarter = new Thread(workerThread);
        if (!previousThread.equals(null)) {
            if (previousThread.isAlive()) {
                try {
                    btnGenerateDNList.setText("Generate DN List File(s)");
                    previousThread.getThreadGroup().interrupt();
                } catch (Exception e) {
                    logger.error(e.getMessage());
                }

            }
        }
        workerThreadStarter.start();
        previousThread = workerThreadStarter;
    }

    class Worker implements Runnable {
        @Override
        public void run() {

            // TODO: FOR FAST TESTING ONLY. SO NO NEED TO BROWSE FOR FILES.
            // REMOVE AFTER TEST.
            /*
             * marketSiteInfoFileList.clear();
             * siteAssignmentExportedFilesList.clear();
             * marketSiteInfoFileList.add(Constants.SAMPLE_FILEPATH_MARKET_SITE)
             * ; siteAssignmentExportedFilesList.add(Constants.
             * SAMPLE_FILEPATH_ASSIGNMENT);
             */

            if ((marketSiteInfoFileList.size() > 0) && (siteAssignmentExportedFilesList.size() > 0)) {
                ProgressBarPainter progressBarPainter = new ProgressBarPainter();
                progressBarPainter.jProgressBar1 = progressBar;

                Thread progressBarPainterStarter = new Thread(progressBarPainter);
                if (!previousProgressBarPainterThread.equals(null)) {
                    if (previousProgressBarPainterThread.isAlive()) {
                        try {
                            previousProgressBarPainterThread.getThreadGroup().interrupt();
                        } catch (Exception e) {
                            logger.error(e.getMessage());
                        }

                    }
                }
                progressBarPainterStarter.start();
                previousProgressBarPainterThread = progressBarPainterStarter;

                progressBarWorker.setjProgressBar1(progressBar);
                Thread progressBarWorkerThread = new Thread(progressBarWorker);
                if (!previousprogressBarWorkerThread.equals(null)) {
                    if (previousprogressBarWorkerThread.isAlive()) {
                        try {
                            previousprogressBarWorkerThread.getThreadGroup().interrupt();
                        } catch (Exception e) {
                            logger.error(e.getMessage());
                        }
                    }
                }
                progressBarWorkerThread.start();
                previousprogressBarWorkerThread = progressBarWorkerThread;

                ExcelFileService excelFileService = new ExcelFileServiceImpl();
                List<String> generatedFileList = new ArrayList<String>();
                String generatedFile = null;

                try {

                    for (String marketSiteFile : marketSiteInfoFileList) {
                        for (String siteAssignFile : siteAssignmentExportedFilesList) {
                            btnGenerateDNList.setText("Generating DN List...");
                            progressBar.setValue(0);
                            progressBarWorker.setInternalCount(0);

                            generatedFile = excelFileService.generateDNFile(siteAssignFile, marketSiteFile);
                            if (!generatedFile.equals(null)) {
                                generatedFileList.add(generatedFile);
                            }

                            setTextAreaText(generatedFileList, textAreaGeneratedDNListFiles);
                            MainWindow.setProgressBarWorkerInternalCount(MainWindow.getProgresBarWorkerInternalCount() + (100 - MainWindow.getProgresBarWorkerInternalCount()));
                        }
                    }
                } catch (Exception e) {
                    logger.error(e.getMessage());
                    /*
                     * if (previousThread.isAlive()) {
                     * previousThread.getThreadGroup().interrupt(); } if
                     * (Thread.currentThread().isAlive()) {
                     * Thread.currentThread().getThreadGroup().interrupt(); }
                     */

                }

                btnGenerateDNList.setText("Generate DN List File(s)");
            }
        }
    }

    /*
     * Set TextArea Logger
     */
    public static void setTextAreaLoggerText(String logMessage) {
        String existingText = textAreaLogger.getText();
        existingText += logMessage + "\n";
        textAreaLogger.setText(existingText);
    }

    /*
     * Progress Bar Worker InternalCount setter
     */
    static public void setProgressBarWorkerInternalCount(int internalCount) {
        progressBarWorker.setInternalCount(internalCount);
    }

    static public int getProgresBarWorkerInternalCount() {
        return progressBarWorker.getInternalCount();
    }

    /*
     * CheckBox 3G RNC
     */
    public static boolean getChkbox3GRNCIsSelected() {
        return chkbox3GRNC.isSelected();
    }

}
