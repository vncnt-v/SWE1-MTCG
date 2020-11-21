import bif3.swe1.mtcg.GameManager;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class GameManagerTest {

    @Test
    public void createOnlyOneInstance(){
        GameManager manager_1 = GameManager.getInstance();
        GameManager manager_2 = GameManager.getInstance();
        GameManager manager_3 = GameManager.getInstance();
        assertTrue(manager_1.registerUser("admin","123"));
        assertTrue(manager_2.registerUser("admin2","123"));
        assertFalse(manager_3.registerUser("admin","1234"));
        assertTrue(manager_1.deleteUser("admin","123"));
        assertTrue(manager_1.deleteUser("admin2","123"));
    }


    @Test
    void registerUser(){
        GameManager manager = GameManager.getInstance();
        assertTrue(manager.registerUser("admin","123"));
        assertTrue(manager.registerUser("admin2","123"));
        assertTrue(manager.registerUser("admin3","1234"));
        assertTrue(manager.deleteUser("admin","123"));
        assertTrue(manager.deleteUser("admin2","123"));
        assertTrue(manager.deleteUser("admin3","1234"));
    }

    @Test
    void registerUserFail(){
        GameManager manager = GameManager.getInstance();
        assertTrue(manager.registerUser("admin","123"));
        assertTrue(manager.registerUser("admin2","123"));
        assertFalse(manager.registerUser("admin","1234"));
        assertTrue(manager.deleteUser("admin","123"));
        assertTrue(manager.deleteUser("admin2","123"));
    }

    @Test
    void loginUser(){
        GameManager manager = GameManager.getInstance();
        assertTrue(manager.registerUser("admin","123"));
        assertTrue(manager.loginUser("admin","123"));
        assertTrue(manager.deleteUser("admin","123"));
    }

    @Test
    void loginUserFail(){
        GameManager manager = GameManager.getInstance();
        assertTrue(manager.registerUser("admin","123"));
        assertFalse(manager.loginUser("admin","1234"));
        assertFalse(manager.loginUser("admin2","1234"));
        assertFalse(manager.loginUser("admin2","1234"));
        assertTrue(manager.deleteUser("admin","123"));
    }

    @Test
    void deleteUserFail(){
        GameManager manager = GameManager.getInstance();
        assertTrue(manager.registerUser("admin","123"));
        assertFalse(manager.deleteUser("admin","1233"));
        assertFalse(manager.deleteUser("admin2","123"));
        assertTrue(manager.deleteUser("admin","123"));
    }
}
