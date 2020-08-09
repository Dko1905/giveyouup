package xyz.w190405.xyz.giveyouup.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import xyz.w190405.xyz.giveyouup.domain.config.Access
import xyz.w190405.xyz.giveyouup.repository.AccessRepository

@Service
class AccessService(@Autowired private val accessRepository: AccessRepository) {


    fun addAccess(access: Access): Long{
        try{
            return accessRepository.addAccess(access)
        }
        catch (e: Exception){
            System.err.println("AccessService, addAccess: "+e.message)
            throw e
        }
    }

    fun removeAccess(id: Long){
        try{
            return accessRepository.removeAccess(id)
        }
        catch (e: Exception){
            System.err.println("AccessService, removeAccess: "+e.message)
            throw e
        }
    }

    fun getAccesses(): ArrayList<Access>{
        try{
            return accessRepository.getAccesses()
        }
        catch (e: Exception){
            System.err.println("AccessService, getAccesses: "+e.message)
            throw e
        }
    }
}