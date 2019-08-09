package com.sinaungoding.web.consumeapi.service;

import com.sinaungoding.web.consumeapi.dto.Mahasiswa;
import com.sinaungoding.web.consumeapi.util.RestResponsePage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class MahasiswaRestClient {
    @Autowired
    private RestTemplate restTemplate;
    @Value(value = "${server.rest}")
    private String restServer;

    private static final Logger LOGGER = LoggerFactory.getLogger(MahasiswaRestClient.class.getName());

    public Page<Mahasiswa> getMahasiswas(String param) {
        ParameterizedTypeReference<RestResponsePage<Mahasiswa>> ptr =
                new ParameterizedTypeReference<RestResponsePage<Mahasiswa>>() {
                };
        return restTemplate.exchange(restServer + "/api/mahasiswa" + (param == null ? "" : "?" + param), HttpMethod.GET, null, ptr).getBody();
    }

    public Mahasiswa getMahasiswa(String id) {
        return restTemplate.getForObject(restServer + "/api/mahasiswa/{id}", Mahasiswa.class, id);
    }

    public boolean insert(Mahasiswa mahasiswa) {
        HttpEntity<Mahasiswa> mahasiswaHttpEntity = new HttpEntity<>(mahasiswa);
        ResponseEntity<String> response = restTemplate.exchange(restServer + "/api/mahasiswa", HttpMethod.POST, mahasiswaHttpEntity, String.class);
        LOGGER.info("" + response.getStatusCode().value());
        return (HttpStatus.CREATED.value() == response.getStatusCodeValue());
    }

    public boolean delete(String id) {
        HttpEntity<String> mahasiswaHttpEntity = new HttpEntity<>(id);
        ResponseEntity<String> response = restTemplate.exchange(restServer + "/api/mahasiswa", HttpMethod.DELETE, mahasiswaHttpEntity, String.class);
        LOGGER.info("" + response.getStatusCode().value());
        return (HttpStatus.OK.value() == response.getStatusCodeValue());
    }
}
