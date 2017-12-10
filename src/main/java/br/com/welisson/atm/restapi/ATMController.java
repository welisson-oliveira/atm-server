package br.com.welisson.atm.restapi;

import br.com.welisson.atm.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/atm")
public class ATMController {

    @Autowired
    private ATMService atmService;

    @PostMapping(value="/login")
    @ResponseStatus(HttpStatus.OK)
    public Client login(@RequestBody final Login login){
        return atmService.login(login.getAccount(), login.getPassword());
    }

    @PostMapping(value="/withdraw")
    @ResponseStatus(HttpStatus.OK)
    public Money withdraw(@RequestBody final WithdrawVO withdrawVO){
        return atmService.withdraw(withdrawVO.getIdClient(), withdrawVO.getValue());
    }

}
