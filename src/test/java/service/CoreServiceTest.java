package service;

import config.GuiceModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Key;
import com.google.inject.TypeLiteral;
import dao.DataStore;
import entity.CoreEntity;
import entity.GoodsEntity;
import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;


public class CoreServiceTest {

    private CoreService<CoreEntity> coreService;

    private DataStore dataStore;

    private List<CoreEntity> goodsEntityList = new ArrayList<>();

    @Before
    public void setup(){

        Injector injector = Guice.createInjector(new GuiceModule());

        coreService = injector.getInstance(Key.get(new TypeLiteral<CoreService<CoreEntity>>(){}));
        dataStore = injector.getInstance(DataStore.class);

        GoodsEntity goodsEntity1 = new GoodsEntity();
        goodsEntity1.setDescription("descOne");
        goodsEntity1.setSize("1.1");
        goodsEntity1.setTitle("title one");
        goodsEntity1.setUnitPrice("2.1");

        goodsEntityList.add(goodsEntity1);

    }

    @After
    public void tearDown(){

        dataStore.deleteAll();

    }

    @Test
    public void addItemStoresAnEntity() {

        coreService.addItem(goodsEntityList.get(0));

        CoreEntity entity = dataStore.get(goodsEntityList.get(0).getDescription());

        assertThat(entity.getDescription(), Matchers.is(equalTo(goodsEntityList.get(0).getDescription())));

    }

    @Test
    public void getItemRetrivedsAnEntity() {

        coreService.addItem(goodsEntityList.get(0));

        CoreEntity entity = dataStore.get(goodsEntityList.get(0).getDescription());

        assertThat(entity, Matchers.is(notNullValue()));

    }

}
