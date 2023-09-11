package mscars.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Document(collection = "cars")
public class CarEntity {

    @Id
    private String id;
    private String brand;
    @Indexed(unique = true)
    private String model;
    @Indexed(unique = true)
    private PilotEntity pilot;
    private String year;

}
