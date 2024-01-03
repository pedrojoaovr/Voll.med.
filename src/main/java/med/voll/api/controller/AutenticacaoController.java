package med.voll.api.controller;

import jakarta.validation.Valid;
import med.voll.api.infra.security.DadosTokenJWT;
import med.voll.api.infra.security.TokenService;
import med.voll.api.usuario.DadosAutenticacao;
import med.voll.api.usuario.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class AutenticacaoController {

    @Autowired
    private AuthenticationManager manager;
    @Autowired
    private TokenService service;
    @PostMapping
    public ResponseEntity efetuarLoogin(@RequestBody @Valid DadosAutenticacao dados){
        var authenticationTokentoken = new UsernamePasswordAuthenticationToken(dados.login(), dados.senha());
        var authenticationToken = manager.authenticate(authenticationTokentoken);
        var tokenJWT = service.gerarToken((Usuario) authenticationToken.getPrincipal());
       return ResponseEntity.ok(new DadosTokenJWT(tokenJWT));
    }
}
