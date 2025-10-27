package com.biblioswipe.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//al ejecutarlo con el run se activa el servidor embebido se spring boot (pone a mi backend a
// escuchar peticiones HTTP) por defecto el puerto 8080, con esto ya podemos usar postman o navegador o frontend
@SpringBootApplication
public class BackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(BackendApplication.class, args);
    }

}
