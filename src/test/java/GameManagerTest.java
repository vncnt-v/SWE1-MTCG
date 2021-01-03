import static org.junit.jupiter.api.Assertions.assertEquals;

public class GameManagerTest {

    /*@Test
    public void createOnlyOneInstance(){
        EloManager manager_1 = EloManager.getInstance();
        EloManager manager_2 = EloManager.getInstance();
        EloManager manager_3 = EloManager.getInstance();
        assertTrue(manager_1.registerUser("admin","123"));
        assertTrue(manager_2.registerUser("admin2","123"));
        assertFalse(manager_3.registerUser("admin","1234"));
        assertTrue(manager_1.deleteUser("admin","123"));
        assertTrue(manager_1.deleteUser("admin2","123"));
    }


    @Test
    void registerUser(){
        EloManager manager = EloManager.getInstance();
        assertTrue(manager.registerUser("admin","123"));
        assertTrue(manager.registerUser("admin2","123"));
        assertTrue(manager.registerUser("admin3","1234"));
        assertTrue(manager.deleteUser("admin","123"));
        assertTrue(manager.deleteUser("admin2","123"));
        assertTrue(manager.deleteUser("admin3","1234"));
    }

    @Test
    void registerUserFail(){
        EloManager manager = EloManager.getInstance();
        assertTrue(manager.registerUser("admin","123"));
        assertTrue(manager.registerUser("admin2","123"));
        assertFalse(manager.registerUser("admin","1234"));
        assertTrue(manager.deleteUser("admin","123"));
        assertTrue(manager.deleteUser("admin2","123"));
    }

    @Test
    void loginUser(){
        EloManager manager = EloManager.getInstance();
        assertTrue(manager.registerUser("admin","123"));
        assertTrue(manager.loginUser("admin","123"));
        assertTrue(manager.deleteUser("admin","123"));
    }

    @Test
    void loginUserFail(){
        EloManager manager = EloManager.getInstance();
        assertTrue(manager.registerUser("admin","123"));
        assertFalse(manager.loginUser("admin","1234"));
        assertFalse(manager.loginUser("admin2","1234"));
        assertFalse(manager.loginUser("admin2","1234"));
        assertTrue(manager.deleteUser("admin","123"));
    }

    @Test
    void deleteUserFail(){
        EloManager manager = EloManager.getInstance();
        assertTrue(manager.registerUser("admin","123"));
        assertFalse(manager.deleteUser("admin","1233"));
        assertFalse(manager.deleteUser("admin2","123"));
        assertTrue(manager.deleteUser("admin","123"));
    }*/
}
