package com.bank_transaction.utils;

import com.bank_transaction.models.dto.TransactionDto;
import com.bank_transaction.models.entities.Referentiel;
import com.bank_transaction.models.entities.Transaction;
import com.bank_transaction.repository.ReferentielRepository;
import com.bank_transaction.repository.TransactionRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public final class TransactionsUtils {

    private static final Logger logger = LoggerFactory.getLogger(TransactionsUtils.class);

    private final TransactionRepository transactionRepository;
    private final ReferentielRepository referentielRepository;

    public TransactionsUtils(TransactionRepository transactionRepository, ReferentielRepository referentielRepository) {
        this.transactionRepository = transactionRepository;
        this.referentielRepository = referentielRepository;
    }

    public TransactionDto getTransactionDto(Transaction transact) {
        TransactionDto transactDto = new TransactionDto();

        transactDto.setPrimaryId(transact.getPrimaryId());
        transactDto.setSecondaryId(transact.getSecondaryId());
        transactDto.setEventType(transact.getEventType());
        transactDto.setEventDateTime(transact.getEventDateTime());
        if ( Objects.nonNull(transact.getReference())) {
            transactDto.setStepRank(transact.getReference().getStepRank());
            transactDto.setEventRank(transact.getReference().getEventRank());
        }
        return transactDto;
    }


    public void loadReferentielData() {

        try {

            InputStream is = getClass().getResourceAsStream("/data/referentiel.md");
            List<String> lines = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))
                    .lines()
                    .collect(Collectors.toList());


            List<Referentiel> refs = lines.stream().skip(2)
                    .filter(line -> line.trim().startsWith("|"))
                    .map(line -> line.split("\\|"))
                    .filter(parts -> parts.length >= 5)
                    .map(parts -> {
                        Referentiel ref = new Referentiel();
                        ref.setStepCode(parts[1].trim());
                        ref.setStepRank(Integer.parseInt(parts[2].trim()));
                        ref.setEventRank(Integer.parseInt(parts[3].trim()));
                        ref.setEventType(parts[4].trim());
                        return ref;
                    }).collect(Collectors.toList());

            for(Referentiel ref : refs){
                if(!this.referentielRepository.findByEventType(ref.getEventType()).isPresent()){
                    this.referentielRepository.save(ref);
                }
            }

        } catch (Exception e) {
            logger.error(Constants.EXCEPTION_LOADING_REFERENTIEL_DATA,e);
        }
    }

    public void loadTransactionData() {

        try (InputStream is= getClass().getResourceAsStream("/data/transactions.json")) {
            ObjectMapper mapper = new ObjectMapper();

            List<Map<String, Object>> raw = mapper.readValue(is, List.class);
            List<Transaction> batchOfTransaction = new ArrayList<>();

            for (Map<String, Object> entry : raw) {

                Transaction transact = new Transaction();
                transact.setPrimaryId((String) entry.get("primary_id"));
                transact.setSecondaryId((String) entry.get("secondary_id"));

                Map<String, String> event = (Map<String, String>) entry.get("event");
                String eventType = event.get("eventType");
                transact.setEventType(eventType);

                String rawDate = (String) entry.get("date");
                transact.setEventDateTime(parseDateSafe(rawDate));

                Referentiel ref = referentielRepository.findByEventType(eventType).orElse(null);
                transact.setReference(ref);

                batchOfTransaction.add(transact);

                if (batchOfTransaction.size() == 500) {
                    transactionRepository.saveAll(batchOfTransaction);
                    batchOfTransaction.clear();
                }
            }

            if (!batchOfTransaction.isEmpty()) {
                transactionRepository.saveAll(batchOfTransaction);
            }
        } catch (Exception e) {
            logger.error(Constants.EXCEPTION_LOADING_TRANSACTION_DATA,e);
        }
    }

    private LocalDateTime parseDateSafe(String raw) {

        DateTimeFormatter[] formatters = {
                DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"),
                DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm"),
        };

        for (DateTimeFormatter fmt : formatters) {
            try {
                return LocalDateTime.parse(raw, fmt);
            } catch (Exception e) {}
        }
        return null;
    }
}
