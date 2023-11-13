package restaurante.controller.api;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import restaurante.service.LocalidadeService;

import java.util.Map;

@RestController
@RequestMapping("/ajax")
@Tag(name = "Ajax Lista")
public class ApiAjaxController {

    @Autowired
    private LocalidadeService localidadeService;

    @GetMapping("/municipioEstado")
    public ResponseEntity<Map<Long, String>> retornaMunicipios(@RequestParam Long idEstado) {
        return new ResponseEntity<>(localidadeService.findMunicipioAsMapPerEstado(idEstado), HttpStatus.OK);
    }

    @GetMapping("/bairroMunicipio")
    public ResponseEntity<Map<Long, String>> retornaBairros(@RequestParam Long idMunicipio) {
        return new ResponseEntity<>(localidadeService.findBairroAsMapPerMunicipio(idMunicipio), HttpStatus.OK);
    }

}
