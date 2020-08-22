package com.example.trainingapp.interfaces;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class SampleController {
  @GetMapping()
  public String fetch() {
    return "Backend application is up!";
  }
}
