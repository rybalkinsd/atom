package ru.atom.websocket.model;

/**
 * Created by BBPax on 14.05.17.
 */
public enum Action {
    MOVE_UP {

        @Override
        public void applyAction(GameSession gameSession, int pawnId) {
            gameSession.movePawn(pawnId, Movable.Direction.UP);
        }
    },
    MOVE_DOWN {

        @Override
        public void applyAction(GameSession gameSession, int pawnId) {
            gameSession.movePawn(pawnId, Movable.Direction.DOWN);
        }
    },
    MOVE_LEFT {

        @Override
        public void applyAction(GameSession gameSession, int pawnId) {
            gameSession.movePawn(pawnId, Movable.Direction.LEFT);
        }
    },
    MOVE_RIGHT {

        @Override
        public void applyAction(GameSession gameSession, int pawnId) {
            gameSession.movePawn(pawnId, Movable.Direction.RIGHT);
        }
    },
    BOMB_PLANT {
        @Override
        public void applyAction(GameSession gameSession, int pawnId) {
            gameSession.plantBomb(pawnId);
        }
    },
    DIE {
        @Override
        public void applyAction(GameSession gameSession, int pawnId) {
            gameSession.removePawn(pawnId);
        }
    };

    public void applyAction(GameSession gameSession, int pawnId) {
        System.out.println("here I will do nothing");
    }
}
