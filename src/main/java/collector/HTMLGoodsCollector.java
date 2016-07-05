package collector;

import entity.CoreEntity;
import entity.GoodsEntity;
import com.google.inject.Inject;
import com.google.inject.name.Named;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class HTMLGoodsCollector implements GoodsCollector {

    private FORMAT pageFormat;

    @Inject
    @Named("default.url.encoding")
    private final String ENCODING = "";

    @Override
    public FORMAT getFormat() {
        return pageFormat;
    }

    @Override
    public void setFormat(FORMAT format) {
        pageFormat = format;
    }

    @Override
    public List<CoreEntity> collectGoodsFrom(String source, String classItem) throws IOException {

        Document webPageDocument;

        if(source.startsWith("http")) {
            webPageDocument = Jsoup.connect(source).get();
        }
        else{
            webPageDocument = Jsoup.parse(source);
        }

        List<CoreEntity> goodsEntities = new ArrayList<>();

        ClassLoader loader = Thread.currentThread().getContextClassLoader();

        Elements elements = webPageDocument.getElementsByClass(classItem);

        for(Element element : elements) {

            GoodsEntity goodsEntity = new GoodsEntity();

            String unitPrice = element.getElementsByClass("pricePerUnit").get(0).text();
            String title = element.children().select("a").get(0).text();
            String goodsLink = element.getElementsByTag("a").attr("href");

            Document childDoc = null;
            String contentLength = null;

            if(goodsLink.startsWith("file")) {

                //local content
                String resource = FilenameUtils.getBaseName(goodsLink) + "." + FilenameUtils.getExtension(goodsLink);
                InputStream resourceStream = loader.getResourceAsStream(resource);
                contentLength = String.valueOf(resourceStream.available());
                String webPageString = IOUtils.toString(resourceStream,"UTF-8");
                childDoc = Jsoup.parseBodyFragment(webPageString);

            }
            else{
                Connection.Response response = Jsoup.connect(goodsLink).execute();
                contentLength = response.header("content-Length");
                childDoc = response.parse();
            }

            String description = childDoc.getElementsByClass("productText").get(0).getElementsByTag("p").text();

            goodsEntity.setTitle(title);
            goodsEntity.setUnitPrice(convertUnitPriceToPrice(unitPrice));
            goodsEntity.setSize(convertContentSizeToKB(contentLength));
            goodsEntity.setDescription(description);

            goodsEntities.add(goodsEntity);
        }

        return goodsEntities;
    }

    public String getGoodsContentFromURL(String urlString) throws IOException {

        URL url = new URL(urlString);

        return IOUtils.toString(url, ENCODING);
    }

    private String convertUnitPriceToPrice(String unitPrice) {

        return unitPrice.replaceAll("[^0-9.]","");

    }

    private String convertContentSizeToKB(String bytes) {

        String size = "0";

        int bytesAsInt = Integer.parseInt(bytes);

        if(bytesAsInt > 0) {

            double bytesAsDouble = bytesAsInt / 1024;

            DecimalFormat decimalFormat = new DecimalFormat("0.00");
            size = decimalFormat.format(bytesAsDouble).concat("kb");

        }

        return size;

    }

}
