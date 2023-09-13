package msraces.entities;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Track {
    @NotBlank(message = "Track name must not be blank")
    private String name;
    @NotBlank(message = "Country must not be blank")
    private String country;
    @PastOrPresent(message = "Date must be in the past or present")
    private Date date;
}
