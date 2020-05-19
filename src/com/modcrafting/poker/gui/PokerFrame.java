package com.modcrafting.poker.gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.AffineTransform;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.TitledBorder;

import com.modcrafting.poker.PokerCard;
import com.modcrafting.poker.PokerDeck;
import com.modcrafting.poker.PokerHand;

public class PokerFrame extends JFrame{
	private static final long serialVersionUID = 2662488563819600966L;
	static PokerFrame frame;
	PokerDeck deck;
	JPanel cardpanel;
	JPanel opponent;
	Color color = new Color(49, 132, 43);
	public PokerFrame(){
		super("Video Poker");
		frame = this;
		deck = new PokerDeck(this.getClass().getResource("/cards.png"));
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        final Dimension center = new Dimension((int) (screenSize.width * 0.75), (int) (screenSize.height * 0.75));
        final int x = (int) (center.width * 0.2);
        final int y = (int) (center.height * 0.2);
        this.setBounds(x, y, center.width, center.height);
        try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | 
				InstantiationException | 
				IllegalAccessException | 
				UnsupportedLookAndFeelException e) {
			e.printStackTrace();
			
		}
        cardpanel = new JPanel();
        cardpanel.setBorder(BorderFactory.createLoweredSoftBevelBorder());
        cardpanel.setBackground(color);
        opponent = new JPanel();
        opponent.setBorder(BorderFactory.createLoweredSoftBevelBorder());
        opponent.setBackground(color);
        
        dealCards(cardpanel, true);
        dealCards(opponent, false);
        this.getContentPane().add(new JSplitPane(JSplitPane.VERTICAL_SPLIT, cardpanel, opponent));
        //this.getContentPane().add(opponent);
        this.setVisible(true);
        
        
	}
	
	public PokerHand dealCards(JPanel cpanel, boolean show){
		PokerCard[] cards = deck.draw(5);
		for(int i=0;i<cards.length;i++){
			JPanel panel = new JPanel();
			TitledBorder border = new TitledBorder(
					BorderFactory.createLineBorder(Color.WHITE), 
					"Card: "+String.valueOf(i+1), 
					TitledBorder.DEFAULT_JUSTIFICATION, 
					TitledBorder.ABOVE_TOP, 
					Font.getFont(Font.SANS_SERIF), Color.WHITE);
			panel.setBorder(border);
			panel.setBackground(color);
			
			ImageIcon image = null;
			if(show){
				image = new ImageIcon(cards[i].getImage());
			}else{
				image = new ImageIcon(PokerCard.getBack());
			}
			JLabel label = new JLabel(image);
			label.setBorder(BorderFactory.createLoweredSoftBevelBorder());
			label.addMouseListener(new MListener(image.getImage())); //Draw method
			panel.add(label);
			cpanel.add(panel);			
		}
		return new PokerHand(cards);
	}
	
	public static PokerFrame getInstance(){
		return frame;
	}
	
	public class MListener implements MouseListener{
		Image image;
		public MListener(Image image) {
			this.image = image;
		}

		@Override
		public void mouseClicked(final MouseEvent event) {
			new Thread(new Runnable(){
				@Override
				public void run() {
					Container cad = ((Container) event.getSource());
					for(int i=0;i<89;i++){
						Graphics2D g2 = (Graphics2D) image.getGraphics().create();
						AffineTransform trans = new AffineTransform();
						trans.rotate(i, i, cad.getAlignmentX()+(cad.getWidth()/2), cad.getAlignmentY()+(cad.getHeight()/2));
						g2.transform(trans);
						cad.getGraphics().clearRect((int)cad.getAlignmentX(), (int)cad.getAlignmentY(), cad.getWidth(), cad.getHeight());
						cad.repaint();
						cad.print(g2);
						cad.repaint();
						sleep(50);
					}
				}

				private void sleep(int i) {
					long time = System.currentTimeMillis();
					while((time+i)>System.currentTimeMillis()){
						
					}
				}
			}).start();
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
		
	}
}
