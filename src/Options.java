/**********************************************************************

   Class: Options.java
   purpose: Allow a user to specify who moves first, number of rows and 
            columns of a connect board, and turn on/off gravity in a game.
   Author: Thuan Truong
   E-mail: tim@drimgmt.com

***********************************************************************/
import java.awt.*;
import symantec.itools.awt.*;
import com.symantec.itools.awt.MaskedTextField;
import java.lang.*;

import javax.swing.JOptionPane;

import symantec.itools.awt.shape.Rect;
//import com.sun.java.swing.*;


public class Options extends Dialog {
    
   

	public Options(Frame parent, boolean modal)//constructor
	{
		super(parent, modal);
		// This code is automatically generated by Visual Cafe when you add
		// components to the visual environment. It instantiates and initializes
		// the components. To modify the code, only use code syntax that matches
		// what Visual Cafe can generate, or Visual Cafe may be unable to back
		// parse your Java file into its visual environment.
		//{{INIT_CONTROLS
		setLayout(null);
		setBackground(new java.awt.Color(204,204,204));
		setSize(304,495);
		setVisible(false);
		try {
			player.setSelectedRadioButtonIndex(0);
		}
		catch(java.beans.PropertyVetoException e) { }
		try {
			player.setBevelStyle(symantec.itools.awt.RadioButtonGroupPanel.BEVEL_LOWERED);
		}
		catch(java.beans.PropertyVetoException e) { }
		try {
			player.setLabel(" Choose Your Mark ");
		}
		catch(java.beans.PropertyVetoException e) { }
		try {
			player.setBorderColor(java.awt.Color.gray);
		}
		catch(java.beans.PropertyVetoException e) { }
		try {
			player.setAlignStyle(symantec.itools.awt.RadioButtonGroupPanel.ALIGN_LEFT);
		}
		catch(java.beans.PropertyVetoException e) { }
		player.setLayout(null);
		add(player);
		player.setBounds(24,24,252,133);
		player1.setState(true);
		player1.setLabel("Player 1 (Go First)");
		player.add(player1);
		player1.setBounds(104,14,132,24);
		player2.setLabel("Player2 (Go First)");
		player.add(player2);
		player2.setBounds(104,67,144,24);
		try {
			imageHuman.setImageURL(symantec.itools.net.RelativeURL.getURL("res/human.gif"));
		}
		catch (java.net.MalformedURLException error) { }
		catch(java.beans.PropertyVetoException e) { }
		imageHuman.setLayout(null);
		player.add(imageHuman);
		imageHuman.setBounds(32,5,35,33);
		try {
			imageCom.setImageURL(symantec.itools.net.RelativeURL.getURL("res/computer.gif"));
		}
		catch (java.net.MalformedURLException error) { }
		catch(java.beans.PropertyVetoException e) { }
		try {
			imageCom.setStyle(symantec.itools.awt.ImagePanel.IMAGE_NORMAL);
		}
		catch(java.beans.PropertyVetoException e) { }
		imageCom.setLayout(null);
		player.add(imageCom);
		imageCom.setBounds(32,59,34,32);
		try {
			rect1.setBevelStyle(symantec.itools.awt.shape.Rect.BEVEL_LOWERED);
		}
		catch(java.beans.PropertyVetoException e) { }
		//add(rect1);
		rect1.setBounds(24,192,253,108);
		label1.setText(" Game Parameters ");
		add(label1);
		label1.setBounds(48,180,108,24);
		label2.setText("M");
		add(label2);
		label2.setFont(new Font("Dialog", Font.PLAIN, 14));
		label2.setBounds(60,216,12,24);
		label3.setText("N");
		add(label3);
		label3.setFont(new Font("Dialog", Font.PLAIN, 14));
		label3.setBounds(60,240,12,24);
		label4.setText("K");
		add(label4);
		label4.setFont(new Font("Dialog", Font.PLAIN, 14));
		label4.setBounds(60,264,12,24);
		try {
			gravity.setSelectedRadioButtonIndex(0);
		}
		catch(java.beans.PropertyVetoException e) { }
		try {
			gravity.setBevelStyle(symantec.itools.awt.RadioButtonGroupPanel.BEVEL_LOWERED);
		}
		catch(java.beans.PropertyVetoException e) { }
		try {
			gravity.setLabel(" Gravity Settings ");
		}
		catch(java.beans.PropertyVetoException e) { }
		try {
			gravity.setBorderColor(java.awt.Color.gray);
		}
		catch(java.beans.PropertyVetoException e) { }
		try {
			gravity.setAlignStyle(symantec.itools.awt.RadioButtonGroupPanel.ALIGN_LEFT);
		}
		catch(java.beans.PropertyVetoException e) { }
		gravity.setLayout(null);
		add(gravity);
		gravity.setBounds(24,324,252,96);
		Gon.setState(true);
		Gon.setLabel("Gravity On");
		gravity.add(Gon);
		Gon.setBounds(32,14,132,24);
		Goff.setLabel("Gravity Off");
		gravity.add(Goff);
		Goff.setBounds(32,43,144,24);
		bOk.setLabel("Ok");
		add(bOk);
		bOk.setBounds(24,444,101,29);
		label5.setText("Rows");
		add(label5);
		label5.setFont(new Font("Dialog", Font.PLAIN, 12));
		label5.setBounds(132,216,36,24);
		label6.setText("Columns");
		add(label6);
		label6.setFont(new Font("Dialog", Font.PLAIN, 12));
		label6.setBounds(132,240,60,24);
		label8.setText("Winning Run");
		add(label8);
		label8.setFont(new Font("Dialog", Font.PLAIN, 12));
		label8.setBounds(132,264,96,24);
		txtrows.setText("6_");
		txtrows.setMask("00");
		txtrows.setCursor(java.awt.Cursor.getPredefinedCursor(java.awt.Cursor.TEXT_CURSOR));
		add(txtrows);
		txtrows.setBounds(84,216,35,24);
		txtcols.setText("7_");
		txtcols.setMask("00");
		txtcols.setCursor(java.awt.Cursor.getPredefinedCursor(java.awt.Cursor.TEXT_CURSOR));
		add(txtcols);
		txtcols.setBounds(84,240,35,24);
		txtkwin.setText("4");
		txtkwin.setMask("0");
		txtkwin.setCursor(java.awt.Cursor.getPredefinedCursor(java.awt.Cursor.TEXT_CURSOR));
		add(txtkwin);
		txtkwin.setBounds(84,264,35,24);
		setTitle("Game Options");
		//}}
		

		//{{INIT_MENUS
		//}}

		//{{REGISTER_LISTENERS
		SymWindow aSymWindow = new SymWindow();
		this.addWindowListener(aSymWindow);
		SymAction lSymAction = new SymAction();
		bOk.addActionListener(lSymAction);
		SymFocus aSymFocus = new SymFocus();
		txtrows.addFocusListener(aSymFocus);
		txtcols.addFocusListener(aSymFocus);
		txtkwin.addFocusListener(aSymFocus);
		//}}
	}

