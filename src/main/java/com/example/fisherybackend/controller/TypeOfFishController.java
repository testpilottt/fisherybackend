package com.example.fisherybackend.controller;

import jakarta.transaction.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Transactional
@RestController
@RequestMapping("/fishery")
public class TypeOfFishController {

}
