package bif3.swe1.mtcg;

public class EloManager {

    private static EloManager single_instance = null;

    public static EloManager getInstance()
    {
        if (single_instance == null) {
            single_instance = new EloManager();
        }
        return single_instance;
    }

    public String getEloScores(){
        // ToDo Database
        return "";
    }

    public String getEloUsername(String username){
        // ToDo Database
        return "";
    }

    public void setEloUsername(String username, boolean victory){
        // ToDo Database
        if (victory){

        } else {

        }
    }
}
