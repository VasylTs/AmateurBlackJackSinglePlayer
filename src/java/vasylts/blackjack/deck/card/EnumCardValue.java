/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vasylts.blackjack.deck.card;

/**
 * Enum that describes possible card rank. From Two to Ace. There is no Joker in
 * this enum
 * <p>
 * @author VasylcTS
 */
public enum EnumCardValue {

    TWO(2),
    THREE(3),
    FOUR(4),
    FIVE(5),
    SIX(6),
    SEVEN(7),
    EIGHT(8),
    NINE(9),
    TEN(10),
    JACK(10),
    QUEEN(10),
    KING(10),
    ACE(11);

    private final int scoreVal;

    EnumCardValue(int scoreValue) {
        scoreVal = scoreValue;
    }

    public int getScoreValue() {
        return scoreVal;
    }

    public static int getAceSecondaryScoreValue() {
        return 1; // Ace can be calculated like 11 and 1
    }
}
