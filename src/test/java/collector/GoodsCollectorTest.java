package collector;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Key;
import com.google.inject.TypeLiteral;
import config.GuiceModule;
import entity.CoreEntity;
import entity.GoodsEntity;
import org.apache.commons.io.IOUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import response.GoodsResponse;
import service.CoreService;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;


public class GoodsCollectorTest {

    private List<CoreEntity> goodsEntityList = new ArrayList<>();

    private CoreService<CoreEntity> coreService;

    private WebPageConfigFactory<GoodsCollector> configFactory = null;

    private GoodsCollector htmlGoodsCollector = null;

    @Before
    public void setup() {

        GoodsEntity goodsEntity1 = new GoodsEntity();
        goodsEntity1.setDescription("Avocado description...");
        goodsEntity1.setSize("0.00kb");
        goodsEntity1.setTitle("Avocado Ripe");
        goodsEntity1.setUnitPrice("1.80");

        GoodsEntity goodsEntity2 = new GoodsEntity();
        goodsEntity2.setDescription("Grapefruit description...");
        goodsEntity2.setSize("0.00kb");
        goodsEntity2.setTitle("Grapefruit");
        goodsEntity2.setUnitPrice("1.00");

        GoodsEntity goodsEntity3 = new GoodsEntity();
        goodsEntity3.setDescription("Watermelon description...");
        goodsEntity3.setSize("0.00kb");
        goodsEntity3.setTitle("Watermelon");
        goodsEntity3.setUnitPrice("2.10");

        goodsEntityList.add(goodsEntity1);
        goodsEntityList.add(goodsEntity2);
        goodsEntityList.add(goodsEntity3);

        Collections.sort(goodsEntityList, (entityFirst, entitySecond) -> entityFirst.getDescription().compareTo(entitySecond.getDescription()));

        Injector injector = Guice.createInjector(new GuiceModule());

        coreService = injector.getInstance(Key.get(new TypeLiteral<CoreService<CoreEntity>>(){}));
        configFactory = injector.getInstance(Key.get(new TypeLiteral<WebPageConfigFactory<GoodsCollector>>(){}));
        configFactory.registerConfig(GoodsCollector.FORMAT.HTML.toString(), new HTMLGoodsCollector());
        htmlGoodsCollector = configFactory.getConfig(GoodsCollector.FORMAT.HTML.toString());

    }

    @After
    public void tearDown() {

        htmlGoodsCollector = null;
        configFactory = null;
        coreService.deleteAllItems();

    }

    @Test
    public void countOfGoodsEntitiesCollectedIsCorrect() throws IOException {

        GoodsResponse goodsResponse = new GoodsResponse();
        goodsResponse.setResults(goodsEntityList);
        goodsResponse.setTotal("TestTotal");

        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        InputStream resourceStream = loader.getResourceAsStream("TestGoodsData.html");
        String webPageString = IOUtils.toString(resourceStream,"UTF-8");

        List<CoreEntity> collectedGoodsEntities = htmlGoodsCollector.collectGoodsFrom(webPageString, "product");

        assertThat(collectedGoodsEntities, hasSize(goodsEntityList.size()));

    }

    @Test
    public void goodsEntitiesCollectedHasValidContent() throws IOException {

        GoodsResponse goodsResponse = new GoodsResponse();
        goodsResponse.setResults(goodsEntityList);
        goodsResponse.setTotal("TestTotal");

        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        InputStream resourceStream = loader.getResourceAsStream("TestGoodsData.html");
        String webPageString = IOUtils.toString(resourceStream,"UTF-8");

        List<CoreEntity> collectedGoodsEntities = htmlGoodsCollector.collectGoodsFrom(webPageString, "product");

        assertThat(collectedGoodsEntities.get(0).getDescription(), is(equalTo(goodsEntityList.get(0).getDescription())));
        assertThat(collectedGoodsEntities.get(0).getSize(), is(equalTo(goodsEntityList.get(0).getSize())));
        assertThat(collectedGoodsEntities.get(0).getTitle(), is(equalTo(goodsEntityList.get(0).getTitle())));
        assertThat(collectedGoodsEntities.get(0).getUnitPrice(), is(equalTo(goodsEntityList.get(0).getUnitPrice())));

    }

}
