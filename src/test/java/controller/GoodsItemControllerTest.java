package controller;

import config.GuiceModule;
import com.google.gson.Gson;
import com.google.inject.Guice;
import com.google.inject.Injector;
import entity.CoreEntity;
import entity.GoodsEntity;
import org.apache.commons.io.IOUtils;
import org.junit.Before;
import org.junit.Test;
import reader.WebPageReader;
import response.GoodsResponse;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.isEmptyOrNullString;
import static org.hamcrest.core.IsNot.not;


public class GoodsItemControllerTest {

    private List<CoreEntity> goodsEntityList = new ArrayList<>();

    private GoodsItemController goodsItemController = null;

    private WebPageReader webPageReader = null;

    private Gson gson = null;

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

        goodsItemController = injector.getInstance(GoodsItemController.class);
        gson = injector.getInstance(Gson.class);
        webPageReader = injector.getInstance(WebPageReader.class);

    }

    @Test
    public void getGoodsItemsAsJsonHasCorrectContent() throws Exception {

        GoodsResponse goodsResponse = new GoodsResponse();
        goodsResponse.setResults(goodsEntityList);
        goodsResponse.setTotal("£4.90");
        String testJsonGoods = gson.toJson(goodsResponse);

        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        InputStream resourceStream = loader.getResourceAsStream("TestGoodsData.html");
        String webPageString = IOUtils.toString(resourceStream,"UTF-8");

        webPageReader.readInGoodsData(webPageString, "product");

        String goodsRetrievedAsJSon = goodsItemController.getItemsAsJSon();

        assertThat(goodsRetrievedAsJSon, is(not(isEmptyOrNullString())));

        assertThat(testJsonGoods, is(equalTo(goodsRetrievedAsJSon)));

    }
}