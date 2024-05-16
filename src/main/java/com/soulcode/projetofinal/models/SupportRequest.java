package com.soulcode.projetofinal.models;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@Table(name = "request")
public class SupportRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private int id;

    private String title;

    private String description;

    @ManyToOne
    @JoinColumn(name = "priority_id")
    private Priority priority;

    private LocalDateTime startDate;

    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;

    @ManyToOne
    @JoinColumn(name = "status_id")
    private Status status;

    @ManyToOne
    @JoinColumn(name = "technician_id")
    private Person technician;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Person user;

    public SupportRequest(){

    }
    public SupportRequest(String title, String description, Priority priority, LocalDateTime startDate, Department department, Status status, Person technician, Person user) {
        this.title = title;
        this.description = description;
        this.priority = priority;
        this.startDate = startDate;
        this.department = department;
        this.status = status;
        this.technician = technician;
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public LocalDateTime getStartDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        LocalDateTime currentDateTime = LocalDateTime.now();
        String formattedDate = currentDateTime.format(formatter);
        LocalDateTime convertedDate = LocalDateTime.parse(formattedDate, formatter);
        return convertedDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Person getTechnician() {
        return technician;
    }

    public void setTechnician(Person technician) {
        this.technician = technician;
    }

    public Person getUser() {
        return user;
    }

    public void setUser(Person user) {
        this.user = user;
    }

    public String getRequesterName() {
        if (user != null) {
            return user.getName();
        } else {
            return null;
        }
    }
}