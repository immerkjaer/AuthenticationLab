package src;

public class ImplCompute implements Compute {

    // TODO: Don't allow calling functions
    // that require authentication before verifying
    // that the user is in fact authenticated.
    // Simply use isUserAuthenticated() from passwordManager
    // once merged for checking
    // The boolean is a placeholder for the actual check
    boolean fakeAuthResult = true;
    public void printMsg(String msg) {
        if (fakeAuthResult){
            System.out.println(msg);
        } else {
            System.out.println("Access denied.");
        }
    }

    // TODO: This is a placeholder function
    // for until we merge all code from from our branches
    // Obviously, we should only send a hashed password
    // even if the server and connection is trusted.
    // Simply hashing it and calling the authorizeUser()
    // function from passwordManager should be all left to do here
    public boolean authenticate(String userId, string password){
        System.out.println("PLACEHOLDER FUNCTION: fake authentication of " + userId + "," + password + ". FIX AFTER CODE MERGE");
        return true;
    }
}
