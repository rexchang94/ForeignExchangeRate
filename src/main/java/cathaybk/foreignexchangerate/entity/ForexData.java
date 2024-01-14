package cathaybk.foreignexchangerate.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;


@Data
@Entity
@Table(name = "FOREX_DATA")
public class ForexData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "DATE", nullable = false)
    private String date;

    @Column(name = "USD_NTD", nullable = false)
    private Double usdNtd;

    @Column(name="INSERT_DATE", insertable = false)
    private Date insertDate;


}

