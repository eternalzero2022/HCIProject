package com.njuse.battlerankbackend;


import com.njuse.battlerankbackend.vo.*;
import org.apache.hc.client5.http.cookie.BasicCookieStore;
import org.apache.hc.client5.http.cookie.CookieStore;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BaseUtilTest {

    @Autowired
    protected TestRestTemplate restTemplate;

    private CookieStore cookieStore;

    @BeforeEach
    public void setUp() {
        cookieStore = new BasicCookieStore();

        RestTemplateBuilder builder = new RestTemplateBuilder();
        CloseableHttpClient httpClient = HttpClients.custom()
                .setDefaultCookieStore(cookieStore)
                .build();

        restTemplate.getRestTemplate().setRequestFactory(new HttpComponentsClientHttpRequestFactory(httpClient));
    }

    protected Boolean login(String phone, String password) {
        UserVO user = new UserVO();
        user.setPhone(phone);
        user.setPassword(password);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        HttpEntity<UserVO> requestEntity = new HttpEntity<>(user, headers);

        ResponseEntity<Boolean> response = restTemplate.exchange("/api/users/login", HttpMethod.POST, requestEntity, Boolean.class);

        assert(response.getStatusCodeValue() == 200);
        assert(response.getBody().booleanValue());

        return true;

    }

    protected Boolean register(String phone, String password) {
        UserVO user = new UserVO();
        user.setPhone(phone);
        user.setPassword(password);
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        HttpEntity<UserVO> requestEntity = new HttpEntity<>(user, headers);

        ResponseEntity<Boolean> response = restTemplate.exchange("/api/users/register", HttpMethod.POST, requestEntity, Boolean.class);

        assert(response.getStatusCodeValue() == 200);
        assert(response.getBody().booleanValue());
        return true;
    }

    protected UserVO getCurrentUser() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");

        ResponseEntity<UserVO> response = restTemplate.exchange("/api/users", HttpMethod.GET, new HttpEntity<>(headers), UserVO.class);

        assert(response.getStatusCodeValue() == 200);
        return response.getBody();
    }

    protected Integer createCollection(String name, List<ItemVO> itemVOList) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        HttpEntity<List<ItemVO>> httpEntity = new HttpEntity<>(itemVOList, headers);

        String uri = UriComponentsBuilder.fromUriString("/api/collections")
                .queryParam("collectionName", name)
                .toUriString();

        ResponseEntity<Integer> response = restTemplate.exchange(uri, HttpMethod.POST, httpEntity, Integer.class);
        assert(response.getStatusCodeValue() == 200);
        return response.getBody();
    }

    protected CollectionVO getCollectionById(Integer collectionId) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");

        ResponseEntity<CollectionVO> response = restTemplate.exchange("/api/collections/"+collectionId, HttpMethod.GET, new HttpEntity<>(headers), CollectionVO.class);
        assert(response.getStatusCodeValue() == 200);
        return response.getBody();
    }

    protected Integer startVoteSession(Integer collectionId) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");

        ResponseEntity<Integer> response = restTemplate.exchange("/api/vote/start/"+collectionId, HttpMethod.GET, new HttpEntity<>(headers), Integer.class);
        assert(response.getStatusCodeValue() == 200);
        return response.getBody();
    }

    protected VoteRound getNextRound(Integer sessionId) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        ResponseEntity<VoteRound> response = restTemplate.exchange("/api/vote/"+sessionId+"/next", HttpMethod.GET, new HttpEntity<>(headers), VoteRound.class);
        assert(response.getStatusCodeValue() == 200);
        return response.getBody();
    }

    protected Boolean submitVoteRound(VoteRoundResult result) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        HttpEntity<VoteRoundResult> httpEntity = new HttpEntity<>(result, headers);
        ResponseEntity<Boolean> response = restTemplate.exchange("/api/vote/result", HttpMethod.POST, httpEntity, Boolean.class);
        assert(response.getStatusCodeValue() == 200);
        assert(response.getBody().booleanValue());
        return true;
    }

    protected Boolean endVoteSession(Integer sessionId) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");

        ResponseEntity<Boolean> httpEntity = restTemplate.exchange("/api/vote/"+sessionId+"/end", HttpMethod.GET, new HttpEntity<>(headers), Boolean.class);
        assert(httpEntity.getStatusCodeValue() == 200);
        assert (httpEntity.getBody().booleanValue());
        return httpEntity.getBody();
    }
}
