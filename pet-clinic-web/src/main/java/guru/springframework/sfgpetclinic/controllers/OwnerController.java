package guru.springframework.sfgpetclinic.controllers;

import guru.springframework.sfgpetclinic.model.Owner;
import guru.springframework.sfgpetclinic.services.OwnerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Collection;
import java.util.List;

/**
 * Created by jt on 7/22/18.
 */
@RequestMapping("/owners")
@Controller
public class OwnerController {

    private final OwnerService ownerService;

    public OwnerController(OwnerService ownerService) {
        this.ownerService = ownerService;
    }

    @InitBinder
    public void setAllowedFields(WebDataBinder dataBinder) {
        dataBinder.setDisallowedFields("id");
    }

    @GetMapping("/{id}")
    public ModelAndView displayOwner(@PathVariable Long id) {

        ModelAndView mav = new ModelAndView("owners/ownerDetails");
        mav.addObject("owner", ownerService.findById(id));

        return mav;
    }

    @GetMapping("/find")
    public String findOwners(Model model) {
        model.addAttribute("owner", Owner.builder().build());
        return "owners/findOwners";
    }

    @GetMapping()
    public String processFindForm(Owner owner, BindingResult result, Model model) {

        if (owner.getLastName() == null) {
            owner.setLastName("");
        }

        List<Owner> results = ownerService.findAllByLastNameLike("%" + owner.getLastName() + "%");

        if (results.isEmpty()) {
            //no owners found
            result.rejectValue("lastName", "notFound", "not found");
            return "owners/findOwners";
        } else if (results.size() == 1) {
            //one owner found
            owner = results.get(0);
            return "redirect:/owners/" + owner.getId();
        } else {
            //multiple owners found
            model.addAttribute("selections", results);
            return "owners/ownersList";
        }
    }

    @GetMapping("/new")
    public String addNewOwner(Model model) {
        Owner owner = Owner.builder().build();
        model.addAttribute("owner", owner);
        return "owners/createOrUpdateOwnerForm";
    }

    @PostMapping("/new")
    public String processNewOwner(@ModelAttribute Owner owner) {
        Owner savedOwner = ownerService.save(owner);
        return String.format("redirect:/owners/%d", savedOwner.getId());
    }

    @GetMapping("/{id}/update")
    public String updateOwner(Model model, @PathVariable Long id) {
        Owner owner = ownerService.findById(id);

        if (owner != null) {
            model.addAttribute("owner", owner);
            return "owners/createOrUpdateOwnerForm";
        } else {
            throw new IllegalArgumentException(String.format("Owner with id: %d was not found", id));
        }
    }

    @PostMapping("/{id}/update")
    public String processUpdateOwner(@ModelAttribute Owner owner, @PathVariable Long id) {
        owner.setId(id);
        Owner savedOwner = ownerService.save(owner);
        return String.format("redirect:/owners/%d", savedOwner.getId());
    }
}
