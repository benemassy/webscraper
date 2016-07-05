package controller;

import entity.CoreEntity;
import com.google.gson.Gson;
import com.google.inject.Inject;
import com.google.inject.name.Named;
import collector.GoodsCollector;
import org.apache.commons.lang3.StringUtils;
import reader.WebPageReader;
import response.GoodsResponse;
import service.CoreService;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Currency;
import java.util.Locale;


public class GoodsItemController {

    @Inject
    private CoreService<CoreEntity> coreService;

    @Inject
    private WebPageReader webPageReader;

    @Inject
    private GoodsCollector goodsCollector;

    @Inject
    private Gson gson;

    private String DEFAULT_GOODS_SOURCE_URL = "";

    @Inject
    @Named("default.goods.source.item.parent.identifier")
    private String DEFAULT_GOODS_ITEM_IDENTIFIER = "";

    @Inject
    public GoodsItemController(@Named("default.goods.source.url") String goodsSource) {
        DEFAULT_GOODS_SOURCE_URL = goodsSource;
    }

    public String getDefaultsGoodsSource() {
        return DEFAULT_GOODS_SOURCE_URL;
    }

    public void setItemsFromUrl(String url) {

        webPageReader.readInGoodsData(url, DEFAULT_GOODS_ITEM_IDENTIFIER);

    }

    public void setItemsFromSource(String source) throws Exception {

        String itemsString = goodsCollector.getGoodsContentFromURL(source);

        webPageReader.readInGoodsData(itemsString, DEFAULT_GOODS_ITEM_IDENTIFIER);

    }

    public String getItemsAsJSon() {

        Collection<CoreEntity> goodsList = coreService.getAllItems(CoreEntity.class);

        GoodsResponse goodsResponse = new GoodsResponse();
        goodsResponse.setResults(goodsList);

        goodsResponse.setTotal(calculateTotalPrice(goodsList));

        return gson.toJson(goodsResponse);

    }

    private String calculateTotalPrice(Collection<CoreEntity> goodsList) {

        Locale.setDefault(Locale.UK);

        BigDecimal totalPrice = BigDecimal.ZERO;

        for(CoreEntity item : goodsList) {

            String amountString = item.getUnitPrice();
            String amount = amountString.replace(Currency.getInstance(Locale.UK).getSymbol(), StringUtils.EMPTY);
            totalPrice = totalPrice.add(new BigDecimal(amount));

        }

        return (Currency.getInstance(Locale.UK).getSymbol() + totalPrice.toString());

    }

}
