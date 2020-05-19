package com.modcrafting.poker;

public class PokerHand implements Comparable<PokerHand> {
	private int[] suits, values;
	private HandType handType;
	private int[] cardSortedFrequency;
	private PokerCard[] cards;

	public PokerHand(PokerCard[] cards) {
		this.cards = cards;
		suits = new int[4];
		values = new int[13];
		cardSortedFrequency = new int[5];
		for (PokerCard card : cards) {
			++suits[card.getSuit().ordinal()];
			++values[card.getValue().ordinal()];
		}
		evalHandType();
	}

	private void evalHandType() {
		boolean isFlush = false, isStraight = false;
		for (int suit : suits) {
			if (suit == 5) {
				isFlush = true;
				break;
			}
		}

		int straightCount = 0;
		for (int value : values) {
			if (value == 1) {
				++straightCount;
				if (straightCount == 5) {
					isStraight = true;
					break;
				}
			} else {
				straightCount = 0;
			}
		}

		if (isFlush) {
			if (isStraight) {
				handType = HandType.STRAIGHTFLUSH;
			} else {
				handType = HandType.FLUSH;
			}
		} else if (isStraight) {
			handType = HandType.STRAIGHT;
		} else {
			int[] cardRepeats = new int[3];
			for (int value : values) {
				switch (value) {
				case 4:
					++cardRepeats[2];
					break;
				case 3:
					++cardRepeats[1];
					break;
				case 2:
					++cardRepeats[0];
					break;
				}
			}

			if (cardRepeats[2] == 1) {
				handType = HandType.FOUROFAKIND;
			} else if (cardRepeats[1] == 1) {
				if (cardRepeats[0] == 1) {
					handType = HandType.FULLHOUSE;
				} else {
					handType = HandType.THREEOFAKIND;
				}
			} else if (cardRepeats[0] == 2) {
				handType = HandType.TWOPAIRS;
			} else if (cardRepeats[0] == 1) {
				handType = HandType.ONEPAIR;
			} else {
				handType = HandType.HIGHCARD;
			}
		}
		cardFrequency();
	}

	private void cardFrequency() {
		int count = -1;
		for (int i = 12; i >= 0; --i) {
			if (values[i] == 4) {
				cardSortedFrequency[++count] = i;
			}
		}
		for (int i = 12; i >= 0; --i) {
			if (values[i] == 3) {
				cardSortedFrequency[++count] = i;
			}
		}
		for (int i = 12; i >= 0; --i) {
			if (values[i] == 2) {
				cardSortedFrequency[++count] = i;
			}
		}
		for (int i = 12; i >= 0; --i) {
			if (values[i] == 1) {
				cardSortedFrequency[++count] = i;
			}
		}
	}

	public HandType getHandType() {
		return handType;
	}

	public int[] cardComparable() {
		return cardSortedFrequency;
	}

	public int compareTo(PokerHand hand) {
		if (this.handType.ordinal() == hand.getHandType().ordinal()) {
			int[] otherHand = hand.cardComparable();
			for (int i = 0, len = otherHand.length; i < len; ++i) {
				if (cardSortedFrequency[i] > otherHand[i]) {
					return 1;
				} else if (cardSortedFrequency[i] < otherHand[i]) {
					return -1;
				}
			}
			return 0;
		} else {
			return this.handType.ordinal() > hand.getHandType().ordinal() ? 1
					: -1;
		}
	}

	public String coolOut() {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < cards.length; i++)
			sb.append(" ┌──────────┐");
		sb.append("\n");
		for (int i = 0; i < cards.length; i++) {
			PokerCard card = cards[i];
			sb.append(" │" + card.value.getCharacter()
					+ card.suit.getCharacter() + "       │");
		}
		sb.append("\n");
		for (int i = 0; i < cards.length; i++)
			sb.append(" │          │");
		sb.append("\n");
		for (int i = 0; i < cards.length; i++) {
			PokerCard card = cards[i];
			sb.append(" │     " + card.suit.getCharacter() + "    │");
		}
		sb.append("\n");
		for (int i = 0; i < cards.length; i++)
			sb.append(" │          │");
		sb.append("\n");
		for (int i = 0; i < cards.length; i++) {
			PokerCard card = cards[i];
			sb.append(" │      " + card.value.getCharacter()
					+ card.suit.getCharacter() + " │");
		}
		sb.append("\n");
		for (int i = 0; i < cards.length; i++)
			sb.append(" └──────────┘");
		sb.append("\n");
		for (int i = 0; i < cards.length; i++) {
			PokerCard card = cards[i];
			sb.append("│" + card.value + " Of " + card.suit + "S│");
		}
		sb.append("\n\n\n");
		sb.append("==============" + getHandType() + "==============");
		return sb.toString();
	}

	enum HandType {
		HIGHCARD, ONEPAIR, TWOPAIRS, THREEOFAKIND, STRAIGHT, FLUSH, FULLHOUSE, FOUROFAKIND, STRAIGHTFLUSH
	}
}
