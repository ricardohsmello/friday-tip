package br.com.ricas;

import org.springframework.stereotype.Service;

@Service
public class TipService {

    private final TipRepository tipRepository;


    public TipService(TipRepository tipRepository) {
        this.tipRepository = tipRepository;
    }

    public FridayTip save(FridayTip fridayTip) {
        return tipRepository.save(fridayTip);
    }
}
