package cinema_springboot.controller;

import com.example.cinema_springboot.model.entity.Cinema;
import com.example.cinema_springboot.service.impl.CinemaService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@RequiredArgsConstructor
@RequestMapping("/cinema")
@PreAuthorize("hasAuthority('ADMIN')")
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class CinemaController {

    CinemaService cinemaService;

    @PreAuthorize("hasAuthority('USER')")
    @GetMapping("find_all")
    public String findAll(Model model) {
        model.addAttribute("all_cinemas", cinemaService.findAll());
        return "cinema/find_all";
    }

    @GetMapping("/save")
    public String save(Model model) {
        model.addAttribute("cinema", new Cinema());
        return "/cinema/save";
    }

    @PostMapping("/save_cinema")
    public String saveCinema(@ModelAttribute Cinema cinema, @RequestParam("file") MultipartFile multipartFile) throws IOException {
        cinema.setImage(multipartFile.getBytes());
        cinemaService.save(cinema);
        return "redirect:/cinema/find_all";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        cinemaService.deleteById(id);
        return "redirect:/cinema/find_all";
    }

    @GetMapping("/image/{id}")
    public ResponseEntity<byte[]> getImage(@PathVariable("id") Long id) {
        Cinema cinema = cinemaService.findById(id);
        if (cinema != null && cinema.getImage() != null) {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_JPEG);
            return new ResponseEntity<>(cinema.getImage(), headers, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
