package br.com.welisson.atm.restapi;

import br.com.welisson.atm.domain.*;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/atm")
@AllArgsConstructor
public class ATMController extends AbstractController {

    private final ATMService atmService;

    @PostMapping(value="/login")
    public Client login(@RequestBody final Login login){
        return atmService.login(login.getAccount(), login.getPassword());
    }

    @PostMapping(value="/withdraw")
    public Money withdraw(@RequestBody final WithdrawVO withdrawVO){
        return atmService.withdraw(withdrawVO.getIdClient(), withdrawVO.getValue());
    }

}