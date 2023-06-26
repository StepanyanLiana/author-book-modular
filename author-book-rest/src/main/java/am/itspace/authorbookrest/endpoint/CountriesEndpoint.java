package am.itspace.authorbookrest.endpoint;

import am.itspace.authorbookcommon.dto.CountryListDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;


@RestController
@RequestMapping("/countries")
public class CountriesEndpoint {

    @Value("${countries.api.key}")
    private String countriesApiKey;

    @GetMapping
    public ResponseEntity<CountryListDto> getAll() {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<CountryListDto> exchange = restTemplate.exchange("https://restfulcountries.com/api/v1/countries",
                HttpMethod.GET, httpEntity(), CountryListDto.class);
        if (exchange.getStatusCode().is2xxSuccessful()) {
            return ResponseEntity.ok(exchange.getBody());
        }
        return ResponseEntity.notFound().build();
    }

    private HttpEntity httpEntity() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", "Bearer " + countriesApiKey);
        HttpEntity<Object> entity = new HttpEntity<>(httpHeaders);
        return entity;
    }
}