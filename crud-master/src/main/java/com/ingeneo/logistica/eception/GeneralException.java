package com.ingeneo.logistica.eception;

import com.ingeneo.logistica.dto.Mensaje;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class GeneralException extends Exception {

        private HttpStatus status;
        private Mensaje mensaje;

        public GeneralException(HttpStatus status) {
            super();
            this.status = status;
        }

        public GeneralException(String mensaje, HttpStatus status) {
            super(mensaje);
            this.status = status;
            this.mensaje=new Mensaje(mensaje);
        }


    }