	public Options(Frame parent,String title, boolean modal)
	{
		this(parent,modal);
		setTitle(title);
	}
    
    /** 
     * Followings are public functions used to return
     * all the private variable values in Options class
     **/
    public int getWins()    { return wins;}
    public int getRows()    { return rows;}
    public int getCols()    { return cols;}
    public boolean getG()   { return isGon;}
    public char getPlayer() { return mark;}
    
    /**
     * Shows or hides the component depending on the boolean flag b.
     * @param b  if true, show the component; otherwise, hide the component.
     * @see java.awt.Component#isVisible
     */
    public void setVisible(boolean b)
	{
		if(b)
		{
			setLocation(50, 50);
		}
		super.setVisible(b);
	}

    public void addNotify()
	{
	    // Record the size of the window prior to calling parents addNotify.
	    Dimension d = getSize();
	    
		super.addNotify();

		if (fComponentsAdjusted)
			return;

		// Adjust components according to the insets
		Insets insets = getInsets();
		setSize(insets.left + insets.right + d.width, insets.top + insets.bottom + d.height);
		Component components[] = getComponents();
		for (int i = 0; i < components.length; i++)
		{
			Point p = components[i].getLocation();
			p.translate(insets.left, insets.top);
			components[i].setLocation(p);
		}
		fComponentsAdjusted = true;
	}

