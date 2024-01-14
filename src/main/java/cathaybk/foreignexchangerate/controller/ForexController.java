package cathaybk.foreignexchangerate.controller;

import cathaybk.foreignexchangerate.entity.ForexData;
import cathaybk.foreignexchangerate.model.ForexRequest;
import cathaybk.foreignexchangerate.model.ForexResponse;
import cathaybk.foreignexchangerate.repository.ForexDataRepository;
import cathaybk.foreignexchangerate.service.ForexDataService;
import cathaybk.foreignexchangerate.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/api/forex")
public class ForexController {

    private final ForexDataRepository forexDataRepository;

    private final ForexDataService forexDataService;

    @Autowired
    public ForexController(ForexDataRepository forexDataRepository, ForexDataService forexDataService) {
        this.forexDataRepository = forexDataRepository;
        this.forexDataService = forexDataService;
    }

    @PostMapping("/findByDateBetween")
    public ForexResponse getForexData(@RequestBody ForexRequest request) {
        return forexDataService.getForexData(request);
    }

    @GetMapping("/insertInitialData")
    public ResponseEntity<String> insertInitialData() {
        return forexDataService.insertInitForexData();
    }
}
