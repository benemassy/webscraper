package reader;

import com.google.inject.Key;
import com.google.inject.TypeLiteral;
import entity.CoreEntity;
import entity.GoodsEntity;
import com.google.inject.Guice;
import com.google.inject.Injector;
import collector.GoodsCollector;
import config.GuiceModule;
import collector.HTMLGoodsCollector;
import collector.WebPageConfigFactory;
import org.apache.commons.io.IOUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import service.CoreService;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.hasSize;

public class WebPageReaderTest {

    private List<CoreEntity> goodsEntityList = new ArrayList<>();

    private CoreService<CoreEntity> coreService;

    private WebPageConfigFactory<GoodsCollector> configFactory = null;

    private WebPageReader webPageReader = null;

    @Before
    public void setup() {

        GoodsEntity goodsEntity1 = new GoodsEntity();
        goodsEntity1.setDescription("descOne");
        goodsEntity1.setSize("1.1");
        goodsEntity1.setTitle("title one");
        goodsEntity1.setUnitPrice("2.1");

        GoodsEntity goodsEntity2 = new GoodsEntity();
        goodsEntity2.setDescription("descTwo");
        goodsEntity2.setSize("1.2");
        goodsEntity2.setTitle("title two");
        goodsEntity2.setUnitPrice("2.2");

        GoodsEntity goodsEntity3 = new GoodsEntity();
        goodsEntity3.setDescription("descThree");
        goodsEntity3.setSize("1.3");
        goodsEntity3.setTitle("title three");
        goodsEntity3.setUnitPrice("2.3");

        goodsEntityList.add(goodsEntity1);
        goodsEntityList.add(goodsEntity2);
        goodsEntityList.add(goodsEntity3);

        Collections.sort(goodsEntityList, (entityFirst, entitySecond) -> entityFirst.getDescription().compareTo(entitySecond.getDescription()));

        Injector injector = Guice.createInjector(new GuiceModule());

        coreService = injector.getInstance(Key.get(new TypeLiteral<CoreService<CoreEntity>>(){}));
        configFactory = injector.getInstance(Key.get(new TypeLiteral<WebPageConfigFactory<GoodsCollector>>(){}));
        webPageReader = injector.getInstance(WebPageReader.class);

        configFactory.registerConfig(GoodsCollector.FORMAT.HTML.toString(), new HTMLGoodsCollector());

    }

    @After
    public void tearDown() {

        configFactory = null;
        coreService.deleteAllItems();

    }

    @Test
    public void readItemsDataCountIsCorrect() throws IOException {

        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        InputStream resourceStream = loader.getResourceAsStream("TestGoodsData.html");
        String webPageString = IOUtils.toString(resourceStream,"UTF-8");

        webPageReader.readInGoodsData(webPageString, "product");

        Collection<CoreEntity> items = coreService.getAllItems(CoreEntity.class);

        assertThat(items, hasSize(goodsEntityList.size()));

    }

}
