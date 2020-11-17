package project.bookcrossing.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.bookcrossing.entity.Report;
import project.bookcrossing.entity.User;
import project.bookcrossing.repository.ReportRepository;

import java.util.List;

@Service
public class ReportService {

	@Autowired
	private ReportRepository reportRepository;

	public void create(String description, User user, User reporter) {
		Report report = new Report(description, user, reporter);
		reportRepository.save(report);
	}

	public List<Report> getAll() {
		return (List<Report>) reportRepository.findAll();
	}
}
