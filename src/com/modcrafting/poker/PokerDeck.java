package com.modcrafting.poker;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.imageio.ImageIO;

import com.modcrafting.poker.PokerCard.Suit;
import com.modcrafting.poker.PokerCard.Value;

public class PokerDeck {
	List<PokerCard> deck = new ArrayList<PokerCard>();
	Random gen = new Random();
	BufferedImage in;
	int width;
	public PokerDeck(){
		for(int i=0;i<52;i++){
			Suit suit = Suit.fromInt((i/13)+1);
			Value val = Value.fromInt((i)+1);
			if(i>12 && i<26){
				val = Value.fromInt((i-13)+1);
			}else if(i>25 && i<39){
				val = Value.fromInt((i-26)+1);
			}else if(i>38){
				val = Value.fromInt((i-39)+1);
			}
			if(val!=null){
				deck.add(new PokerCard(suit,val,null));
			}
		}
	}
	public PokerDeck(URL url){
		try {
			in = ImageIO.read(url);
			width = in.getWidth()/52;
			for(int i=0;i<52;i++){
				Suit suit = Suit.fromInt((i/13)+1);
				Value val = Value.fromInt((i)+1);
				if(i>12 && i<26){
					val = Value.fromInt((i-13)+1);
				}else if(i>25 && i<39){
					val = Value.fromInt((i-26)+1);
				}else if(i>38){
					val = Value.fromInt((i-39)+1);
				}
				if(val!=null){
					deck.add(new PokerCard(suit,val,in.getSubimage(width*i, 0, width, in.getHeight())));
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public PokerCard[] draw(int amt){
		PokerCard[] hand = new PokerCard[amt];
		for(int i=0;i<hand.length;i++){
			hand[i] = deck.remove(gen.nextInt(deck.size()-1));
		}
		return hand;
	}
	
	public PokerCard getCard(Suit suit, Value value){
		int pos = value.i;
		if(suit != Suit.CLUB)
			pos += (suit.i-1)*13;
		return new PokerCard(suit,value,in.getSubimage(width*pos, 0, width, in.getHeight()));
	}
}
