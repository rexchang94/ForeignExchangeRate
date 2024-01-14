package cathaybk.foreignexchangerate.service.impl;

import cathaybk.foreignexchangerate.entity.ForexData;
import cathaybk.foreignexchangerate.enums.ApiUrl;
import cathaybk.foreignexchangerate.enums.ErrorCode;
import cathaybk.foreignexchangerate.model.ForexDataDTO;
import cathaybk.foreignexchangerate.model.ForexRequest;
import cathaybk.foreignexchangerate.model.ForexResponse;
import cathaybk.foreignexchangerate.repository.ForexDataRepository;
import cathaybk.foreignexchangerate.service.ForexDataService;
import cathaybk.foreignexchangerate.util.DateUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ForexDataServiceImpl implements ForexDataService {
    private final ForexDataRepository forexDataRepository;


    public ForexDataServiceImpl(ForexDataRepository forexDataRepository) {
        this.forexDataRepository = forexDataRepository;

    }


    @Override
    public boolean fetchAndStoreForexData() {
        RestTemplate restTemplate = new RestTemplate();
        String json = restTemplate.getForObject(ApiUrl.FOREX.getUrl(), String.class);
        ObjectMapper objectMapper = new ObjectMapper();
        ModelMapper modelMapper = new ModelMapper();
        ForexDataDTO[] forexDataArray = new ForexDataDTO[0];
        try {
            forexDataArray = objectMapper.readValue(json, ForexDataDTO[].class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");

        ForexData[] retVal = modelMapper.map(forexDataArray, ForexData[].class);
        boolean dataInserted = false;
        for (ForexData forexData : retVal) {
            LocalDate dataDate = LocalDate.parse(forexData.getDate(), formatter);
            if (dataDate.equals(today)) {
                forexDataRepository.save(forexData);
                dataInserted = true;
            }
        }
        return dataInserted;
    }


    @Override
    public ForexResponse getForexData(ForexRequest request) {
        LocalDate oneYearAgo = LocalDate.now().minusYears(1);
        LocalDate yesterday = LocalDate.now().minusDays(1);
        LocalDate startDate, endDate;
        try {
            startDate = DateUtil.parseAndValidateDate(request.getStartDate(), oneYearAgo, yesterday);
            endDate = DateUtil.parseAndValidateDate(request.getEndDate(), oneYearAgo, yesterday);
        } catch (DateTimeParseException e) {
            return new ForexResponse(new ForexResponse.ErrorInfo("E001", e.getMessage()));
        }

        String st = DateUtil.convertDateFormat(startDate.toString(), "yyyy-MM-dd", "yyyyMMdd");
        String ed = DateUtil.convertDateFormat(endDate.toString(), "yyyy-MM-dd", "yyyyMMdd");

        List<ForexData> forexDataList = forexDataRepository.findByDateBetween(st, ed);

        List<ForexResponse.CurrencyData> currencyDataList = forexDataList.stream()
                .map(data -> new ForexResponse.CurrencyData(data.getDate(), data.getUsdNtd()))
                .collect(Collectors.toList());
        return new ForexResponse(new ForexResponse.ErrorInfo(ErrorCode.SUCCESS.getCode(), ErrorCode.SUCCESS.getMessage()), currencyDataList);
    }

    @Override
    public ResponseEntity<String> insertInitForexData() {
        RestTemplate restTemplate = new RestTemplate();
        String json = restTemplate.getForObject(ApiUrl.FOREX.getUrl(), String.class);
        ObjectMapper objectMapper = new ObjectMapper();
        ModelMapper modelMapper = new ModelMapper();
        ForexDataDTO[] forexDataArray = new ForexDataDTO[0];
        try {
            forexDataArray = objectMapper.readValue(json, ForexDataDTO[].class);
            ForexData[] retVal = modelMapper.map(forexDataArray, ForexData[].class);
            forexDataRepository.saveAll(Arrays.asList(retVal));
            return ResponseEntity.ok("初始資料新增成功");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("資料新增失敗: " + e.getMessage());

        }


    }

}

