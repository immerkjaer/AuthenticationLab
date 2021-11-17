package src;

public class RoleHierarchy{
    String name;
    RoleHierarchy superior;
    boolean hasSubject;
    String[] permissions;

    //New role subject of superior (In latice there is a lone downwards from superior to subject)
    public RoleHierarchy(String name, RoleHierarchy superior, String[] permissions){
        this.name = name;
        this.superior = superior;
        this.permissions = permissions;
        superior.hasSubject = true;
    }

    public RoleHierarchy(String name, RoleHierarchy superior){
        this.name = name;
        this.superior = superior;
        superior.hasSubject = true;
    }

    //New bottom starting point of latice. (When checking for permissions we are looking from the bottom up)
    public RoleHierarchy(String name, String[] permissions){
        this.name = name;
        this.permissions = permissions;

    }

    //from name only
    public RoleHierarchy(String name){
        this.name = name;
    }

}