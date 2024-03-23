package com.mendusa.transactions.service.transactions;

import com.mendusa.transactions.dto.AppResponse;
import com.mendusa.transactions.dto.RecentTransactionResponse;
import com.mendusa.transactions.entity.Transaction;
import com.mendusa.transactions.repository.TransactionRepository;
import com.mendusa.transactions.service.email.EmailServiceImpl;
import com.mendusa.transactions.utils.TrUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.util.List;


@Slf4j
@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository repository;

    private final EmailServiceImpl emailService;


    @Override
    public AppResponse<List<RecentTransactionResponse>> getTransactionReceipt() {// todo: what transaction receipt?

        return new AppResponse<>(0, "Successful", getDescResponse());
    }

    @Override
    public AppResponse<String> getAndSendToMail() throws IllegalAccessException, MessagingException {
        // todo: this is not readable
        // todo: what is trutils??
        // todo: what descResponse are you getting
        // todo: why are you sending mail synchronously
        emailService.sendToEmail(TrUtils.writeToExcelSheet(getDescResponse()));

//        emailService.sendToEmail(TrUtils.writeToCsv(getDescResponse())); // todo: remove unused code or write a reason for commenting it

        return new AppResponse<>(0, "successful");

    }


    private List<RecentTransactionResponse> getDescResponse() {// todo: i told you of this name and you left it unattended
        List<Transaction> trans = repository.findAllByOrderByIdDesc();// todo: what is this for
        Page<RecentTransactionResponse> page = repository.findAll( //todo: this page variable is unneccesary
                PageRequest.of(0, 10, Sort.by(Sort.Direction.DESC, "id"))).map(RecentTransactionResponse::new);


        return page.getContent();
    }




}