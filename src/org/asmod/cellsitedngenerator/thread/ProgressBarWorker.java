package org.asmod.cellsitedngenerator.thread;

import javax.swing.JProgressBar;

public class ProgressBarWorker implements Runnable {
    private JProgressBar jProgressBar1;
    private Integer internalCount = 0;

    public ProgressBarWorker() {

    }

    public void run() {
	// int x = 0;
	while (jProgressBar1.getValue() != jProgressBar1.getMaximum()) {
	    try {
		// Do some work
		Thread.sleep(100);
		// Update bar
		jProgressBar1.setValue(internalCount++);
	    } catch (InterruptedException ex) {
		break;
	    }
	}
    }

    public Integer getInternalCount() {
	return internalCount;
    }

    public void setInternalCount(Integer internalCount) {
	this.internalCount = internalCount;
    }

    public JProgressBar getjProgressBar1() {
	return jProgressBar1;
    }

    public void setjProgressBar1(JProgressBar jProgressBar1) {
	this.jProgressBar1 = jProgressBar1;
    }

}
