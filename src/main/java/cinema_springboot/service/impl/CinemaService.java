package cinema_springboot.service.impl;

import com.example.cinema_springboot.exception.EntityNotFoundException;
import com.example.cinema_springboot.model.entity.Cinema;
import com.example.cinema_springboot.repository.CinemaRepository;
import com.example.cinema_springboot.service.ServiceLayer;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class CinemaService implements ServiceLayer<Cinema> {

    CinemaRepository cinemaRepository;

    @Override
    public void save(Cinema cinema) {
        cinemaRepository.save(cinema);
    }

    @Override
    public Cinema findById(Long id) {
        return cinemaRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException(String.format("Cinema with id=%d not found!!!", id)));
    }

    @Override
    public List<Cinema> findAll() {
        return cinemaRepository.findAll();
    }

    @Override
    public Cinema update(Long id, Cinema cinema) {

        Cinema oldCinema = findById(id);
        oldCinema.setName(cinema.getName());
        oldCinema.setAddress(cinema.getAddress());
        oldCinema.setImage(cinema.getImage());
        cinemaRepository.save(oldCinema);
        return oldCinema;
    }

    @Override
    public void deleteById(Long id) {
        cinemaRepository.deleteById(id);
    }
}
