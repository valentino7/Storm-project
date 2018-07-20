package sdcc2018.spring.domain;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.stereotype.Component;

@Getter
@Setter
@NoArgsConstructor
@Component
@Document(collection = "Semaphore")
public class Semaphore {

    @Field("id")
    private int id;

    @Field("longitude")
    private float longitude;

    @Field("latitude")
    private float latitude;

    private Sensor sensor;




}
