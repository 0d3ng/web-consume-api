package com.sinaungoding.web.consumeapi.controller;

import com.sinaungoding.web.consumeapi.dto.Mahasiswa;
import com.sinaungoding.web.consumeapi.service.MahasiswaRestClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
public class MahasiswaController {

    private final static Logger LOGGER = LoggerFactory.getLogger(MahasiswaController.class.getName());

    @Autowired
    private MahasiswaRestClient mahasiswaRestClient;

    @GetMapping("/index")
    public ModelMap getAll(HttpServletRequest request) {
        return new ModelMap().addAttribute("mahasiswas", mahasiswaRestClient.getMahasiswas(request.getQueryString()));
    }

    @GetMapping("/")
    public String index() {
        return "redirect:/index";
    }

    @GetMapping("/mahasiswa/form/{id}")
    public ModelMap tampilFormedit(@PathVariable(name = "id") String id) {
        return new ModelMap().addAttribute("mahasiswa", mahasiswaRestClient.getMahasiswa(id));
    }

    @PostMapping("/mahasiswa/form")
    public String editMahasiswa(@ModelAttribute @Valid Mahasiswa mahasiswa, BindingResult errors, SessionStatus status) {
        LOGGER.info(mahasiswa.toString());
        LOGGER.info(errors.toString());
        LOGGER.info("" + errors.hasErrors());
        LOGGER.info("" + errors.hasGlobalErrors());
        if (mahasiswaRestClient.insert(mahasiswa)) {
            status.setComplete();
            return "redirect:/index";
        } else {
            return "/mahasiswa/form";
        }
    }

    @GetMapping("/mahasiswa/detail_form/{id}")
    public ModelMap tampilFormDetail(@PathVariable(name = "id") String id) {
        return new ModelMap().addAttribute("mahasiswa", mahasiswaRestClient.getMahasiswa(id));
    }

    @DeleteMapping("/mahasiswa/hapus")
    public String hapusMahasiswa(@RequestParam(name = "id") String id) {
        mahasiswaRestClient.delete(id);
        return "redirect:/index";
    }
}
