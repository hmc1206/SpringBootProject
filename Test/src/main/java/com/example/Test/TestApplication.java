package com.example.Test;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

// 반드시 확인: 아래 두 임포트가 정확해야 합니다
import javax.sql.DataSource;
import java.sql.Connection;

@SpringBootApplication
public class TestApplication {

	public static void main(String[] args) {
		SpringApplication.run(TestApplication.class, args);
	}

	@Bean
	public CommandLineRunner testConnection(DataSource dataSource) {
		return args -> {
			try (Connection connection = dataSource.getConnection()) {
				System.out.println("=========================================");
				System.out.println("MariaDB 연결 성공!: " + connection.getMetaData().getURL());
				System.out.println("=========================================");
			} catch (Exception e) {
				System.err.println("=========================================");
				System.err.println("MariaDB 연결 실패: " + e.getMessage());
				System.err.println("=========================================");
			}
		};
	}
}

