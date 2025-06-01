package com.example.demo.servicios;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.Excepciones.MyException;
import com.example.demo.entidades.Usuario;
import com.example.demo.enumeraciones.Rol;
import com.example.demo.repositorios.UsuarioRepositorio;

import jakarta.servlet.http.HttpSession;

@Service
public class UsuarioServicio implements UserDetailsService {

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    @Autowired
    private ImagenServicio imagenServicio;

    @Autowired
    private BCryptPasswordEncoder encoder;

    public void registrarUsuario(MultipartFile archivo, String nombre, String email, String password, String password2)
            throws Exception {

        validar(nombre, email, password, password2);
        Usuario nuevoUsuario = new Usuario();
        nuevoUsuario.setNombre(nombre);
        nuevoUsuario.setEmail(email);
        nuevoUsuario.setPassword(encoder.encode(password));
        nuevoUsuario.setRol(Rol.USER);

        if (archivo != null) {
            nuevoUsuario.setImagen(imagenServicio.guardarImagen(archivo));
        }

        usuarioRepositorio.save(nuevoUsuario);

    }

    private void validar(String nombre, String email, String password, String password2) throws MyException {

        if (nombre.isEmpty() || nombre == null) {
            throw new MyException("el nombre no puede ser nulo o estar vacío");
        }
        if (email.isEmpty() || email == null) {
            throw new MyException("el email no puede ser nulo o estar vacío");
        }
        if (password.isEmpty() || password == null || password.length() <= 5) {
            throw new MyException("La contraseña no puede estar vacía, y debe tener más de 5 dígitos");
        }
        if (!password.equals(password2)) {
            throw new MyException("Las contraseñas ingresadas deben ser iguales");
        }
    }

    private void validar(String nombre, String email) throws MyException {

        if (nombre.isEmpty() || nombre == null) {
            throw new MyException("el nombre no puede ser nulo o estar vacío");
        }
        if (email.isEmpty() || email == null) {
            throw new MyException("el email no puede ser nulo o estar vacío");
        }

    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Usuario usuario = usuarioRepositorio.buscarPorEmail(email);

        if (usuario == null) {
            throw new UsernameNotFoundException("Usuario no encontrado");
        }

        List<GrantedAuthority> permisos = new ArrayList<>();
        permisos.add(new SimpleGrantedAuthority("ROLE_" + usuario.getRol().name()));

        return new User(usuario.getEmail(), usuario.getPassword(), permisos);

        // Usuario usuario = usuarioRepositorio.buscarPorEmail(email);

        // if (usuario != null) {
        // List<GrantedAuthority> permisos = new ArrayList<>();
        // GrantedAuthority p = new SimpleGrantedAuthority("ROLE_" +
        // usuario.getRol().toString());
        // permisos.add(p);
        // // ServletRequestAttributes attr = (ServletRequestAttributes)
        // RequestContextHolder.currentRequestAttributes();
        // // HttpSession session = attr.getRequest().getSession(true);
        // // session.setAttribute("usuariosession", usuario);
        // return new User(usuario.getEmail(), usuario.getPassword(), permisos);
        // } else {
        // return null;
        // }

    }

    @Transactional(readOnly = true)
    public List<Usuario> listarUsuarios() {
        return usuarioRepositorio.findAll();
    }

    @Transactional
    public void modificarRol(UUID id) throws MyException {

        Optional<Usuario> usuarioEncontrado = usuarioRepositorio.findById(id);
        System.out.println("nombre " + usuarioEncontrado.isPresent());

        if (usuarioEncontrado.isPresent()) {

            if (usuarioEncontrado.get().getRol().equals(Rol.ADMIN)) {
                usuarioEncontrado.get().setRol(Rol.USER);
            } else if (usuarioEncontrado.get().getRol().equals(Rol.USER)) {
                usuarioEncontrado.get().setRol(Rol.ADMIN);
            } else {
                throw new MyException("El rol no es válido");
            }
            usuarioRepositorio.save(usuarioEncontrado.get());

        } else {
            throw new MyException("usuario no encontrado");
        }

    }

    @Transactional
    public void modificarUsuario(MultipartFile archivo, UUID id, String nombre, String email)
            throws MyException {

        validar(nombre, email);

        Optional<Usuario> usuario = usuarioRepositorio.findById(id);

        if (usuario.isPresent()) {
            usuario.get().setNombre(nombre);
            usuario.get().setEmail(email);

            if (archivo != null) {
                usuario.get().setImagen(imagenServicio.actualizarImagen(archivo, id));
            }

            usuarioRepositorio.save(usuario.get());
        } else {
            throw new MyException("Usuario no encontrado");
        }

    }

    @Transactional(readOnly = true)
    public Usuario buscarUsuario(UUID id) throws MyException {
        Optional<Usuario> usuario = usuarioRepositorio.findById(id);
        if (usuario.isPresent()) {
            return usuario.get();
        } else {
            throw new MyException("Usuario no encontrado");
        }
    }

    @Transactional(readOnly = true)
    public List<String> listaRols() {
        List<String> listaRols = new ArrayList<>();
        for (Rol rol : Rol.values()) {
            listaRols.add(rol.toString());
        }
        return listaRols;
    }

}
