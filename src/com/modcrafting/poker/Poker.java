package com.modcrafting.poker;

import java.util.Scanner;
import javax.swing.SwingUtilities;
import com.modcrafting.poker.gui.PokerFrame;

public class Poker{
	static Poker poker;
	PokerDeck deck;
	
	public static void main(String[] args){
		//if(args.length>0){
			System.out.println("Poker 0.0 Alpha\nBy Deathmarine\n");
			int simple_econ = 1000;
			boolean not_dead = true;
			Scanner scan = new Scanner(System.in);
			while(not_dead){
				System.out.println("Wallet: "+simple_econ);
				System.out.println("Enter an amount to bet: ");
				int amt = 0;
				try{
					int i = Integer.parseInt(scan.nextLine());
					if(i>=simple_econ)
						i=simple_econ;
					amt = amt+i;
					simple_econ -= i; 
				}catch(NumberFormatException nfe){
					System.out.println("Invalid Entry!");
				}
				if(amt==0)
					continue;
				PokerDeck deck = new PokerDeck();
				PokerCard[] cards = deck.draw(5);
				System.out.println("=== Your cards: ===\n");
				PokerHand hand = new PokerHand(cards);
				System.out.println(hand.coolOut());
				System.out.println("\nYour Current Bet: "+amt
						+"\n\nType Fold or an Amount:");
				scan = new Scanner(System.in);
				String arg = scan.nextLine();
				boolean fold = false;
				if (arg.equalsIgnoreCase("fold")){
					fold = true;
				}else{
					int i = Integer.parseInt(arg);
					if(i>=simple_econ)
						i=simple_econ;
					amt = amt+i;
					simple_econ -= i; 
				}
				System.out.println("=== Opponents cards: ===\n");
				PokerHand hand1 = new PokerHand(deck.draw(5));
				System.out.println(hand1.coolOut());
				
				if(!fold){
					if(hand.compareTo(hand1)>0){
						int purse = amt*2;
						System.out.println("\nYou won! Amount:" + purse + "\n\n");
						simple_econ += purse; 
					}else{
						System.out.println("\nYou Lost! Amount:" + amt + "\n\n");
					}
				}else{
					System.out.println("\nYou Folded! Amount:" + amt + "\n\n");
				}
				if(simple_econ<=0){
					System.out.println("\nYou've Bankrupted!\n\n::Game Over::");
					not_dead = false;
				}
			}
			scan.close();
			System.exit(0);
			/*
		}else{
			SwingUtilities.invokeLater(new Runnable(){

				@Override
				public void run() {
					new PokerFrame();
					
				}
				
			});
		}*/
	}

	public static Poker getInstance() {
		return poker;
	}
	
	public PokerDeck getDeck(){
		return deck;
	}
}
