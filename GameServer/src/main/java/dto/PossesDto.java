package dto;

public class PossesDto {
    private String type = "POSSESS";
    private int data;

    public PossesDto(int playerId) {
        data = playerId;
    }
}
