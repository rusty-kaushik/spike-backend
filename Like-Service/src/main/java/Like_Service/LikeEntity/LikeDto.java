package Like_Service.LikeEntity;

import java.util.UUID;

public class LikeDto {
    private UUID id;
   private String userName;

    public LikeDto(UUID id, String userName) {
        this.id = id;
        this.userName = userName;

    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }


}
