package org.example.javabeadando.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@Controller
public class RestOldalController {

    @GetMapping("/rest")
    public String rest(Model model) {
        String url = ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/szallodak").toUriString();
        String uj = "{\"az\":\"TS\",\"nev\":\"Teszt Szalloda\",\"besorolas\":4,\"helysegAz\":1,\"tengerpartTav\":100,\"repterTav\":20,\"felpanzio\":true}";
        String modositott = "{\"az\":\"TS\",\"nev\":\"Teszt Szalloda 2\",\"besorolas\":5,\"helysegAz\":2,\"tengerpartTav\":50,\"repterTav\":10,\"felpanzio\":false}";
        String curl = String.join("\n\n",
                "curl " + url,
                "curl " + url + "/BS",
                "curl -u admin:admin -X POST " + url + " -H \"Content-Type: application/json\" -d '" + uj + "'",
                "curl -u admin:admin -X PUT " + url + "/TS -H \"Content-Type: application/json\" -d '" + modositott + "'",
                "curl -u admin:admin -X DELETE " + url + "/TS");
        model.addAttribute("apiUrl", url);
        model.addAttribute("curl", curl);
        return "rest";
    }
}
