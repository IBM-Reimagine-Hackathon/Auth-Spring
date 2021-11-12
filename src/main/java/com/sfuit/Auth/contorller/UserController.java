package com.sfuit.Auth.contorller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.sfuit.Auth.Constants;
import com.sfuit.Auth.entity.User;
import com.sfuit.Auth.services.EmailSenderService;
import com.sfuit.Auth.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/users")

public class UserController{

    @Autowired
    UserService userService;

    @Autowired
    EmailSenderService emailSenderService;

    @Value("${jwt.secret}")
    private String secret;

    @PostMapping("/login")
    public ResponseEntity<Map<String, String >> loginUser(@RequestBody Map<String, Object> userMap)
    {

        String email = (String) userMap.get("email");
        String password = (String) userMap.get("password");
        User user = userService.validateUser(email, password);
        return new ResponseEntity<>(generateLoginJWTToken(user), HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> registerUser(@RequestBody Map<String, Object> userMap)
    {
        String name = (String) userMap.get("name");
        String email = (String) userMap.get("email");
        String dob = (String) userMap.get("dob");
        String phone = (String) userMap.get("phone");
        String password = (String) userMap.get("password");
        String otp = Integer.toString(rand_otp());

        Algorithm algorithm = Algorithm.HMAC256(Constants.API_SECRET_KEY);
        String token = JWT.create()
                .withIssuer("auth0")
                .withClaim("name",name)
                .withClaim("email",email)
                .withClaim("dob",dob)
                .withClaim("phone",phone)
                .withClaim("password",password)
                .withExpiresAt(new Date(System.currentTimeMillis() + Constants.TOKEN_VALIDITY))
                .sign(algorithm);

        String is_verified = "false";
        String device_id  = (String) userMap.get("device_id");
        User user= userService.registerUser(name, email, dob, phone, password, otp, token, is_verified, device_id);
        triggerMail(email, name, otp);
        return new ResponseEntity<>(generateJWTToken(user), HttpStatus.OK);
    }

    @PostMapping("/verification")
    public ResponseEntity<Map<String, String>> verifyUser(@RequestBody Map<String, Object> userMap)
    {
        String email = (String) userMap.get("email");
        String otp = (String) userMap.get("otp");
        User user = userService.verifyUser(email, otp);
        return new ResponseEntity<>(generateJWTToken(user), HttpStatus.OK);
    }

    //JWT token is created, user is parameter and returns map<string,string>
    private Map<String, String > generateJWTToken(User user)
    {

        Map<String, String> map = new HashMap<>();
            Algorithm algorithm = Algorithm.HMAC256(Constants.API_SECRET_KEY);
            String token = JWT.create()
                    .withIssuer("auth0")
                    .withClaim("email",user.getEmail())
                    .withClaim("name",user.getName())
                    .withClaim("dob",user.getDob())
                    .withClaim("phone",user.getPhone())
                    .withClaim("password",user.getPassword())
                    .withExpiresAt(new Date(System.currentTimeMillis() + Constants.TOKEN_VALIDITY))
                    .sign(algorithm);

            map.put("token", token);
            return map;
    }

    private Map<String, String > generateLoginJWTToken(User user)
    {

        Map<String, String> map = new HashMap<>();
        Algorithm algorithm = Algorithm.HMAC256(Constants.API_SECRET_KEY);
        String token = JWT.create()
                .withIssuer("auth0")
                .withClaim("email",user.getEmail())
                .withClaim("name",user.getName())
                .withClaim("dob",user.getDob())
                .withClaim("phone",user.getPhone())
                .withClaim("password",user.getPassword())
                .withIssuedAt(new Date((System.currentTimeMillis())))
                .withExpiresAt(new Date(System.currentTimeMillis() + Constants.UPDATED_TOKEN_VALIDITY))
                .sign(algorithm);

        //Token token_updated = userService.addToken(user.getEmail(), token, user.getDevice_id());

        map.put("name",user.getName());
        map.put("email",user.getEmail());
        map.put("dob",user.getDob());
        map.put("phone", user.getPhone());
        map.put("device_id", user.getDevice_id());
        map.put("token", token);
        return map;
    }

    //generating random 4 digit otp
    private int rand_otp()
    {
        int min = 1000;
        int max = 9999;
        return ((int)Math.floor(Math.random()*(max-min+1)+min));
    }


    public void triggerMail(String email, String name, String otp)
    {
        emailSenderService.sendSimpleEmail(email, "Hi "+name+"\n"+otp+"This is your otp \n Valid for 5 minutes only", "OTP Verification");
    }
}
