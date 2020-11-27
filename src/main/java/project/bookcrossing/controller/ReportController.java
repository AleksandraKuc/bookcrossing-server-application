package project.bookcrossing.controller;

import io.swagger.annotations.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import project.bookcrossing.dto.report.ReportDataDTO;
import project.bookcrossing.dto.report.ReportResponseDTO;
import project.bookcrossing.entity.Report;
import project.bookcrossing.entity.User;
import project.bookcrossing.service.ReportService;
import project.bookcrossing.service.UserService;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = {"http://localhost:4200", "http://localhost:8100"})
@RestController
@RequestMapping(value = "/api/report")
public class ReportController {

	@Autowired
	private ReportService reportService;
	@Autowired
	private UserService userService;
	@Autowired
	private ModelMapper modelMapper;

	@PostMapping("/create")
	@PreAuthorize("hasRole('ROLE_CLIENT')")
	@ApiOperation(value = "${ReportController.create}")
	@ApiResponses(value = {//
			@ApiResponse(code = 400, message = "Something went wrong"), //
			@ApiResponse(code = 403, message = "Access denied"),})
	public void create(@ApiParam("Report") @RequestBody ReportDataDTO report) {
		User user = userService.search(report.getUser());
		User reporter = userService.search(report.getReporter());
		reportService.create(report.getDescription(), user, reporter);
	}

	@GetMapping(value = "/all")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@ApiOperation(value = "${ReportController.getAll}")
	@ApiResponses(value = {//
			@ApiResponse(code = 400, message = "Something went wrong"), //
			@ApiResponse(code = 403, message = "Access denied"),})
	public List<ReportResponseDTO> getAll() {
		List<Report> reports = reportService.getAll();
		List<ReportResponseDTO> response = new ArrayList<>();
		for (Report report : reports) {
			response.add(new ReportResponseDTO(report.getId_report(), report.getFirstUser().getUsername(),
					report.getSecondUser().getUsername(), report.getDescription(), report.getDate()));
		}
		return response;
	}

	@DeleteMapping(value = "/delete/{reportId}")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@ApiOperation(value = "${ReportController.delete}", authorizations = { @Authorization(value="apiKey") })
	@ApiResponses(value = {//
			@ApiResponse(code = 400, message = "Something went wrong"), //
			@ApiResponse(code = 403, message = "Access denied"), //
			@ApiResponse(code = 404, message = "The report doesn't exist"), //
			@ApiResponse(code = 500, message = "Expired or invalid JWT token")})
	public void deleteByConversation(@ApiParam("ReportId") @PathVariable long reportId) {
		reportService.deleteReport(reportId);
	}
}
