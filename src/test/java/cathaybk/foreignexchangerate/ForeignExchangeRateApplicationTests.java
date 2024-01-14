package cathaybk.foreignexchangerate;

import cathaybk.foreignexchangerate.entity.ForexData;
import cathaybk.foreignexchangerate.model.ForexDataDTO;
import cathaybk.foreignexchangerate.repository.ForexDataRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


@SpringBootTest
class ForeignExchangeRateApplicationTests {

    @Autowired
    private ForexDataRepository forexDataRepository;


    @Test
    void contextLoads() throws JsonProcessingException {
        RestTemplate restTemplate = new RestTemplate();
        String apiUrl = "https://openapi.taifex.com.tw/v1/DailyForeignExchangeRates";
        String json = restTemplate.getForObject(apiUrl, String.class);
        ObjectMapper objectMapper = new ObjectMapper();
        ModelMapper modelMapper = new ModelMapper();
        ForexDataDTO[] forexDataArray = objectMapper.readValue(json, ForexDataDTO[].class);
        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");

        ForexData[] retVal = modelMapper.map(forexDataArray, ForexData[].class);

        for (ForexData forexData : retVal) {
            LocalDate dataDate = LocalDate.parse(forexData.getDate(), formatter);
            if (dataDate.equals(today)) {
                forexDataRepository.save(forexData);
            }
        }


    }

}
