package cathaybk.foreignexchangerate.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ForexRequest {

    private String startDate;

    private String endDate;

    private String currency;

}
