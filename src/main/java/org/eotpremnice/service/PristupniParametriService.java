package org.eotpremnice.service;

import lombok.RequiredArgsConstructor;
import org.eotpremnice.model.PristupniParametri;
import org.eotpremnice.repository.PristupniParametriRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PristupniParametriService {

    private final PristupniParametriRepository repository;

    public PristupniParametri loadApiAccess() {
        return repository.load();
    }

}
