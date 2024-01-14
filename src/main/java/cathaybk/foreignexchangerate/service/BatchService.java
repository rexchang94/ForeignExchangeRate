package cathaybk.foreignexchangerate.service;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

public interface BatchService {


    void fetchAndStoreForexData();
}
