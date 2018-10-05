package sdcc2018.spring.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.stereotype.Component;
import com.tdunning.math.stats.TDigest;
import org.springframework.stereotype.Indexed;

import javax.validation.constraints.NotNull;
import java.util.Comparator;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Component
@Document(collection = "Intersection")
public class IntersectionGUI {


    @Id
    private String id;

    @Field("idIntersection")
    private int idIntersection;

    @Field("semaphoreList")
    private List<SemaphoreGUI> semaphoreList;

    public List<SemaphoreGUI> getSemaphoreList() {
        return semaphoreList;
    }

    public void setSemaphoreList(List<SemaphoreGUI> semaphoreList) {
        this.semaphoreList = semaphoreList;
    }

    public void updateIntersection(@NotNull IntersectionGUI newIntersection) {
        this.semaphoreList = newIntersection.semaphoreList;
    }

}
