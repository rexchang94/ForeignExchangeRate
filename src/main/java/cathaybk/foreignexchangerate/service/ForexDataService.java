package cathaybk.foreignexchangerate.service;

import cathaybk.foreignexchangerate.model.ForexRequest;
import cathaybk.foreignexchangerate.model.ForexResponse;
import org.springframework.http.ResponseEntity;

public interface ForexDataService {

    boolean fetchAndStoreForexData();

    ForexResponse getForexData(ForexRequest request);

    ResponseEntity<String> insertInitForexData();
}
