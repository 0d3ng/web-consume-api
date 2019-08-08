package com.sinaungoding.web.consumeapi.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MahasiswaController {

    private final static Logger LOGGER = LoggerFactory.getLogger(MahasiswaController.class.getName());

    @GetMapping("/index")
    public ModelMap getAll() {
        return new ModelMap();
    }

    @GetMapping("/")
    public String index() {
        return "redirect:/index";
    }

    @GetMapping("/mahasiswa/form")
    public ModelMap tampilFormedit() {
        return new ModelMap();
    }

    @PostMapping("/mahasiswa/form")
    public String editMahasiswa() {
        return "redirect:/index";
    }

    @GetMapping("/mahasiswa/detail_form")
    public ModelMap tampilFormDetail() {
        return new ModelMap();
    }

    @DeleteMapping("/mahasiswa/hapus")
    public String hapusMahasiswa(@RequestParam(name = "id") String id) {
        return "redirect:/index";
    }
}
