package restaurante.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import restaurante.service.ImagemService;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Controller
@RequestMapping("/imagem")
public class ImagemController {

    @Autowired
    private ImagemService imagemService;

    @GetMapping(path = "/{nomeArquivo:.+}")
    public ResponseEntity<Resource> carregarImagem(@PathVariable String nomeArquivo, RedirectAttributes ra) {
        Resource resource = imagemService.buscarArquivo(nomeArquivo);
        String contentType = null;
        try {
            Path path = new File(resource.getFile().getAbsolutePath()).toPath();
            contentType = Files.probeContentType(path);
        } catch (IOException ex) {
            ra.addFlashAttribute("erro", "A Imagem não foi encontrada.");
        }
        if (contentType == null) {
            contentType = "application/octet-stream";
        }
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .body(resource);
    }

    @PostMapping(value = "/armazenar-imagem", consumes = "multipart/form-data")
    public ModelAndView armazenarImagem(@RequestParam("idCardapioItem") Long idCardapioItem, @RequestParam("files[]") MultipartFile[] arquivos) {
        imagemService.armazenarImagem(idCardapioItem, arquivos);
        ModelAndView mv = new ModelAndView("redirect:/cardapio-item/visualizar/" + idCardapioItem);
        return mv;
    }

}
