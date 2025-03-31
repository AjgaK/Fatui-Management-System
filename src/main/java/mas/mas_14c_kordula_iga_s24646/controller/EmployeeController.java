package mas.mas_14c_kordula_iga_s24646.controller;

import mas.mas_14c_kordula_iga_s24646.model.Agent;
import mas.mas_14c_kordula_iga_s24646.model.AgentTypeAgent;
import mas.mas_14c_kordula_iga_s24646.model.Employee;
import mas.mas_14c_kordula_iga_s24646.model.Harbinger;
import mas.mas_14c_kordula_iga_s24646.repository.EmployeeRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.HashSet;
import java.util.Set;

/**
 * Controller responsible for handling operations related to employees.
 * Allows displaying a list of all employees and assigning them to specific roles.
 */
@Controller
public class EmployeeController {
    private EmployeeRepository employeeRepository;

    /**
     * Constructor for the controller, initializes the employee repository.
     *
     * @param employeeRepository the employee repository
     */
    public EmployeeController(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    /**
     * Handles GET requests for displaying all employees.
     * Employees are filtered based on their roles (Harbinger, Special Agent, Senior Agent, Normal Agent).
     *
     * @param model the model used to pass data to the view
     * @return the name of the view to display
     */
    @GetMapping("/employees")
    public String showAllEmployees(Model model) {
        Set<Harbinger> harbingers = new HashSet<>();
        Set<Agent> specialAgents = new HashSet<>();
        Set<Agent> seniorAgents = new HashSet<>();
        Set<Agent> normalAgents = new HashSet<>();
        if(employeeRepository.count() == 0) {
            model.addAttribute("employees", null);
        }
        for(Employee employee : employeeRepository.findAll()) {
            Harbinger harbinger = employee.getHarbinger();
            Agent agent = employee.getAgent();
            if(harbinger != null) {
                harbingers.add(harbinger);
            } else if(agent != null) {
                Set<AgentTypeAgent> agentTypeConns = agent.getAgentTypeAgents();
                for(AgentTypeAgent agentTypeConnection : agentTypeConns) {
                    if(agentTypeConnection.getAgentType().getName().equals("Special Agent")) {
                        specialAgents.add(agent);
                    }
                    if(agentTypeConnection.getAgentType().getName().equals("Senior Agent")){
                        seniorAgents.add(agent);
                    }
                    if(agentTypeConnection.getAgentType().getName().equals("Normal")) {
                        normalAgents.add(agent);
                    }
                }
            }
        }
        model.addAttribute("harbingers", harbingers);
        model.addAttribute("seniorAgents", seniorAgents);
        model.addAttribute("specialAgents", specialAgents);
        model.addAttribute("normalAgents", normalAgents);

        return "employees";
    }
}
