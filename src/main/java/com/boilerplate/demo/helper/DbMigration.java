package com.boilerplate.demo.helper;

import org.flywaydb.core.api.MigrationVersion;
import org.flywaydb.core.api.migration.JavaMigration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public abstract class DbMigration  implements JavaMigration {
    private final Logger logger = LoggerFactory.getLogger(DbMigration.class);

    private final MigrationVersion version;
    private Properties apiProperties;

    public DbMigration(String version) {
        this.version = MigrationVersion.fromVersion(version);
    }

    @Override
    public MigrationVersion getVersion() {
        return version;
    }

    @Override
    public Integer getChecksum() {
        return null;
    }

    @Override
    public boolean isUndo() {
        return false;
    }

    @Override
    public boolean canExecuteInTransaction() {
        return true;
    }

    protected String getApiProperty(String name) {
        if (apiProperties == null) {
            loadApiProperties();
        }
        return apiProperties.getProperty(name);
    }

    private void loadApiProperties() {
        apiProperties = new Properties();
        try (final InputStream stream = this.getClass().getResourceAsStream("/api.properties")) {
            apiProperties.load(stream);
        } catch (IOException e) {
            logger.error(null, e);
        }
    }
}
