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
public class SemaphoreGUI {


    @Field("id")
    private int idSem; //0,1,2,3

    @Field("longitude")
    private float longitude;

    @Field("latitude")
    private float latitude;

    @Field("green")
    private int green;

    @Field("red")
    private int red;

    @Field("saturation")
    private double saturation;//variabile per ogni semaforo

}
