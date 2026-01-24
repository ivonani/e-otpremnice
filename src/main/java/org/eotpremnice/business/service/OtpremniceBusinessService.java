package org.eotpremnice.business.service;

import lombok.RequiredArgsConstructor;
import org.eotpremnice.service.FirmaService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class OtpremniceBusinessService {

    private final FirmaService firmaService;
}
