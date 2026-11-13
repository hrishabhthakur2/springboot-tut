
package com.example.employee_management.controller;

import com.example.employee_management.model.Employee;
import com.example.employee_management.dto.EmployeeRequest;
import com.example.employee_management.dto.EmployeeResponse;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    private final List<Employee> employees = new ArrayList<>();

    public EmployeeController() {
        //fixed constructor, fills with 3 value
        employees.add(new Employee(
                1L, "Hrishabh", "hrishabh@gmail.com",
                "Engineering", 85000.0
        ));

        employees.add(new Employee(
                2L, "Rahul", "rahul@gmail.com",
                "Marketing", 65000.0
        ));

        employees.add(new Employee(
                3L, "Priya", "priya@gmail.com",
                "HR", 70000.0
        ));
    }

    @GetMapping
    public List<Employee> getAllEmployees() {
        return employees;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id){
        return employees.stream()
                .filter(employee -> employee.getId().equals(id))
                .findFirst()
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    
    @PostMapping
    public ResponseEntity<EmployeeResponse> createEmployee(
            @Valid @RequestBody EmployeeRequest request) {

        long newId = employees.stream()
                .mapToLong(Employee::getId)
                .max()
                .orElse(0L) + 1;

        Employee employee = new Employee(
                newId,
                request.getName(),
                request.getEmail(),
                request.getDepartment(),
                request.getSalary()
        );

        employees.add(employee);

        EmployeeResponse response = new EmployeeResponse(
                employee.getId(),
                employee.getName(),
                employee.getEmail(),
                employee.getDepartment(),
                employee.getSalary()
        );

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(response);
    }


    @PutMapping("/{id}") 
    public ResponseEntity<Employee> updateEmployee(@PathVariable Long id, @RequestBody Employee updatedEmployee){

        for (Employee employee: employees){

            if(employee.getId().equals(id)){

                employee.setName(updatedEmployee.getName());
                employee.setEmail(updatedEmployee.getEmail());
                employee.setDepartment(updatedEmployee.getDepartment());
                employee.setSalary(updatedEmployee.getSalary());

                return ResponseEntity.ok(employee);
            }
        }

        return ResponseEntity.notFound().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Employee> patchEmployee(@PathVariable Long id, @RequestBody Map<String, Object> updates){

        Employee employee = employees.stream()
                    .filter(e -> e.getId().equals(id))
                    .findFirst()
                    .orElse(null);
        
        if(employee == null){
            return ResponseEntity.notFound().build();
        }

        if(updates.containsKey("name")){
            employee.setName((String) updates.get("name"));
        }

        if(updates.containsKey("email")){
            employee.setEmail((String) updates.get("email"));
        }

        if(updates.containsKey("department")){
            employee.setDepartment((String) updates.get("department"));
        }

        if(updates.containsKey("salary")){
            employee.setSalary(((Number) updates.get("salary")).doubleValue());
        }

        return ResponseEntity.ok(employee);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Employee> deleteEmployee(@PathVariable Long id) {

        Employee employee = employees.stream()
                .filter(e -> e.getId().equals(id))
                .findFirst()
                .orElse(null);

        if (employee == null) {
            return ResponseEntity.notFound().build();
        }

        employees.remove(employee);

        return ResponseEntity.ok(employee);
    }

    @GetMapping("/search")
    public List<Employee> searchEmployees(@RequestParam String department){

        return employees.stream()
                .filter(employee -> employee.getDepartment().equalsIgnoreCase(department))
                .toList();
    }
}
