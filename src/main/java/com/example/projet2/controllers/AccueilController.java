package com.example.projet2.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AccueilController {

    @GetMapping("/accueil")
    public String afficherAccueil() {
        return "accueil"; // Nom du fichier HTML pour la page d'accueil
    }
}
