package bomber.connectionhandler.json;

import bomber.connectionhandler.PlayerAction;
import bomber.games.model.GameObject;
import bomber.games.util.JsonHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public final class Json {
    private static Logger log = LoggerFactory.getLogger(Json.class);


    @Nullable
    public static PlayerAction jsonToPlayerAction(@NotNull final Integer playerId,
                                                  @NotNull final String json) {
        // Это для PLANT_BOMB и MOVE
        HandleInputJson handleInputJson = JsonHelper.fromJson(json, HandleInputJson.class);
        PlayerAction playerAction = convertToPlayerAction(playerId, handleInputJson);
        if (playerAction == null) {
            log.error("Не смогли конвертировать Json в PlayerAction");
            log.error("Клиент отправил не правильный тип json");
            return null;
        } else {
            return playerAction;
        }
    }

    @NotNull
    public static String possesToJson(@NotNull final Integer data) { // это для отправки json
        Possess possess = new Possess();
        possess.setData(data);
        return JsonHelper.toJson(possess);
    }


    @NotNull
    public static String replicaToJson(@NotNull final Map<Integer, ? extends GameObject> map) {
        //отправка Replic через JSON
        Replica replica = new Replica();
        DataReplica dataReplica = replica.getData();
        List<GameObject> list = new ArrayList<>(map.values());
        dataReplica.setObjects(list);
        return JsonHelper.toJson(replica);
    }

    @Nullable
    private static PlayerAction convertToPlayerAction(@NotNull final Integer playerId,
                                                      @NotNull final HandleInputJson handleInputJson) {
        PlayerAction playerAction = new PlayerAction();
        if (handleInputJson.getTopic() == Topic.MOVE) {
            playerAction.setId(playerId);
            playerAction.setType(handleInputJson.getData().getEventType());
            return playerAction;
        }

        if (handleInputJson.getTopic() == Topic.PLANT_BOMB) {
            playerAction.setId(playerId);
            playerAction.setType(PlayerAction.EventType.BOMB);
            return playerAction;
        }

        return null;
    }
}
