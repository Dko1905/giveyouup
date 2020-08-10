package xyz.w190405.xyz.giveyouup.repository

import org.springframework.stereotype.Repository
import xyz.w190405.xyz.giveyouup.domain.config.Access
import java.sql.SQLException

@Repository
interface AccessRepository {
    /**
     * Add Access to db
     * @return ID of the access
     * @exception SQLException Throws exception if there was any problems with the database
     */
    fun addAccess(access: Access): Long

    /**
     * Remove Access from db
     * @exception SQLException Throws exception if there was any problems with the database
     */
    fun removeAccess(id: Long)

    /**
     * Gets all Accesses from the db
     * @return Array of Access, may be zero long
     * @exception SQLException Throws exception if there was any problems with the database
     */
    fun getAccesses(): ArrayList<Access>
}