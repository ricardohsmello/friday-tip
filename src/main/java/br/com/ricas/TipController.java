package br.com.ricas;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.logging.Logger;

@RestController
@RequestMapping("/tips")
public class TipController {

    Logger logger = Logger.getLogger("TipController");

    private final TipService tipService;

    public TipController(TipService tipService) {
        this.tipService = tipService;
    }

    @PostMapping
    public ResponseEntity<Tip> addTip(@RequestBody Tip tip) {
        logger.info("Creating a new tip" + tip);
        return ResponseEntity.ok(tipService.save(tip));
    }
}
