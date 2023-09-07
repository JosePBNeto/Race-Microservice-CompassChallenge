package mscars.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
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
    private String model;
    private PilotEntity pilot;
    private Date year;

}
