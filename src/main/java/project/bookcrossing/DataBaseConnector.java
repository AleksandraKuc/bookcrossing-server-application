package project.bookcrossing;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataBaseConnector implements CommandLineRunner {

	@Autowired
	public DataBaseConnector() {
	}

	@Override
	public void run(String... args) throws Exception {

	}
}
