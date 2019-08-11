package com.sinaungoding.web.consumeapi.service;

import com.sinaungoding.web.consumeapi.dto.Mahasiswa;
import com.sinaungoding.web.consumeapi.util.RestResponsePage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
public class MahasiswaRestClient {
    @Autowired
    private RestTemplate restTemplate;
    @Value(value = "${server.rest}")
    private String restServer;

    public Page<Mahasiswa> getMahasiswas(String param) {
        ParameterizedTypeReference<RestResponsePage<Mahasiswa>> ptr =
                new ParameterizedTypeReference<RestResponsePage<Mahasiswa>>() {
                };
        return restTemplate.exchange(restServer + "/api/mahasiswa" + (param == null ? "" : "?" + param), HttpMethod.GET, null, ptr).getBody();
    }

    public Mahasiswa getMahasiswa(String id) {
        return restTemplate.getForObject(restServer + "/api/mahasiswa/{id}", Mahasiswa.class, id);
    }

    public boolean insert(Mahasiswa mahasiswa) throws HttpStatusCodeException {
        try {
            HttpEntity<Mahasiswa> mahasiswaHttpEntity = new HttpEntity<>(mahasiswa);
            ResponseEntity<String> response = restTemplate.exchange(restServer + "/api/mahasiswa", HttpMethod.POST, mahasiswaHttpEntity, String.class);
            log.info("" + response.getStatusCode().value());
            log.info("" + response.getBody());
            return (HttpStatus.CREATED.value() == response.getStatusCodeValue());
        } catch (HttpStatusCodeException e) {
            throw e;
        }
    }

    public boolean delete(String id) throws HttpStatusCodeException {
        try {
            ResponseEntity<String> response = restTemplate.exchange(restServer + "/api/mahasiswa/{id}", HttpMethod.DELETE, null, String.class, id);
            log.info("" + response.getStatusCode().value());
            return (HttpStatus.OK.value() == response.getStatusCodeValue());
        } catch (HttpStatusCodeException e) {
            throw e;
        }
    }
}
