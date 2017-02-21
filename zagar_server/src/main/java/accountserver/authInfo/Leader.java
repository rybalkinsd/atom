package accountserver.authInfo;

/**
 * Created by Max on 07.11.2016.
 */
public class Leader {
    private int id;
    private int points;

    public Leader setPts(int pts) {
        this.points = pts;
        return this;
    }

    public Leader setId(int id){
        this.id = id;
        return this;
    }

    public int getId(){
        return id;
    }

    public int getPoints(){
        return points;
    }

    public String toJSON(){
        //User JUser = Authentification.userDAO.getUserById(id);
        //return "\"" + JUser.getLogin()+ "\": "+this.points;
        return  this.id + " : " + this.points;
    }
}
