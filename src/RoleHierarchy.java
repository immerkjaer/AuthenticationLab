package src;

public class RoleHierarchy{
    String name;
    RoleHierarchy superior;

    //New role subject of superior (In latice there is a lone downwards from superior to subject)
    public RoleHierarchy(String name, RoleHierarchy superior, String[] permissions){
        this.name = name;
        this.superior = superior;

    }

    public RoleHierarchy(String name, RoleHierarchy superior){
        this.name = name;
        this.superior = superior;
    }

    //New bottom starting point of latice. (When checking for permissions we are looking from the bottom up)
    public RoleHierarchy(String name, String[] permissions){
        this.name = name;

    }


    public boolean checkIfRoleHierarchyHasPermission(String role){
        if(role.equals(this.name)){
            return true;
        }
        if  (this.superior==null){
            return false;
        }


        return this.superior.checkIfRoleHierarchyHasPermission(role);

    }

    //from name only
    public RoleHierarchy(String name) {
        this.name = name;
    }

}