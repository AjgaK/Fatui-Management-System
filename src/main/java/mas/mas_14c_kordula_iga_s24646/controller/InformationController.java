package mas.mas_14c_kordula_iga_s24646.controller;

import mas.mas_14c_kordula_iga_s24646.model.*;
import mas.mas_14c_kordula_iga_s24646.repository.RegionRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Set;

/**
 * Controller responsible for handling operations related to region information.
 */
@Controller
public class InformationController {
    private RegionRepository regionRepository;

    /**
     * Constructor for the controller, initializes the region repository.
     *
     * @param regionRepository the region repository
     */
    public InformationController(RegionRepository regionRepository) {
        this.regionRepository = regionRepository;
    }

    /**
     * Handles GET requests for displaying all regions.
     *
     * @param model the model used to pass data to the view
     * @return the name of the view to display
     */
    @GetMapping("/information")
    public String showAllRegions(Model model) {
        if(regionRepository.count() == 0) {
            model.addAttribute("regions", null);
        } else {
            model.addAttribute("regions", regionRepository.findAll());
        }
        return "information";
    }

    /**
     * Handles GET requests for displaying details of a specific region.
     *
     * @param id    the region ID
     * @param model the model used to pass data to the view
     * @return the name of the view to display
     */
    @GetMapping("information/{id}")
    public String getRegionDetails(@PathVariable("id") Long id, Model model) {
        Region region = regionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid region Id:" + id));
        Set<God> gods = region.getGods();
        Set<Leader> leaders = region.getLeaders();
        model.addAttribute("region", region);
        model.addAttribute("gods", gods);
        model.addAttribute("leaders", leaders);

        return "region";
    }
}
