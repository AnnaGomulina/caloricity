package ru.caloricity;

import com.vaadin.flow.router.PageTitle;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Locale;

@SpringBootApplication
@PageTitle("Caloricity")
public class Application {

	public static void main(String[] args) {
		Locale.setDefault(Locale.forLanguageTag("ru-RU"));
		SpringApplication.run(Application.class, args);
	}

}
