package vasylts.blackjack.user.databaseworker.hibernateworker;
// Generated Oct 29, 2015 5:01:25 PM by Hibernate Tools 4.3.1



/**
 * Blackjackuser generated by hbm2java
 */
public class Blackjackuser  implements java.io.Serializable {


     private long id;
     private String login;
     private String password;
     private Long walletid;

    public Blackjackuser() {
    }

	
    public Blackjackuser(long id, String login) {
        this.id = id;
        this.login = login;
    }
    public Blackjackuser(long id, String login, String password, Long walletid) {
       this.id = id;
       this.login = login;
       this.password = password;
       this.walletid = walletid;
    }
   
    public long getId() {
        return this.id;
    }
    
    public void setId(long id) {
        this.id = id;
    }
    public String getLogin() {
        return this.login;
    }
    
    public void setLogin(String login) {
        this.login = login;
    }
    public String getPassword() {
        return this.password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    public Long getWalletid() {
        return this.walletid;
    }
    
    public void setWalletid(Long walletid) {
        this.walletid = walletid;
    }




}


