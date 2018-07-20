package sdcc2018.spring.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Field;

@Getter
@Setter
@NoArgsConstructor
public class DBReference {

    @Field("id")
    private String id;

    @Field("ref")
    private String ref;

    public DBReference(String id, String ref) {
        this.id = id;
        this.ref = ref;
    }
}

