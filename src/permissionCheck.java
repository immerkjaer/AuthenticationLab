package src;

import java.util.ArrayList;

public class permissionCheck {

    public static void main(String[] args) {

    }

    public boolean isPermitted(String name, String command){
        //Read Json to get role of person
        String role = getRole(name);

        //Read Json to get list og permitted commands for role
        ArrayList<String> permittedCommands = getPermissionsFromRole(role);

        //if command is in list, return true, otherwise return false
        for (String s : permittedCommands){
            if (s.equals(command)){
                return true;
            }
        }
        return false;
    }

    public String getRole(String name){
        //some Json thingy here to get the role of the person with name
        return "";
    }

    public ArrayList<String> getPermissionsFromRole(String role){
        ArrayList<String> permittedCommands = new ArrayList<String>();

        //some Json thingy to put permissions into Arraylist

        return permittedCommands;
    }

}
