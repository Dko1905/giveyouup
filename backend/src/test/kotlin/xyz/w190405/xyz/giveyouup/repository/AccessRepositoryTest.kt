package xyz.w190405.xyz.giveyouup.repository

import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import xyz.w190405.xyz.giveyouup.domain.config.Access
import java.lang.Exception
import java.time.Instant

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class AccessRepositoryTest(@Autowired private val accessRepository: AccessRepository) {
    val cleanupArr: ArrayList<Long> = ArrayList()

    @Test
    fun `Add access to repository`(){
        val a = Access(null, Instant.now(), "192.168.1.1")

        a.ID = accessRepository.addAccess(a)

        cleanupArr.add(a.ID!!)
    }

    @Test
    fun `Add access and then check if it is in the getAccesses`(){
        val a = Access(null, Instant.now(), "192.168.1.2")

        a.ID = accessRepository.addAccess(a)

        var found = false
        for(access in accessRepository.getAccesses()){
            if(access.ID == a.ID){
                found = true
                Assertions.assertEquals(access.DATE.epochSecond, a.DATE.epochSecond)
                Assertions.assertEquals(access.IP, a.IP)
            }
        }

        if(!found) {
            throw Exception("Failed to find access in repository")
        } else{
            cleanupArr.add(a.ID!!)
        }
    }

    @Test
    fun `Add access, then try to remove, and then check if it still is there`(){
        val a = Access(null, Instant.now(), "192.168.1.3")

        a.ID = accessRepository.addAccess(a)

        accessRepository.removeAccess(a.ID!!)

        var found = false
        for(access in accessRepository.getAccesses()){
            if(access.ID == a.ID){
                found = true
                Assertions.assertEquals(access.DATE.epochSecond, a.DATE.epochSecond)
                Assertions.assertEquals(access.IP, a.IP)
            }
        }

        if(found) {
            cleanupArr.add(a.ID!!)
            throw Exception("Failed to delete access in repository")
        }
    }

    @AfterAll
    fun `clear inserted ids`(){
        for(id in cleanupArr){
            accessRepository.removeAccess(id)
        }
    }
}