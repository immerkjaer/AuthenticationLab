package src;

public class RoleHierarchy{
    String name;
    RoleHierarchy superior;

    //New role subject of superior (In latice there is a lone downwards from superior to subject)
    public RoleHierarchy(String name, RoleHierarchy superior, String[] permissions){

    }

    //New bottom starting point of latice. (When checking for permissions we are looking from the bottom up)
    public RoleHierarchy(String name, String[] permissions){

    }


    public boolean checkIfRoleHierarchyHasPermission(String role){

        if(role.equals(this.name)){
            return true;
        }
        if  (superior==null){
            return false;
        }


        return superior.checkIfRoleHierarchyHasPermission(role);
    }



}