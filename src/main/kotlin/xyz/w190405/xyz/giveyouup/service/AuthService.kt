package xyz.w190405.xyz.giveyouup.service

import org.springframework.stereotype.Service

@Service
class AuthService {
    private final val validArr: ArrayList<String> = ArrayList()

    init {
        validArr.add("OQuD5uHju0JrywXX8g9rOQuD5uHju0JrywXX8g9r")
    }

    fun checkToken(token: String): Boolean{
        var valid = false;
        for(token2: String in validArr){
            if(token2 == token){
                valid = true;
            }
        }

        return valid;
    }
}