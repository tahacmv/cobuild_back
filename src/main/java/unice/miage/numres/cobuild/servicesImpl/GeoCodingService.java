package unice.miage.numres.cobuild.servicesImpl;

import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.web.util.UriUtils;

import lombok.RequiredArgsConstructor;
import unice.miage.numres.cobuild.requestModel.GeoCodeResult;
import unice.miage.numres.cobuild.requestModel.PositionStackResponse;

@Service
@RequiredArgsConstructor
public class GeoCodingService {

    @Value("${positionstack.api.key}")
    private String apiKey;

    @Value("${positionstack.api.url}")
    private String apiUrl;

    private final RestTemplate restTemplate = new RestTemplate();

    public GeoCodeResult geocodeAddress(String address) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(apiUrl)
        .queryParam("access_key", apiKey)
        .queryParam("query", address); // pass raw address

        URI uri = builder.build().encode().toUri(); // ✅ correct way
        System.out.println("[Geocoding] Request: " + uri);

        ResponseEntity<PositionStackResponse> response = restTemplate.exchange(
            uri, HttpMethod.GET, null, PositionStackResponse.class);

        List<GeoCodeResult> results = response.getBody().getData();

        if (results == null || results.isEmpty()) {
            throw new RuntimeException("Adresse non trouvée: " + address);
        }

        return results.get(0); // return the first match
    }
}
