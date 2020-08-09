package xyz.w190405.xyz.giveyouup.service

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

@Service
class AuthService {
    private final val validArr: ArrayList<String> = ArrayList()

    init {
        val keys: String = System.getenv("TOKENS") ?: "1234"

        for(key in keys.split(',')){
            validArr.add(key)
        }
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