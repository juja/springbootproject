package opensarasa.controller;

import opensarasa.entity.Localidad;
import opensarasa.repository.LocalidadRepository;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class LocalidadController {

	@Autowired
    private LocalidadRepository localidadReposotory;

    @RequestMapping(value = "/localidad", method = RequestMethod.GET)
    public String list(Model model){
        model.addAttribute("localidades", localidadReposotory.findAll());
        model.addAttribute("pueblos", localidadReposotory.findByTipo(Localidad.Tipo.PUEBLO));
        model.addAttribute("ciudades", localidadReposotory.findByTipo(Localidad.Tipo.CIUDAD));
        return "localidad/index";
    }

    @RequestMapping("localidad/{id}")
    public String showLocalidad(@PathVariable Integer id, Model model){
        model.addAttribute("localidad", localidadReposotory.findOne(id));
        return "localidad/show";
    }

    @RequestMapping("localidad/edit/{id}")
    public String edit(@PathVariable Integer id, Model model){
        model.addAttribute("localidad", localidadReposotory.findOne(id));
        return "localidad/new";
    }

    @RequestMapping("localidad/new")
    public String newLocalidad(Model model){
        model.addAttribute("localidad", new Localidad());
        return "localidad/new";
    }

    @RequestMapping(value = "localidad", method = RequestMethod.POST)
    public String saveLocalidad(Localidad localidad){

        localidadReposotory.save(localidad);

        return "redirect:/localidad/" + localidad.getId();
    }

}
