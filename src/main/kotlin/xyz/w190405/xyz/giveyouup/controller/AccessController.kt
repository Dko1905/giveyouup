package xyz.w190405.xyz.giveyouup.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.context.request.RequestContextHolder
import org.springframework.web.context.request.ServletRequestAttributes
import xyz.w190405.xyz.giveyouup.domain.config.Access
import xyz.w190405.xyz.giveyouup.service.AccessService
import xyz.w190405.xyz.giveyouup.service.AuthService
import java.time.Instant


@RestController
class AccessController(@Autowired val accessService: AccessService, @Autowired val authService: AuthService) {
    @CrossOrigin(origins = ["*"], allowedHeaders = ["*"])
    @PostMapping("/access")
    fun addAccess(): ResponseEntity<HttpStatus>{
        return try{
            val request = (RequestContextHolder.getRequestAttributes() as ServletRequestAttributes?)!!.request
            val ip: String = request.getHeader("X-Forwarded-For") ?: request.remoteAddr;

            accessService.addAccess(Access(null, Instant.now(), ip))
            println("AccessController, addAccess: "+Instant.now())
            ResponseEntity.ok(HttpStatus.OK)
        }
        catch (e: Exception){
            System.err.println("AccessController, addAccess: "+e.message)
            ResponseEntity.ok(HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }

    @GetMapping("/access")
    fun getAccess(): ResponseEntity<ArrayList<Access>>{
        try{
            val request = (RequestContextHolder.getRequestAttributes() as ServletRequestAttributes?)!!.request
            val loginToken: String? = request.getHeader("X-Auth-Token")
            if(loginToken == null){
                System.out.println("AccessController, getAccess: "+"Access Denied (No token)")
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null)
            } else{
                val valid = authService.checkToken(loginToken)
                if(!valid){
                    System.out.println("AccessController, getAccess: "+"Access Denied (Token wrong)")
                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null)
                } else{
                    val arr = accessService.getAccesses()
                    println("AccessController, getAccess: "+Instant.now())
                    return ResponseEntity.ok(arr)
                }
            }
        }
        catch (e: Exception){
            System.err.println("AccessController, getAccess: "+e.message)
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null)
        }
    }
}