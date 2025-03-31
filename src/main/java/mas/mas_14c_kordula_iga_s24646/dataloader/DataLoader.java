package mas.mas_14c_kordula_iga_s24646.dataloader;

import lombok.Builder;
import lombok.RequiredArgsConstructor;
import mas.mas_14c_kordula_iga_s24646.model.*;
import mas.mas_14c_kordula_iga_s24646.repository.*;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;

@Component
@RequiredArgsConstructor
public class DataLoader {
    //repositories
    private final LeaderRepository leaderRepository;
    private final GodRepository godRepository;
    private final SkillRepository skillRepository;
    private final TitleRepository titleRepository;
    private final RegionRepository regionRepository;
    private final MissionRepository missionRepository;
    private final TaskRepository taskRepository;
    private final TaskReportRepository taskReportRepository;
    private final ResourceRepository resourceRepository;
    private final EmployeeRepository employeeRepository;
    private final MissionEmployeeRepository missionEmployeeRepository;
    private final HarbingerRepository harbingerRepository;
    private final AgentRepository agentRepository;
    private final AgentTypeRepository agentTypeRepository;
    private final AgentTypeAgentRepository agentTypeAgentRepository;

    @EventListener
    public void atStart(ContextRefreshedEvent ev) throws SQLException {

        if(employeeRepository.count() == 0) {
            //building example data and saving
            Region fontaine = Region.builder().name("Fontaine").description("Country of Water").element("Hydro").logoPath("/images/fontaine_emblem.webp").build();
            Region sumeru = Region.builder().name("Sumeru").description("Country of Knowledge").element("Dendro").logoPath("/images/sumeru_emblem.webp").build();
            God focalors = God.builder().name("Focalors").age(700).element("Hydro").region(fontaine).build();
            Leader neuvillette = Leader.builder().name("Neuvillette").startDate(LocalDate.of(2023, 9, 22)).endDate(null).region(fontaine).build();
            Skill elementalNeuvi = Skill.builder().name("O Tears, I Shall Repay").description("Summons a Raging Waterfall.").leader(neuvillette).build();
            Title focalorsTitle1 = Title.builder().person(focalors).name("Regina of All Waters, Kindreds, Peoples, and Laws").build();
            Title focalorsTitle2 = Title.builder().person(focalors).name("God of Justice").build();
            Task exampleTask = Task.builder().description("Kill the Fontaine diplomats.").status("Started").deadline(LocalDate.of(2025, 6, 28)).build();
            Mission exampleMission = Mission.builder().startDate(LocalDate.of(2024, 8, 12)).deadline(LocalDate.of(2025, 10, 2)).description("Gnosis retrieval mission.").fund(900000).region(fontaine).build();
            Mission exampleMission2 = Mission.builder().startDate(LocalDate.of(2023, 5, 17)).deadline(LocalDate.of(2024, 12, 12)).description("Abducting some important person.").fund(30000).region(fontaine).build();
            TaskReport exampleReport = TaskReport.builder().startDate(LocalDate.of(2024, 8, 20)).details("Having some trouble.").task(exampleTask).mission(exampleMission).build();
            Resource exampleResource = Resource.builder().type("Weapons").quantity(20).mission(exampleMission).build();
            Harbinger exampleHarbinger = Harbinger.builder().title("Arlecchino").number(4).regionFrom(fontaine).performancePoints(20).build();
            Harbinger exampleHarbinger2 = Harbinger.builder().title("Dottore").number(2).regionFrom(sumeru).performancePoints(90).build();

            AgentType specialAgent = AgentType.builder().name("Special Agent").build();
            AgentType seniorAgent = AgentType.builder().name("Senior Agent").build();
            AgentType normalAgent = AgentType.builder().name("Normal").build();

            Agent exampleAgent = new Agent((long) 2, "Spy", 2, "Spying", null, null, null, null, exampleHarbinger);
            Agent exampleAgent2 = new Agent((long) 2, "Fighter", 20, "Fighting", null, null, null, null, exampleHarbinger2);
            Agent normal = new Agent((long) 2, "Fighter", 0, "Fighting", null, null, null, null, exampleHarbinger2);

            AgentTypeAgent exampleAgentTypeAgent = AgentTypeAgent.builder().agentType(specialAgent).agent(exampleAgent).build();
            exampleAgent.setAgentTypeAgents(Set.of(exampleAgentTypeAgent), 2, "Spying", null, null);

            AgentTypeAgent exampleAgentTypeAgent3 = AgentTypeAgent.builder().agent(normal).agentType(normalAgent).build();
            normal.setAgentTypeAgents(Set.of(exampleAgentTypeAgent3), 0, null, null, exampleAgent2);

            AgentTypeAgent exampleAgentTypeAgent2 = AgentTypeAgent.builder().agentType(seniorAgent).agent(exampleAgent2).build();
            AgentTypeAgent exampleAgentTypeAgent4 = AgentTypeAgent.builder().agentType(specialAgent).agent(exampleAgent2).build();
            exampleAgent2.setAgentTypeAgents(Set.of(exampleAgentTypeAgent2, exampleAgentTypeAgent4), 20, "Fighting", Set.of(normal), null);

            Employee exampleEmployee = Employee.builder().joiningDate(LocalDate.of(2023, 4, 16)).harbinger(exampleHarbinger).build();
            Employee exampleEmployee2 = Employee.builder().joiningDate(LocalDate.of(2023, 4, 16)).harbinger(null).agent(exampleAgent).build();
            Employee exampleEmployee3 = Employee.builder().joiningDate(LocalDate.of(2023, 5, 15)).agent(exampleAgent2).build();
            Employee normalEmp = Employee.builder().joiningDate(LocalDate.of(2024, 8, 12)).agent(normal).build();
            Employee dottore = Employee.builder().joiningDate(LocalDate.of(2022, 4, 5)).harbinger(exampleHarbinger2).build();
            MissionEmployee missionEmployee = MissionEmployee.builder().mission(exampleMission).employee(exampleEmployee).build();
            MissionEmployee missionEmployee2 = MissionEmployee.builder().mission(exampleMission).employee(exampleEmployee2).build();
            MissionEmployee missionEmployee3 = MissionEmployee.builder().mission(exampleMission).employee(exampleEmployee3).build();
            MissionEmployee missionEmployeeNormal = MissionEmployee.builder().mission(exampleMission).employee(normalEmp).build();
            MissionEmployee secondHArbingerMissionEmployee = MissionEmployee.builder().mission(exampleMission).employee(dottore).build();


            regionRepository.save(fontaine);
            regionRepository.save(sumeru);
            godRepository.save(focalors);
            leaderRepository.save(neuvillette);
            skillRepository.save(elementalNeuvi);
            titleRepository.save(focalorsTitle1);
            titleRepository.save(focalorsTitle2);

            taskRepository.save(exampleTask);
            missionRepository.save(exampleMission);
            missionRepository.save(exampleMission2);
            taskReportRepository.save(exampleReport);
            resourceRepository.save(exampleResource);

            harbingerRepository.save(exampleHarbinger);
            harbingerRepository.save(exampleHarbinger2);
            agentTypeRepository.save(specialAgent);
            agentTypeRepository.save(seniorAgent);
            agentTypeRepository.save(normalAgent);
            agentRepository.save(exampleAgent);
            agentRepository.save(exampleAgent2);
            agentRepository.save(normal);
            agentTypeAgentRepository.save(exampleAgentTypeAgent);
            agentTypeAgentRepository.save(exampleAgentTypeAgent2);
            agentTypeAgentRepository.save(exampleAgentTypeAgent3);
            agentTypeAgentRepository.save(exampleAgentTypeAgent4);

            employeeRepository.save(exampleEmployee);
            employeeRepository.save(exampleEmployee2);
            employeeRepository.save(exampleEmployee3);
            employeeRepository.save(normalEmp);
            employeeRepository.save(dottore);
            missionEmployeeRepository.save(missionEmployee);
            missionEmployeeRepository.save(missionEmployee2);
            missionEmployeeRepository.save(missionEmployee3);
            missionEmployeeRepository.save(missionEmployeeNormal);
            missionEmployeeRepository.save(secondHArbingerMissionEmployee);

//        exampleEmployee3.changeRole(null, exampleHarbinger2);
//        //exampleEmployee3.changeRole(null, null);
//        //exampleEmployee3.changeRole(exampleAgent, null);
//        //exampleEmployee3.changeRole(exampleAgent, exampleHarbinger2);
//        employeeRepository.save(exampleEmployee3);
//        // need a function to delete the remnants from the database, agent and agent type
//        agentRepository.delete(exampleAgent2);

//        exampleAgent2.checkSupervised();
//        agentRepository.save(exampleAgent2);
//        for(Agent agent : exampleAgent2.getSupervisedAgents()) {
//            agentRepository.save(agent);
//        }
        }
    }
}
