package cathaybk.foreignexchangerate.service.impl;

import cathaybk.foreignexchangerate.service.BatchService;
import cathaybk.foreignexchangerate.service.ForexDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class BatchServiceImpl implements BatchService {

    private final ForexDataService forexDataService;

    @Autowired
    public BatchServiceImpl(ForexDataService forexDataService) {
        this.forexDataService = forexDataService;
    }

    @Override
    @Scheduled(cron = "0 0 18 * * ?")
    public void fetchAndStoreForexData() {
        System.out.println("FetchAndStoreForexData is Starting ...");
        forexDataService.fetchAndStoreForexData();
        System.out.println("FetchAndStoreForexData is Ending ...");
    }

}
