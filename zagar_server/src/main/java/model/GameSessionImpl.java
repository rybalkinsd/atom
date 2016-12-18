package model;

import accountserver.api.Authentification;
import main.ApplicationContext;
import matchmaker.MatchMaker;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * @author apomosov
 */
public class GameSessionImpl implements GameSession {
    private final static Logger log = LogManager.getLogger(GameSessionImpl.class);
    private static final SequentialIDGenerator idGenerator = new SequentialIDGenerator();
    private final long id = idGenerator.next();
    @NotNull
    private final GameField field;
    @NotNull
    private final List<Player> players = new ArrayList<>();
    @NotNull
    private final FoodGenerator foodGenerator;
    @NotNull
    private final PlayerPlacer playerPlacer;
    @NotNull
    private final VirusGenerator virusGenerator;

    public GameSessionImpl(@NotNull FoodGenerator foodGenerator, @NotNull PlayerPlacer playerPlacer, @NotNull VirusGenerator virusGenerator,
                           @NotNull GameField gameField) {
        this.foodGenerator = foodGenerator;
        this.playerPlacer = playerPlacer;
        this.virusGenerator = virusGenerator;
        field = gameField;
        //virusGenerator.generate();
        Thread foodGenerationTask = new Thread(foodGenerator);
        foodGenerationTask.start();
    }

    @Override
    public void offerNewLocation(Player player, double dx, double dy){
        for(Cell cell: player.getCells()){
            double newX = cell.getLocation().getX() +
                    Math.signum(dx - cell.getLocation().getX()) * Math.pow(dx - cell.getLocation().getX(), 2/3)*
                    cell.getSpeed() / GameConstants.SERVER_FPS;
            double newY = cell.getLocation().getY() +
                    Math.signum(dy - cell.getLocation().getY()) * Math.pow(dy - cell.getLocation().getY(), 2/3)*
                    cell.getSpeed() / GameConstants.SERVER_FPS;
            if(newX - cell.getRadius() < 0 || newX + cell.getRadius() > field.getWidth() ||
                    newY - cell.getRadius() < 0 || newY + cell.getRadius() > field.getHeight()){
                return;
            } else {
                cell.setNewLocation(new Location(newX, newY));
            }
        }
    }

    @Override
    public void tick() {
        for(Player player: players){
            move(player);
            checkFoodCollisions(player);
        }
    }


    private void move(Player player){
        for(Cell cell: player.getCells()){
            cell.setLocation(cell.getNewLocation());
        }
    }

    private void checkFoodCollisions(Player player){
        for(Cell cell: player.getCells()) {
            double lowerX = cell.getLocation().getX() - cell.getRadius() * 10;
            double upperX = cell.getLocation().getX() + cell.getRadius() * 10;
            double lowerY = cell.getLocation().getY() - cell.getRadius() * 10;
            double upperY = cell.getLocation().getY() + cell.getRadius() * 10;
            for(Food food: field.getFoods()){
                double x = food.getLocation().getX();
                double y = food.getLocation().getY();
                if( x > lowerX && x < upperX && y > lowerY && y < upperY){
                    cell.setMass(cell.getMass() + GameConstants.FOOD_MASS);
                    field.getFoodsToRemove().add(food);
                    field.getFoods().remove(food);
                }
            }
        }
    }

    @Override
    public void join(@NotNull Player player) {
        players.add(player);
        this.playerPlacer.place(player);
    }

    @Override
    public void leave(@NotNull Player player) {
        log.info("Player " + player.getName() + " leaved");
        ApplicationContext.get(MatchMaker.class).removePlayerSession(player.getId());
        Authentification.LB.updateScore(player.getId(),0);
        Authentification.tokenDAO.delete(Authentification.tokenDAO.getTokenByUserId(player.getId()));
        players.remove(player);
    }

    @Override
    public List<Player> getPlayers() {
        return new ArrayList<>(players);
    }

    @Override
    public GameField getField() {
        return field;
    }

    @Override
    public String toString() {
        return "GameSessionImpl{" +
                "id=" + id +
                '}';
    }
}
