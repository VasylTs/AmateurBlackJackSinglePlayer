/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vasylts.blackjack.player;

/**
 *
 * @author VasylcTS
 */
public class NoSuchPlayerException extends RuntimeException {
    
    public NoSuchPlayerException(String msg) {
        super(msg);
    }
}
