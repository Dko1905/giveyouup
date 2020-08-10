package xyz.w190405.xyz.giveyouup.repository

import xyz.w190405.xyz.giveyouup.domain.config.Access
import java.sql.SQLException
import java.time.Instant
import javax.sql.DataSource

class AccessRepositoryImpl(val ds: DataSource) : AccessRepository {
    override fun addAccess(access: Access): Long {
        var id: Long? = null;
        ds.connection.use { connection ->
            connection.prepareStatement("INSERT INTO ACCESS ( DATE, IP ) VALUES ( ?, ? );").use { preparedStatement ->
                preparedStatement.setLong(1, access.DATE.epochSecond)
                preparedStatement.setString(2, access.IP)

                preparedStatement.execute()
            }
            connection.prepareStatement("SELECT ID FROM ACCESS WHERE DATE=? AND IP=?;").use { preparedStatement ->
                preparedStatement.setLong(1, access.DATE.epochSecond)
                preparedStatement.setString(2, access.IP)

                preparedStatement.executeQuery().use { resultSet ->
                    if (resultSet.next()){
                        id = resultSet.getLong(1)
                    }
                }
            }
        }
        return id ?: throw SQLException("No item found")
    }

    override fun removeAccess(id: Long) {
        ds.connection.use { connection ->
            connection.prepareStatement("DELETE FROM ACCESS WHERE ID=?;").use { preparedStatement ->
                preparedStatement.setLong(1, id)

                preparedStatement.execute()
            }
        }
    }

    override fun getAccesses(): ArrayList<Access> {
        val arr: ArrayList<Access> = ArrayList()

        try{
            ds.connection.use { connection ->
                connection.prepareStatement("SELECT ID,DATE,IP FROM ACCESS;").use { preparedStatement ->
                    preparedStatement.executeQuery().use { resultSet ->
                        while (resultSet.next()){
                            val ID: Long = resultSet.getLong(1)
                            val DATE: Long = resultSet.getLong(2)
                            val IP: String = resultSet.getString(3)

                            arr.add(Access(ID, Instant.ofEpochSecond(DATE), IP))
                        }
                    }
                }
            }
        }
        catch (e: Exception){
            System.err.println("Error in getAccesses: "+e.message)
            throw SQLException(e)
        }

        return arr
    }

}