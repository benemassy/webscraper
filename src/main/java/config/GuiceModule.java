package config;

import collector.GoodsCollector;
import collector.HTMLGoodsCollector;
import com.google.inject.AbstractModule;
import com.google.inject.TypeLiteral;
import com.google.inject.name.Names;
import dao.DataStore;
import dao.DataStoreImpl;
import entity.CoreEntity;
import reader.WebPageReader;
import reader.WebPageReaderImpl;
import service.CoreService;
import service.CoreServiceImpl;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class GuiceModule extends AbstractModule {

    @Override
    protected void configure() {

        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        Properties properties = new Properties();
        try {
            InputStream resourceStream = loader.getResourceAsStream("config.properties");
            properties.load(resourceStream);
            Names.bindProperties(binder(), properties);

        } catch (IOException ioe) {
            System.out.println("Unable to configure Dependency Injection for application - " + ioe.getLocalizedMessage());
        }

        bind(new TypeLiteral<CoreService<CoreEntity>>(){}).to(CoreServiceImpl.class);
        bind(GoodsCollector.class).to(HTMLGoodsCollector.class);
        bind(WebPageReader.class).to(WebPageReaderImpl.class);
        bind(DataStore.class).to(DataStoreImpl.class);

    }
}