    // Used for addNotify check.
	boolean fComponentsAdjusted = false;
   
    // Used to get user inputs
    //{{DECLARE PRIVATE VARIABLES
    private int rows = 6;
    private int cols = 7;
    private int wins = 4;
    private char mark = constant.X;
    private boolean isGon = true;
    //}}
    
	//{{DECLARE_CONTROLS
	symantec.itools.awt.RadioButtonGroupPanel player = new symantec.itools.awt.RadioButtonGroupPanel();
	java.awt.Checkbox player1 = new java.awt.Checkbox();
	java.awt.Checkbox player2 = new java.awt.Checkbox();
	symantec.itools.awt.ImagePanel imageHuman = new symantec.itools.awt.ImagePanel();
	symantec.itools.awt.ImagePanel imageCom = new symantec.itools.awt.ImagePanel();
	symantec.itools.awt.shape.Rect rect1 = new symantec.itools.awt.shape.Rect();
	java.awt.Label label1 = new java.awt.Label();
	java.awt.Label label2 = new java.awt.Label();
	java.awt.Label label3 = new java.awt.Label();
	java.awt.Label label4 = new java.awt.Label();
	symantec.itools.awt.RadioButtonGroupPanel gravity = new symantec.itools.awt.RadioButtonGroupPanel();
	java.awt.Checkbox Gon = new java.awt.Checkbox();
	java.awt.Checkbox Goff = new java.awt.Checkbox();
	java.awt.Button bOk = new java.awt.Button();
	java.awt.Label label5 = new java.awt.Label();
	java.awt.Label label6 = new java.awt.Label();
	java.awt.Label label8 = new java.awt.Label();
	com.symantec.itools.awt.MaskedTextField txtrows = new com.symantec.itools.awt.MaskedTextField();
	com.symantec.itools.awt.MaskedTextField txtcols = new com.symantec.itools.awt.MaskedTextField();
	com.symantec.itools.awt.MaskedTextField txtkwin = new com.symantec.itools.awt.MaskedTextField();
	//}}

	//{{DECLARE_MENUS
	//}}

	class SymWindow extends java.awt.event.WindowAdapter
	{
		public void windowClosing(java.awt.event.WindowEvent event)
		{
			Object object = event.getSource();
			if (object == Options.this)
				Options_WindowClosing(event);
		}
	}
	
	void Options_WindowClosing(java.awt.event.WindowEvent event)
	{
		setVisible(false);		 // hide the Frame
			 
		Options_WindowClosing_Interaction1(event);
	}

	class SymAction implements java.awt.event.ActionListener
	{
		public void actionPerformed(java.awt.event.ActionEvent event)
		{
			Object object = event.getSource();
			if (object == bOk)
				bOk_ActionPerformed(event);
		}
	}

	void bOk_ActionPerformed(java.awt.event.ActionEvent event)
	{
		// to do: code goes here.
			 
		bOk_ActionPerformed_Interaction1(event);
	}

