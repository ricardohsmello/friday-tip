package br.com.ricas;

import org.springframework.stereotype.Service;

@Service
public class TipService {

    private final TipRepository tipRepository;


    public TipService(TipRepository tipRepository) {
        this.tipRepository = tipRepository;
    }

    public Tip save(Tip tip) {
        return tipRepository.save(tip);
    }
}
