package cathaybk.foreignexchangerate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import cathaybk.foreignexchangerate.entity.ForexData;
import cathaybk.foreignexchangerate.enums.ApiUrl;
import cathaybk.foreignexchangerate.enums.ErrorCode;
import cathaybk.foreignexchangerate.model.ForexRequest;
import cathaybk.foreignexchangerate.model.ForexResponse;
import cathaybk.foreignexchangerate.repository.ForexDataRepository;
import cathaybk.foreignexchangerate.service.impl.ForexDataServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class ForexDataServiceImplTest {

    @Mock
    private ForexDataRepository forexDataRepository;

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private ForexDataServiceImpl forexDataService;


    @Test
    public void testFetchAndStoreForexData() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        //Monday to Friday
//        String jsonResponse = "["
//                + "{"
//                + "\"Date\":\"" + LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")) + "\","
//                + "\"USD/NTD\":31.475,"
//                + "\"RMB/NTD\":4.401558,"
//                + "\"EUR/USD\":1.0899,"
//                + "\"USD/JPY\":148.195,"
//                + "\"GBP/USD\":1.2649,"
//                + "\"AUD/USD\":0.6613,"
//                + "\"USD/HKD\":7.81065,"
//                + "\"USD/RMB\":7.1509,"
//                + "\"USD/ZAR\":18.8502,"
//                + "\"NZD/USD\":0.6163"
//                + "}"
//                + "]";
        //Weekend
        String fixedDate = "20240112"; // 假設這是一個工作日
        String jsonResponse = "["
                + "{"
                + "\"Date\":\"" + fixedDate + "\","
                + "\"USD/NTD\":31.475,"
                + "\"RMB/NTD\":4.401558,"
                + "\"EUR/USD\":1.0899,"
                + "\"USD/JPY\":148.195,"
                + "\"GBP/USD\":1.2649,"
                + "\"AUD/USD\":0.6613,"
                + "\"USD/HKD\":7.81065,"
                + "\"USD/RMB\":7.1509,"
                + "\"USD/ZAR\":18.8502,"
                + "\"NZD/USD\":0.6163"
                + "}"
                + "]";
        Mockito.lenient().when(restTemplate.getForObject(ApiUrl.FOREX.getUrl(), String.class)).thenReturn(jsonResponse);
        boolean result = forexDataService.fetchAndStoreForexData();
        if (result) {
            verify(forexDataRepository).save(argThat(forexData ->
                    LocalDate.parse(forexData.getDate(), formatter).equals(LocalDate.now())
            ));
        } else {
            verify(forexDataRepository, never()).save(any(ForexData.class));
        }


    }

    @Test
    public void testGetForexData_Success() {
        ForexRequest request = new ForexRequest("2023/01/01", "2023/01/10", "usd");
        List<ForexData> mockForexDataList = Arrays.asList(new ForexData());
        when(forexDataRepository.findByDateBetween(anyString(), anyString())).thenReturn(mockForexDataList);
        ForexResponse response = forexDataService.getForexData(request);
        assertEquals(ErrorCode.SUCCESS.getCode(), response.getError().getCode());
        assertFalse(response.getCurrency().isEmpty());
    }

    @Test
    public void testGetForexData_InvalidDate() {
        ForexRequest request = new ForexRequest("invalid-date", "2023-01-31", "usd");
        ForexResponse response = forexDataService.getForexData(request);
        assertEquals("E001", response.getError().getCode());
        assertNull(response.getCurrency());
    }
}

