
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDesktopPane;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.UIManager;
 
public class ButtonAction extends JFrame {
 
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	static String[] tokens; 
	static int radioOption = 0;
	static BigInteger[] bigElements;
	public static boolean flag = false; 
	
	
	private static void createAndShowGUI()  
	{
 
        final JFrame frame1 = new JFrame("Problem podzia³u zbioru");
        frame1.setSize(600, 550);
        frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame1.setVisible(true);
        JPanel panel = new JPanel(new GridBagLayout());
        frame1.getContentPane().add(panel, BorderLayout.NORTH);
        GridBagConstraints c = new GridBagConstraints();
        Font font = new Font("Courier", Font.BOLD,12);

       
        JLabel label1 = new JLabel("Iloœæ pokoleñ");
        label1.setFont(font);
        c.gridx = 1; 
        c.gridy = 2;
        c.insets = new Insets(10,10,0,10);
        panel.add(label1, c);
        final JTextField text1 = new JTextField(10);
        text1.setText("100");
        c.gridx = 1; 
        c.gridy = 3;
        panel.add(text1, c);
        
        
        
        JLabel label2 = new JLabel("Liczebnoœæ populacji");
        label2.setFont(font);
        c.gridx = 1; 
        c.gridy = 4;
        panel.add(label2, c);
        final JTextField text2 = new JTextField(10);
        text2.setText("200");
        c.gridx = 1; 
        c.gridy = 5;
        panel.add(text2, c);
        
        JLabel label3 = new JLabel("Iloœæ zbiorów");
        label3.setFont(font);
        c.gridx = 1; 
        c.gridy = 6;
        panel.add(label3, c);
        final JTextField text3 = new JTextField(10);
        text3.setText("3");
        c.gridx = 1; 
        c.gridy = 7;
        panel.add(text3, c);
        
        JLabel label4 = new JLabel("Wielkoœæ b³êdu");
        label4.setFont(font);
        c.gridx = 1; 
        c.gridy = 8;
        panel.add(label4, c);
        final JTextField text4 = new JTextField(10);
        text4.setText("5");
        c.gridx = 1; 
        c.gridy = 9;
        panel.add(text4, c);
        
        JLabel label5 = new JLabel("% osobników prze¿ywaj¹cych pokolenie");
        label5.setFont(font);
        c.gridx = 1; 
        c.gridy = 10;
        panel.add(label5, c);
        final JTextField text5 = new JTextField(10);
        text5.setText("5");
        c.gridx = 1; 
        c.gridy = 11;
        panel.add(text5, c);
        
        final JButton button1 = new JButton("Generuj");
        button1.setPreferredSize(new Dimension(150, 90));
        c.gridx = 1; 
        c.gridy = 15;
        panel.add(button1, c);
        button1.setEnabled(false);
        
        
        JRadioButton radio1 = new JRadioButton("Losowy");
        c.gridx = 0; 
        c.gridy = 3;
        panel.add(radio1, c);
        
        
        JRadioButton radio2 = new JRadioButton("Rêczny");
        c.gridx = 0; 
        c.gridy = 4;
        panel.add(radio2, c);
        
        
        JRadioButton radio3 = new JRadioButton("Z pliku");
        c.gridx = 0; 
        c.gridy = 5;
        panel.add(radio3, c);
        
        ButtonGroup bg1 = new ButtonGroup( );
        bg1.add(radio1);
        bg1.add(radio2);
        bg1.add(radio3);
        
        
        final JLabel label1a = new JLabel("Liczebnoœæ zbioru");
        label1a.setFont(font);
        c.gridx = 0; 
        c.gridy = 6;
        c.insets = new Insets(10,10,0,10);
        panel.add(label1a, c);
        final JTextField text1a = new JTextField(10);
        text1a.setText("60");
        c.gridx = 0; 
        c.gridy = 7;
        panel.add(text1a, c);
        
        final JLabel label2a = new JLabel("Minimalna wartoœæ");
        label2a.setFont(font);
        c.gridx = 0; 
        c.gridy = 8;
        panel.add(label2a, c);
        final JTextField text2a = new JTextField(10);
        text2a.setText("0");
        c.gridx = 0; 
        c.gridy = 9;
        panel.add(text2a, c);
        
        final JLabel label3a = new JLabel("Maksymalna wartoœæ");
        label3a.setFont(font);
        c.gridx = 0; 
        c.gridy = 10;
        panel.add(label3a, c);
        final JTextField text3a = new JTextField(10);
        text3a.setText("100");
        c.gridx = 0; 
        c.gridy = 11;
        panel.add(text3a, c);
        
        final JLabel label4a = new JLabel("Genotyp");
        c.gridx = 0; 
        c.gridy = 2;
        panel.add(label4a, c);
        label4a.setFont(font);
        
        text1a.setEnabled(false);
        text2a.setEnabled(false);
        text3a.setEnabled(false);
        
        
        
        
        final JTextField text6 = new JTextField(25);
        text6.setPreferredSize(new Dimension(400, 30));
        c.gridx = 0; 
        c.gridy = 15;
        panel.add(text6, c);
        
        final JButton button2 = new JButton("Browse");
        button2.setEnabled(false);
        c.gridx = 0; 
        c.gridy = 14;
        panel.add(button2, c);
        
        
        
        //Add action listener to button
        button1.addActionListener(new ActionListener() 
        {
        	
            public void actionPerformed(ActionEvent e)
            {
            		int gen = Integer.parseInt(text1.getText());
            		int ind = Integer.parseInt(text2.getText());        			
            		int subs = Integer.parseInt(text3.getText());
            		int err = Integer.parseInt(text4.getText());
            		int surv = Integer.parseInt(text5.getText());
            		if(gen < 1 || ind < 2 || subs < 1 || err <= 0 || surv < 0 || surv > 100)
            		{
            			final JDesktopPane desk = new JDesktopPane();
            			JDialog.setDefaultLookAndFeelDecorated(true);
            			JOptionPane.showInternalMessageDialog(desk, "information", "information", JOptionPane.INFORMATION_MESSAGE);
            		}
            		else
            		{
            			
            			/*if(tokens.length-Integer.parseInt(text3.getText()) > -1 )
            			{
            				text3.setText(Integer.toString(Integer.parseInt(text3.getText())-1));
            				subs = Integer.parseInt(text3.getText());
            			}*/
   		
	            		Settings s = new Settings(gen, ind, subs, err, surv, bigElements);
	            		String[] args = {};
	            		new Main(s, bigElements).main(args);
	            		new Chart();
	            		wypiszDoPliku(Settings.BEST_INDS.listGen.get(0));
	            		Settings.BEST_INDS.listGen.clear();
            		}
            }
        }); 
       
        button2.addActionListener(new ActionListener() {
          	 
        	public void actionPerformed(ActionEvent evt) 
        	{
        		
        		switch(radioOption)
        		{
        		case 0:
        		{
        			
        		}
        		case 1:
        		{
        			
        			if(Integer.parseInt(text3a.getText()) < Integer.parseInt(text2a.getText()))
        			{
        				JOptionPane.showMessageDialog(frame1,
        					    "Popraw maksymaln¹ wartoœæ.");
        				text2a.setText("0");
        				text3a.setText("100");
        			}
        			else{
        			Random rng = new Random(); // Ideally just create one instance globally
                	// Note: use LinkedHashSet to maintain insertion order
                	ArrayList<Integer> tablica = new ArrayList<Integer>();
                	for(int z = 0; z < Integer.parseInt(text1a.getText()); ++z)
                	{
                		while(true)
                		{
                			int wylosowana = rng.nextInt( (Integer.parseInt(text3a.getText()) - Integer.parseInt( text2a.getText() ) ) + 1) + Integer.parseInt(text2a.getText());
    	            		boolean znaleziono = false;
    	            		for(int k = 0; k < z; ++k)
    	            		{
    	            			if(tablica.get(k) == wylosowana)
    	            			{
    	            				znaleziono = true;
    	            				break;
    	            			}
    	            		}
    	            		
    	            		if(!znaleziono)
                			{
                				tablica.add(wylosowana);
                				break;
                			}
                		}
                	}
                	
                	String everything = "";
                	for(int i = 0; i < tablica.size(); i++)
        			{
        				everything += Integer.toString(tablica.get(i)) + " ";
        			}
        			
        			tokens = everything.split(" ");
        			
        			bigElements = new BigInteger[tokens.length];
            		for(int i = 0; i < tokens.length; i++)
            			bigElements[i] = new BigInteger(String.valueOf(tokens[i]));
        			button1.setEnabled(true);
        			}
        		}
        		break;
        		case 2:
        		{
        			String everything = text6.getText();        	       
            		tokens = everything.split(" ");
            		bigElements = new BigInteger[tokens.length];
            		for(int i = 0; i < tokens.length; i++)
            			bigElements[i] = new BigInteger(String.valueOf(tokens[i]));
            		button1.setEnabled(true);
            		
            		System.out.println(text6.getText());
        		}
        		break;
        		case 3:
        		{
        			JFileChooser chooser= new JFileChooser("D:\\workspace\\Podzia³_zbioru\\");
            		int choice = chooser.showOpenDialog(button2);
            		if (choice != JFileChooser.APPROVE_OPTION) return;
            		File chosenFile = chooser.getSelectedFile();
            		text6.setText(chosenFile.getAbsolutePath());
            		try(BufferedReader br = new BufferedReader(new FileReader(chosenFile.getAbsolutePath()))) 
            		{
            	        StringBuilder sb = new StringBuilder();
            	        String line = br.readLine();

            	        while (line != null) 
            	        {
            	            sb.append(line);
            	            sb.append(System.lineSeparator());
            	            line = br.readLine();
            	        }
            	        String everything = sb.toString();        	       
                		tokens = everything.split(" ");
                		button1.setEnabled(true);        	        
            	    } catch (FileNotFoundException e) 
            	    {
    					e.printStackTrace();
    				} catch (IOException e) 
    				{
    					e.printStackTrace();
    				}
            		
            		bigElements = new BigInteger[tokens.length-1];
            		for(int i = 0; i < tokens.length-1; i++)
            			bigElements[i] = new BigInteger(String.valueOf(tokens[i]));
        			
        		}
        			
        		}
        		
        		
            }

        }); 
        
   
    
	
	radio1.addActionListener(new ActionListener() {
		
        public void actionPerformed(ActionEvent e) {
        	
        	
        	button2.setEnabled(true);
            button2.setText("Generuj liczby");
            text1a.setEnabled(true);
            text2a.setEnabled(true);
            text3a.setEnabled(true);
            
           
            radioOption = 1;
            System.out.println(radioOption);
        }
    });
	
	radio2.addActionListener(new ActionListener() {
		
        public void actionPerformed(ActionEvent e) {
            button2.setEnabled(true);
            button2.setText("Wczytaj");
            text1a.setEnabled(false);
            text2a.setEnabled(false);
            text3a.setEnabled(false);
            
            
            radioOption = 2;
            System.out.println(radioOption);
        }
    });
	
	radio3.addActionListener(new ActionListener() {
		
        public void actionPerformed(ActionEvent e) {
        	button2.setEnabled(true);
            button2.setText("Browse");
            text1a.setEnabled(false);
            text2a.setEnabled(false);
            text3a.setEnabled(false);
            
            
            
            radioOption = 3;
            System.out.println(radioOption);
        }
    });

	}
	
 
	static void wypiszDoPliku(Individual ind)
	{
		try 
		{
			PrintWriter pisacz = null;
			pisacz  = new PrintWriter(new FileWriter("Wynik.txt"));
			for(int i = 0; i < ind.listInd.size(); i++)
				pisacz.println(ind.listInd.get(i) + "     sum= " + ind.sumSet(i));
			pisacz.println("Idealny zbiór: " + Settings.IDEAL_SET);
			pisacz.println(ind.rank);
			pisacz.close();
		} 
		catch (IOException e) 
		{
			System.out.println("B³¹d. Nie znaleziono pliku.");
		} 
	}
	
 
    public static void main(String[] args) 
    {
    	
    	try 
        {
          //UIManager.setLookAndFeel("LookAndFeel");
          //UIManager.setLookAndFeel("de.javasoft.plaf.synthetica.SyntheticaBlackEyeLookAndFeel");
        } 
        catch (Exception e) 
        {
          e.printStackTrace();
        }
    	
        javax.swing.SwingUtilities.invokeLater(new Runnable() 
        {
        	
        	
            public void run() {
                createAndShowGUI();
            }
        });
    }
}
