package cinema_springboot.service;

import com.example.cinema_springboot.model.entity.Cinema;

import java.util.List;

public interface ServiceLayer <T> {

    void save(T t);

    T findById(Long id);

    List<Cinema> findAll();

    T update(Long id, T t);

    void deleteById(Long id);
}
