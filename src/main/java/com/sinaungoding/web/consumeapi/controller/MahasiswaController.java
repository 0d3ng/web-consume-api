package com.sinaungoding.web.consumeapi.controller;

import com.sinaungoding.web.consumeapi.dto.Mahasiswa;
import com.sinaungoding.web.consumeapi.service.MahasiswaRestClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.client.HttpStatusCodeException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
@Slf4j
public class MahasiswaController {

    @Autowired
    private MahasiswaRestClient mahasiswaRestClient;

    @GetMapping("/")
    public String getAll(HttpServletRequest request, ModelMap modelMap) {
        modelMap.addAttribute("mahasiswas", mahasiswaRestClient.getMahasiswas(request.getQueryString()));
        return "/index";
    }

    @GetMapping("/index")
    public String toIndex() {
        return "redirect:/";
    }

    @GetMapping("/mahasiswa/form/{id}")
    public String tampilFormedit(@PathVariable(name = "id") String id, ModelMap modelMap) {
        modelMap.addAttribute("mahasiswa", mahasiswaRestClient.getMahasiswa(id));
        return "/mahasiswa/form";
    }

    @GetMapping("/mahasiswa/form")
    public void tampilFormTambah(ModelMap modelMap) {
        modelMap.addAttribute("mahasiswa", new Mahasiswa());
    }

    @PostMapping("/mahasiswa/form")
    public String editMahasiswa(@ModelAttribute @Valid Mahasiswa mahasiswa, BindingResult errors, SessionStatus status) {
        log.info(mahasiswa.toString());
        log.info(errors.toString());
        log.info("" + errors.hasErrors());
        log.info("" + errors.hasGlobalErrors());
        try {
            mahasiswaRestClient.insert(mahasiswa);
            status.setComplete();
            return "redirect:/";
        } catch (HttpStatusCodeException e) {
            ResponseEntity<String> response = ResponseEntity.status(e.getStatusCode()).headers(e.getResponseHeaders()).body(e.getResponseBodyAsString());
            log.error(response.getBody());
            log.error("" + response.getStatusCodeValue());
            errors.reject("error.object", response.getBody());
        }
        return "/mahasiswa/form";
    }

    @GetMapping("/mahasiswa/detail_form/{id}")
    public String tampilFormDetail(@PathVariable String id, ModelMap modelMap) {
        modelMap.addAttribute("mahasiswa", mahasiswaRestClient.getMahasiswa(id));
        return "/mahasiswa/detail_form";
    }

    @DeleteMapping("/mahasiswa/hapus")
    public String hapusMahasiswa(@RequestParam(name = "id") String id) {
        mahasiswaRestClient.delete(id);
        return "redirect:/index";
    }
}
