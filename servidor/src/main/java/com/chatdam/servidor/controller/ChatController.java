package com.chatdam.servidor.controller;

import com.chatdam.servidor.dto.UsuarioDTO;
import com.chatdam.servidor.model.Mensaje;
import com.chatdam.servidor.model.Usuario;
import com.chatdam.servidor.repository.MensajeRepository;
import com.chatdam.servidor.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*") // Muy importante para evitar errores CORS al conectar desde JavaFX
public class ChatController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private MensajeRepository mensajeRepository;

    // 1. LOGIN
    @PostMapping("/login")
    public ResponseEntity<UsuarioDTO> login(@RequestBody Usuario usuarioLogin) {
        Optional<Usuario> usuarioDb = usuarioRepository.findByNombreUsuario(usuarioLogin.getNombreUsuario());

        if (usuarioDb.isPresent()) {
            Usuario user = usuarioDb.get();
            if (user.getPassword().equals(usuarioLogin.getPassword())) {
                return ResponseEntity.ok(new UsuarioDTO(user.getNombreUsuario(), user.getRol()));
            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build(); // 401 Error
    }

    // 2. RECUPERAR MENSAJES
    @GetMapping("/mensajes")
    public ResponseEntity<List<Mensaje>> obtenerMensajes() {
        return ResponseEntity.ok(mensajeRepository.findTop10ByOrderByTimestampAsc());
    }

    // 3. GUARDAR MENSAJE
    @PostMapping("/mensajes")
    public ResponseEntity<Mensaje> guardarMensaje(@RequestBody Mensaje mensaje) {
        mensaje.setTimestamp(System.currentTimeMillis()); // Autogeneramos la marca de tiempo
        return ResponseEntity.status(HttpStatus.CREATED).body(mensajeRepository.save(mensaje));
    }

    // 4. CREAR NUEVO USUARIO
    @PostMapping("/usuarios")
    public ResponseEntity<UsuarioDTO> crearUsuario(@RequestBody Usuario nuevoUsuario) {
        if (usuarioRepository.findByNombreUsuario(nuevoUsuario.getNombreUsuario()).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build(); // 409 Ya existe
        }
        Usuario guardado = usuarioRepository.save(nuevoUsuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(new UsuarioDTO(guardado.getNombreUsuario(), guardado.getRol()));
    }
}