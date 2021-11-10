package src;
import java.io.Serializable;
import java.time.*;
import java.util.*;

public class Ticket implements Serializable{
    LocalDateTime expireTime;
    Boolean active;
    String service;
    String sessionKey;
    String user;
    int secAdded = 10000;

    public Ticket(String user){
        this.sessionKey=UUID.randomUUID().toString();
        this.expireTime=LocalDateTime.now().plusSeconds(secAdded);
        this.service="printer";
        this.active=true;
        this.user=user;
    }
    public boolean isActive(){
        if (LocalDateTime.now().isAfter(this.expireTime)){
            this.active=false;
            return this.active;
        }
        return this.active;
    }


    @Override
    public int hashCode() {
        return (sessionKey.hashCode()*11+user.hashCode()*3+expireTime.hashCode()*7);
    }
    @Override
    public String toString(){
        return this.hashCode() +" "+user +" "+sessionKey+ " "+service; 
    }

    @Override
    public boolean equals(Object o) {
        
        if (this == o){
            return true;
        }
        if (o == null){
            
            return false;
        }
        Ticket oTicket = (Ticket) o;

        if (!this.sessionKey.equals(oTicket.sessionKey)){

            return false;
        }
        if (!this.user.equals( oTicket.user)){

            return false;
        }
        if (!this.expireTime.equals(expireTime)){

            return false;
        }
        return true;
    }



}
