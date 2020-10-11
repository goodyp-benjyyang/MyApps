package top.goodyp.springboot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import top.goodyp.springboot.dto.AccessTokenDTO;
import top.goodyp.springboot.dto.GithubUser;
import top.goodyp.springboot.provider.GithubProvider;

@Controller
public class AuthorizeController {
    @Autowired
    private GithubProvider githubProvider;


    @GetMapping("/callback")
    public String callback(@RequestParam(name="code")String code,
                           @RequestParam(name="state")String state){
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setRedirect_uri("http://localhost:8899/callback");
        accessTokenDTO.setCode(code);
        accessTokenDTO.setState(state);
        accessTokenDTO.setClient_id("193299d96df4b9e72db1");
        accessTokenDTO.setClient_secret("6da7cc20ee647d62fc53fd1f9300d78c813fbb29");
        String accessToken = githubProvider.getAccessToken( accessTokenDTO );


        GithubUser user = githubProvider.getUser(accessToken);
        System.out.println( user.getName() );
        return "index";
    }
}
