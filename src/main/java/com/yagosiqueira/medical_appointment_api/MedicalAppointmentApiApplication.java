package com.yagosiqueira.medical_appointment_api;

import com.yagosiqueira.medical_appointment_api.repository.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class MedicalAppointmentApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(MedicalAppointmentApiApplication.class, args);
	}

	}