package model;

/**
 * Created by s on 12.11.16.
 */
public class LeaderBoard {
    private Long id_user;
    private Long score;

    public LeaderBoard() { }

    public LeaderBoard(Long id_user, Long score){
        this.id_user=id_user;
        this.score=score;
    }

    public Long getId_user() {
        return id_user;
    }

    public void setId_user(Long id_user) {
        this.id_user=id_user;
    }

    public Long getScore() {
        return score;
    }

    public void setScore(Long score) {
        this.score = score;
    }

    @Override
    public String toString(){
        return "id_user = " + id_user+"; " + "score = "+score;
    }
}
