package mas.mas_14c_kordula_iga_s24646.controller;

import mas.mas_14c_kordula_iga_s24646.model.*;
import mas.mas_14c_kordula_iga_s24646.repository.MissionEmployeeRepository;
import mas.mas_14c_kordula_iga_s24646.repository.MissionRepository;
import mas.mas_14c_kordula_iga_s24646.repository.ResourceRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Controller responsible for handling operations related to missions.
 */
@Controller
public class MissionController {
    private MissionRepository missionRepository;
    private ResourceRepository resourceRepository;
    private MissionEmployeeRepository missionEmployeeRepository;

    /**
     * Constructor for the controller, initializes the mission, resource, and mission employee repositories.
     *
     * @param missionRepository         the mission repository
     * @param resourceRepository        the resource repository
     * @param missionEmployeeRepository the mission employee repository
     */
    public MissionController(MissionRepository missionRepository, ResourceRepository resourceRepository, MissionEmployeeRepository missionEmployeeRepository) {
        this.missionRepository = missionRepository;
        this.resourceRepository = resourceRepository;
        this.missionEmployeeRepository = missionEmployeeRepository;
    }

    /**
     * Handles GET requests for displaying all missions.
     *
     * @param model the model used to pass data to the view
     * @return the name of the view to display
     */
    @GetMapping("/missions")
    public String showAllMissions(Model model) {
        if(missionRepository.count() == 0) {
            model.addAttribute("missions", null);
        } else {
            model.addAttribute("missions", missionRepository.findAll());
        }
        return "missions";
    }

    /**
     * Handles GET requests for displaying details of a specific mission.
     *
     * @param id    the mission ID
     * @param model the model used to pass data to the view
     * @return the name of the view to display
     */
    @GetMapping("missions/{id}")
    public String getMissionDetails(@PathVariable("id") Long id, Model model) {
        Mission mission = missionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid mission Id:" + id));
        Set<Resource> resources = mission.getResources();
        model.addAttribute("mission", mission);
        model.addAttribute("resources", resources);

        int harbingersCount = 0;
        long agentsCount = 0;

        Set<MissionEmployee> missionEmployees = mission.getMissionEmployees();
        for(MissionEmployee missionEmployee : missionEmployees) {
            Employee employee = missionEmployee.getEmployee();
            if(employee.getHarbinger() != null) {
                harbingersCount++;
            } else if(employee.getAgent() != null){
                agentsCount++;
            }
        }
        model.addAttribute("harbingersCount", harbingersCount);
        model.addAttribute("agentsCount", agentsCount);

        return "mission";
    }

    /**
     * Handles POST requests for updating resource quantities for a specific mission.
     *
     * @param id                 the mission ID
     * @param resourceQuantities a map containing resource IDs and their updated quantities
     * @return a redirect to the mission details page
     */
    @PostMapping("/missions/{id}/updateResources")
    public String updateResourceQuantities(@PathVariable("id") Long id,
                                           @RequestParam Map<String, String> resourceQuantities) {
        for (Map.Entry<String, String> entry : resourceQuantities.entrySet()) {
            Long resourceId = Long.parseLong(entry.getKey().replace("resourceQuantities[", "").replace("]", ""));
            int quantity = Integer.parseInt(entry.getValue());

            Resource resource = resourceRepository.findById(resourceId)
                    .orElseThrow(() -> new IllegalArgumentException("Invalid resource Id:" + resourceId));
            resource.setQuantity(quantity);
            resourceRepository.save(resource);
        }
        return "redirect:/missions/" + id;
    }

    /**
     * Handles GET requests for displaying the employees assigned to a specific mission.
     *
     * @param id    the mission ID
     * @param model the model used to pass data to the view
     * @return the name of the view to display
     */
    @GetMapping("missions/{id}/assigned")
    public String getAssignedEmployees(@PathVariable("id") Long id, Model model) {
        Mission mission = missionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid mission Id:" + id));
        Set<MissionEmployee> missionEmployees = mission.getMissionEmployees();
        Set<Harbinger> harbingers = new HashSet<>();
        Set<Agent> specialAgents = new HashSet<>();
        Set<Agent> seniorAgents = new HashSet<>();
        Set<Agent> normalAgents = new HashSet<>();
        for(MissionEmployee missionEmployee : missionEmployees) {
            Employee employee = missionEmployee.getEmployee();
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
        return "assigned";
    }

    /**
     * Handles POST requests for deleting an agent from a mission.
     *
     * @param missionId the mission ID
     * @param agentId   the agent ID to be deleted
     * @param model     the model used to pass data to the view
     * @return a redirect to the list of assigned employees for the mission
     */
    @PostMapping("/missions/{id}/deleteAgent")
    public String deleteAgentFromMission(@PathVariable("id") Long missionId, @RequestParam("agentId") Long agentId, Model model) {
        Mission mission = missionRepository.findById(missionId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid mission Id:" + missionId));

        Set<MissionEmployee> missionEmployees = mission.getMissionEmployees();
        Long id = 0L;
        for (MissionEmployee missionEmployee : missionEmployees) {
            Employee employee = missionEmployee.getEmployee();
            if (employee.getAgent() != null) {
                id = employee.getAgent().getId();
            }
            if (id.equals(agentId)) {
                missionEmployeeRepository.delete(missionEmployee);
                mission.getMissionEmployees().remove(missionEmployee);
                break;
            }
        }

        missionRepository.save(mission);

        return "redirect:/missions/" + missionId + "/assigned";
    }

    /**
     * Handles POST requests for deleting a harbinger from a mission.
     *
     * @param missionId    the mission ID
     * @param harbingerId  the harbinger ID to be deleted
     * @param model        the model used to pass data to the view
     * @return a redirect to the list of assigned employees for the mission
     */
    @PostMapping("/missions/{id}/deleteHarbinger")
    public String deleteHarbingerFromMission(@PathVariable("id") Long missionId, @RequestParam("harbingerId") Long harbingerId, Model model) {
        Mission mission = missionRepository.findById(missionId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid mission Id:" + missionId));

        Set<MissionEmployee> missionEmployees = mission.getMissionEmployees();

        long harbingerCount = missionEmployees.stream()
                .map(MissionEmployee::getEmployee)
                .filter(employee -> employee.getHarbinger() != null)
                .count();

        Long id = 0L;
        for (MissionEmployee missionEmployee : missionEmployees) {
            Employee employee = missionEmployee.getEmployee();
            if (employee.getHarbinger() != null) {
                id = employee.getHarbinger().getId();
                if (harbingerCount == 1) {
                    return "redirect:/missions/" + missionId + "/assigned";
                }
            }
            if (id.equals(harbingerId)) {
                missionEmployeeRepository.delete(missionEmployee);
                mission.getMissionEmployees().remove(missionEmployee);
                break;
            }
        }

        missionRepository.save(mission);

        return "redirect:/missions/" + missionId + "/assigned";
    }

}
