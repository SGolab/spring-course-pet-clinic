package guru.springframework.sfgpetclinic.controllers;

import guru.springframework.sfgpetclinic.model.Owner;
import guru.springframework.sfgpetclinic.model.PetType;
import guru.springframework.sfgpetclinic.services.OwnerService;
import guru.springframework.sfgpetclinic.services.PetService;
import guru.springframework.sfgpetclinic.services.PetTypeService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RequestMapping("owners/{ownerId}/pets")
@Controller
public class PetController {

    private final PetService petService;
    private final PetTypeService petTypeService;
    private final OwnerService ownerService;

    public PetController(PetService petService, PetTypeService petTypeService, OwnerService ownerService) {
        this.petService = petService;
        this.petTypeService = petTypeService;
        this.ownerService = ownerService;
    }

    @ModelAttribute("types")
    public Collection<PetType> populatePetTypes() {
        return petTypeService.findAll();
    }

    @ModelAttribute("owner")
    public Owner findOwner(@PathVariable("ownerId") Long ownerId) {
        return ownerService.findById(ownerId);
    }

    @InitBinder("owner")
    public void initOwnerBinder(WebDataBinder dataBinder) {
        dataBinder.setDisallowedFields("id");
    }

//    @GetMapping("/{petId}")
//    public String displayPet(@PathVariable String petId, @PathVariable String ownerId) {
//
//        return "";
//    }
//
//    @GetMapping("/new")
//    public String addNewPet(@PathVariable String ownerId) {
//
//        return "";
//    }
//
//    @PostMapping("/new")
//    public String processNewPetForm(@PathVariable String ownerId) {
//
//        return "";
//    }
//
//    @GetMapping("{petId}/update")
//    public String updatePet(@PathVariable String ownerId, @PathVariable String petId) {
//
//        return "";
//    }




}
