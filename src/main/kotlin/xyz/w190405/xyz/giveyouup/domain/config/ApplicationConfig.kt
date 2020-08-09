package xyz.w190405.xyz.giveyouup.domain.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.sqlite.SQLiteDataSource
import xyz.w190405.xyz.giveyouup.repository.AccessRepository
import xyz.w190405.xyz.giveyouup.repository.AccessRepositoryImpl
import javax.sql.DataSource

@Configuration
class ApplicationConfig {
    @Bean
    fun accessRepositoryProvider(ds: DataSource): AccessRepository {
        return AccessRepositoryImpl(ds)
    }

    @Bean
    fun dataSourceProvider(): DataSource {
        Class.forName("org.sqlite.JDBC")

        val ds = SQLiteDataSource()
        ds.url = "jdbc:sqlite:database.db"

        ds.connection.use{ connection ->
            connection.createStatement().use {
                it.execute("CREATE TABLE IF NOT EXISTS ACCESS" +
                        "(" +
                        "  ID INTEGER PRIMARY KEY," +
                        "  DATE INTEGER NOT NULL," +
                        "  IP TEXT NOT NULL" +
                        ");")
            }
        }
        return ds
    }
}