package server.entity;

import shared.TaskStatus;

import java.io.Serializable;
import java.time.LocalDate;

public class Task implements Serializable, Printable {
    private String description;
    private String reporterName;
    private String reporterEmail;
    private Long dateOfCreation;
    private TaskStatus status;
    private int id;
    private String report;

    public Task() {

    }

    public Task(String description, String reporterName, String reporterEmail, Long dateOfCreation) {
        this.description = description;
        this.reporterName = reporterName;
        this.reporterEmail = reporterEmail;
        this.dateOfCreation = dateOfCreation;
        this.status = TaskStatus.OPEN;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getReporterName() {
        return reporterName;
    }

    public void setReporterName(String reporterName) {
        this.reporterName = reporterName;
    }

    public String getReporterEmail() {
        return reporterEmail;
    }

    public void setReporterEmail(String reporterEmail) {
        this.reporterEmail = reporterEmail;
    }

    public Long getDateOfCreation() {
        return dateOfCreation;
    }

    public void setDateOfCreation(Long dateOfCreation) {
        this.dateOfCreation = dateOfCreation;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getReport() {
        return report;
    }

    public void setReport(String report) {
        this.report = report;
    }

    @Override
    public String getText() {
        return toString();
    }

    @Override
    public String toString() {
        return "Id: " + id + ". " + description + " reporter: " + reporterName + " date of creation: "
                + LocalDate.ofEpochDay(dateOfCreation).toString() + " status: " + status.getText();
    }

    @Override
    public String getTrimmedText() {
        return id + ". " + description;
    }
}
