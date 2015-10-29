/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vasylts.blackjack.deck;

import java.util.NoSuchElementException;


/**
 *
 * @author VasylcTS
 */
public class NoCardInDeckException extends NoSuchElementException {
    public NoCardInDeckException() {
        super();
    }
    
    public NoCardInDeckException(String msg) {
        super(msg);
    }
}
