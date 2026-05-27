package com.chatdam.servidor.repository;

import com.chatdam.servidor.model.Mensaje;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface MensajeRepository extends MongoRepository<Mensaje, String> {
    List<Mensaje> findTop10ByOrderByTimestampAsc();
}