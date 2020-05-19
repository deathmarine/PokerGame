package com.modcrafting.poker;

import java.awt.Image;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.modcrafting.poker.gui.PokerFrame;

public class PokerCard {
	Suit suit;
	Value value;
	Image image;
	public PokerCard(Suit s, Value v, Image i){
		this.suit = s;
		this.value = v;
		this.image = i;
	}
	public Image getImage(){
		return image;
	}
	
	public Suit getSuit() {
		return suit;
	}
	
	public Value getValue(){
		return value;
	}
	
	public String toString(){
		return value.name()+" Of "+suit.name();
	}
	
	public static enum Suit {
	    SPADE(2), 
	    HEART(3), 
	    CLUB(1), 
	    DIAMOND(4);
	    int i;
	    Suit(int i){
	    	this.i = i;
	    }
	    int getValue(){
	    	return i;
	    }
	    static Suit fromInt(int i){
	    	for(Suit v:values()){
	    		if(v.getValue()==i)
	    			return v;
	    	}
	    	return null;
	    }
	    
	    char getCharacter(){
	    	switch(this){
			case CLUB:
				return '♣';
			case DIAMOND:
				return '♦';
			case HEART:
				return '♥';
			case SPADE:
				return '♠';	    	
	    	}
			return 0;
	    }
	}
	 
	public static enum Value {
	    TWO(2), 
	    THREE(3), 
	    FOUR(4), 
	    FIVE(5), 
	    SIX(6), 
	    SEVEN(7), 
	    EIGHT(8), 
	    NINE(9), 
	    TEN(10), 
	    JACK(11), 
	    QUEEN(12), 
	    KING(13), 
	    ACE(1);
	    
	    int i;
	    Value(int i){
	    	this.i = i;
	    }
	    int getValue(){
	    	return i;
	    }
	    
	    static Value fromInt(int in){
	    	for(Value v:values()){
	    		if(v.getValue()==in)
	    			return v;
	    	}
	    	return null;
	    }
	    String getCharacter(){
	    	switch(this){
			case ACE:
				return " A";
			case EIGHT:
				return " 8";
			case FIVE:
				return " 5";
			case FOUR:
				return " 4";
			case JACK:
				return " J";
			case KING:
				return " K";
			case NINE:
				return " 9";
			case QUEEN:
				return " Q";
			case SEVEN:
				return " 7";
			case SIX:
				return " 6";
			case TEN:
				return "10";
			case THREE:
				return " 3";
			case TWO:
				return " 2";
			default:
				break;	    	
	    	}
			return "";
	    }
	}
	
	public static Image getBack(){
		try {
			return ImageIO.read(PokerFrame.getInstance().getClass().getResource("/back.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
