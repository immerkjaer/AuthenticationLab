package src;
import java.io.Serializable;
import java.time.*;
import java.util.*;

public class Ticket implements Serializable{
    LocalDateTime expireTime;
    Boolean active;
    String service;
    String sessionKey;
    int secAdded = 20;

    public Ticket(){
        this.sessionKey=UUID.randomUUID().toString();
        this.expireTime=LocalDateTime.now().plusSeconds(secAdded);
        this.service="printer";
        this.active=true;
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
        return sessionKey.hashCode();
    }
    @Override
    public String toString(){
        return this.hashCode() +" "+service +" "+sessionKey; 
    }


}
