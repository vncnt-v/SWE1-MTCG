import bif3.swe1.mtcg.GameManager;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GameManagerTest {
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
