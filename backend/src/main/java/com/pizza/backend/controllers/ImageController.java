package com.pizza.backend.controllers;

import com.pizza.backend.services.ImageService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@AllArgsConstructor
@RestController
@Validated
public class ImageController
{
  ImageService imageService;

  @PostMapping("/image")
  public void upload(@RequestParam("image") MultipartFile image) throws IOException
  {
    imageService.upload(image);
  }

  @GetMapping("/image/{name}")
  public ResponseEntity<?> get(@PathVariable String name)
  {
    byte[] image = imageService.get(name);

    return ResponseEntity.status(HttpStatus.OK)
      .contentType(MediaType.valueOf("image/png"))
      .body(image);
  }

  @PatchMapping("/image/{name}")
  public void update(@PathVariable String name, @RequestParam("image") MultipartFile image) throws IOException
  {
    imageService.update(name, image);
  }

  @DeleteMapping("/image/{name}")
  public void delete(@PathVariable String name)
  {
    imageService.delete(name);
  }
}
