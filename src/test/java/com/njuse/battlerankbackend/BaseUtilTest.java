package com.njuse.battlerankbackend;


import com.njuse.battlerankbackend.vo.*;
import org.apache.hc.client5.http.cookie.BasicCookieStore;
import org.apache.hc.client5.http.cookie.CookieStore;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
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

        HttpHeaders headers = createHeaders();
        HttpEntity<UserVO> requestEntity = new HttpEntity<>(user, headers);

        ResponseEntity<ResultVO<Boolean>> response = restTemplate.exchange(
                "/api/users/login",
                HttpMethod.POST,
                requestEntity,
                new ParameterizedTypeReference<ResultVO<Boolean>>() {
                });

        assert (response.getStatusCodeValue() == 200);
        return response.getBody().getResult();
    }

    protected Boolean register(String phone, String password) {
        UserVO user = new UserVO();
        user.setPhone(phone);
        user.setPassword(password);

        HttpHeaders headers = createHeaders();
        HttpEntity<UserVO> requestEntity = new HttpEntity<>(user, headers);

        ResponseEntity<ResultVO<Boolean>> response = restTemplate.exchange(
                "/api/users/register",
                HttpMethod.POST,
                requestEntity,
                new ParameterizedTypeReference<ResultVO<Boolean>>() {
                });

        assert (response.getStatusCodeValue() == 200);
        return response.getBody().getResult();
    }

    protected UserVO getCurrentUser() {
        HttpHeaders headers = createHeaders();

        ResponseEntity<ResultVO<UserVO>> response = restTemplate.exchange(
                "/api/users",
                HttpMethod.GET,
                new HttpEntity<>(headers),
                new ParameterizedTypeReference<ResultVO<UserVO>>() {
                });

        assert (response.getStatusCodeValue() == 200);
        return response.getBody().getResult();
    }

    protected Integer createCollection(CollectionVO collectionVO) {
        HttpHeaders headers = createHeaders();
        HttpEntity<CollectionVO> httpEntity = new HttpEntity<>(collectionVO, headers);


        ResponseEntity<ResultVO<Integer>> response = restTemplate.exchange(
                "/api/collections",
                HttpMethod.POST,
                httpEntity,
                new ParameterizedTypeReference<>() {
                });
        System.out.println(response.getBody().getResult());
        assert (response.getBody().getCode().equals("200"));
        return response.getBody().getResult();
    }

    protected CollectionVO getCollectionById(Integer collectionId) {
        HttpHeaders headers = createHeaders();

        ResponseEntity<ResultVO<CollectionVO>> response = restTemplate.exchange(
                "/api/collections/" + collectionId,
                HttpMethod.GET,
                new HttpEntity<>(headers),
                new ParameterizedTypeReference<ResultVO<CollectionVO>>() {
                });

        assert (response.getStatusCodeValue() == 200);
        return response.getBody().getResult();
    }

    protected Integer startVoteSession(Integer collectionId) {
        HttpHeaders headers = createHeaders();

        ResponseEntity<ResultVO<Integer>> response = restTemplate.exchange(
                "/api/vote/start/" + collectionId,
                HttpMethod.GET,
                new HttpEntity<>(headers),
                new ParameterizedTypeReference<ResultVO<Integer>>() {
                });

        assert (response.getStatusCodeValue() == 200);
        return response.getBody().getResult();
    }

    protected VoteRound getNextRound(Integer sessionId) {
        HttpHeaders headers = createHeaders();

        ResponseEntity<ResultVO<VoteRound>> response = restTemplate.exchange(
                "/api/vote/" + sessionId + "/next",
                HttpMethod.GET,
                new HttpEntity<>(headers),
                new ParameterizedTypeReference<ResultVO<VoteRound>>() {
                });

        assert (response.getStatusCodeValue() == 200);
        return response.getBody().getResult();
    }

    protected Boolean submitVoteRound(VoteRoundResult result) {
        HttpHeaders headers = createHeaders();
        HttpEntity<VoteRoundResult> httpEntity = new HttpEntity<>(result, headers);

        ResponseEntity<ResultVO<Boolean>> response = restTemplate.exchange(
                "/api/vote/result",
                HttpMethod.POST,
                httpEntity,
                new ParameterizedTypeReference<ResultVO<Boolean>>() {
                });

        assert (response.getStatusCodeValue() == 200);
        return response.getBody().getResult();
    }

    protected Boolean endVoteSession(Integer sessionId) {
        HttpHeaders headers = createHeaders();

        ResponseEntity<ResultVO<Boolean>> response = restTemplate.exchange(
                "/api/vote/" + sessionId + "/end",
                HttpMethod.GET,
                new HttpEntity<>(headers),
                new ParameterizedTypeReference<ResultVO<Boolean>>() {
                });

        assert (response.getStatusCodeValue() == 200);
        return response.getBody().getResult();
    }

    private HttpHeaders createHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        return headers;
    }
}