    /*
	    method: bOk_ActionPerformed_Interaction1
	    purpose: Store inputs into Option private variables.
	    visiblity: ONLY by other methods in this class
	    paramters: 1
	        java.awt.event.ActionEvent event: determine which event a user has invoked
	        (e.g. a click on a button or textbox object).
	    return NONE.
	*/
	void bOk_ActionPerformed_Interaction1(java.awt.event.ActionEvent event)
	{
	    String str1, str2, str3;
	    boolean On = false;
 
		try {
		    mark = (player.getSelectedRadioButtonIndex() == 0)?constant.X:constant.O;
		    isGon = (gravity.getSelectedRadioButtonIndex() == 0)?true:false;
		    str1  = txtrows.getText();
		    str2  = txtcols.getText();
		    str3  = txtkwin.getText();
		    
		    if(str1.equals("__") || str2.equals("__") || str3.equals("_"))
		    {
		        On = true;
		        JOptionPane.showMessageDialog(this, "Please enter a positive integer number starting from 1",
		                                            "Invalid Input" ,constant.ERROR_MESSAGE);
		    }
		    else
		    {
		        if(str1.substring(1).equals("_"))
		            rows = java.lang.Integer.parseInt(str1.substring(0,1),10);
		        else
		            rows = java.lang.Integer.parseInt(str1,10);
		        
		        if(str2.substring(1).equals("_"))
		            cols = java.lang.Integer.parseInt(str2.substring(0,1),10);
		        else
		            cols = java.lang.Integer.parseInt(str2,10);
		        wins = java.lang.Integer.parseInt(str3,10);
	           	//  Options Hide the Options
			    
	            if(rows==0 || cols==0 || wins==0 || (wins>rows && wins>cols) )
	            {
	                JOptionPane.showMessageDialog(this,(wins>rows && wins>cols)?"Please Choose a smaller K - e.g K<=M Or K<=N.":"Please enter a positive integer number starting from 1",
	                                                    "Invalid Input" ,constant.ERROR_MESSAGE);

	                txtrows.setText("6");
	                txtcols.setText("7");
	                txtkwin.setText("4");
	                rows = 6;
	                cols = 7;
	                wins = 4;
	                On = true;
	            }
	            else
	            {
	                this.setVisible(On);    
	            }   
	        }
		} catch (Exception e) {
		}
	}

	String formatStr(String str)
	{
	    String retVal = str;
	    
	    if(str.substring(0,1).equals("_"))
	        retVal = str.substring(1);
	
	    return retVal;
	}
	void Options_WindowClosing_Interaction1(java.awt.event.WindowEvent event)
	{
		try {
			    this.dispose();
		} catch (Exception e) {
		}
	}


	class SymFocus extends java.awt.event.FocusAdapter
	{
		public void focusGained(java.awt.event.FocusEvent event)
		{
			Object object = event.getSource();
			if (object == txtrows)
				txtrows_focusGained(event);
			else if (object == txtcols)
				txtcols_focusGained(event);
			else if (object == txtkwin)
				txtkwin_focusGained(event);
		}

		public void focusLost(java.awt.event.FocusEvent event)
		{
			Object object = event.getSource();
			if (object == txtrows)
				txtrows_focusLost(event);
			else if (object == txtcols)
				txtcols_focusLost(event);
		}
	}

	void txtrows_focusLost(java.awt.event.FocusEvent event)
	{
		// to do: code goes here.
			 
		txtrows_focusLost_Interaction1(event);
	}

	void txtrows_focusLost_Interaction1(java.awt.event.FocusEvent event)
	{
		try {
			txtrows.setMaskedText(formatStr(txtrows.getText()));
		} catch (Exception e) {
		}
	}

	void txtcols_focusLost(java.awt.event.FocusEvent event)
	{
		// to do: code goes here.
			 
		txtcols_focusLost_Interaction1(event);
	}

	void txtcols_focusLost_Interaction1(java.awt.event.FocusEvent event)
	{
		try {
			txtcols.setMaskedText(formatStr(txtcols.getText()));
		} catch (Exception e) {
		}
	}

	void txtrows_focusGained(java.awt.event.FocusEvent event)
	{
		// to do: code goes here.
			 
		txtrows_focusGained_Interaction1(event);
	}

	void txtrows_focusGained_Interaction1(java.awt.event.FocusEvent event)
	{
		try {
			txtrows.selectAll();
		} catch (Exception e) {
		}
	}

	void txtcols_focusGained(java.awt.event.FocusEvent event)
	{
		// to do: code goes here.
			 
		txtcols_focusGained_Interaction1(event);
	}

	void txtcols_focusGained_Interaction1(java.awt.event.FocusEvent event)
	{
		try {
			txtcols.selectAll();
		} catch (Exception e) {
		}
	}

	void txtkwin_focusGained(java.awt.event.FocusEvent event)
	{
		// to do: code goes here.
			 
		txtkwin_focusGained_Interaction1(event);
	}

	void txtkwin_focusGained_Interaction1(java.awt.event.FocusEvent event)
	{
		try {
			txtkwin.selectAll();
		} catch (Exception e) {
		}
	}
}
