package am.itspace.authorbookrest.scheduler;

import am.itspace.authorbookcommon.entity.Currency;
import am.itspace.authorbookcommon.repository.CurrencyRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CurrencyScheduler {

    private final CurrencyRepository currencyRepository;
    private final RestTemplate restTemplate;

    @PostConstruct
    public void init() {
        syncCurrency();
    }

    @Scheduled(cron = "0 * * * * *")
    public void syncCurrency() {
        log.info("Currency Scheduler started at " + new Date());

        ResponseEntity<HashMap> exchange = restTemplate.exchange("https://cb.am/latest.json.php",
                HttpMethod.GET, null, HashMap.class);

        if (exchange.getStatusCode().is2xxSuccessful()) {
            HashMap<String, String> body = (HashMap<String, String>) exchange.getBody();
            double rub = Double.parseDouble(body.get("RUB"));
            double usd = Double.parseDouble(body.get("USD"));
            if (rub != 0 && usd != 0) {
//                currencyRepository.deleteAll();
                List<Currency> all = currencyRepository.findAll();
                Currency currency = Currency.builder()
                        .rub(rub)
                        .usd(usd)
                        .lastUpdatedDate(new Date())
                        .build();
                if (!all.isEmpty()) {
                    currency.setId(all.get(0).getId());
                }
                currencyRepository.save(currency);
                log.info("New currency saved. currency {}", currency);
            }
        }
        log.info("Currency Scheduler finished at " + new Date());

    }


}