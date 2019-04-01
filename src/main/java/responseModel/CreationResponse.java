package responseModel;

import java.io.Serializable;

public class CreationResponse implements Serializable {
    public Long id;

    public CreationResponse(Long id){
        this.id = id;
    }
}
