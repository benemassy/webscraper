package reader;

import entity.CoreEntity;
import com.google.inject.Inject;
import collector.GoodsCollector;
import service.CoreService;
import java.io.IOException;
import java.util.List;

public class WebPageReaderImpl implements WebPageReader {

    @Inject
    private GoodsCollector goodsCollector;

    @Inject
    private CoreService<CoreEntity> coreService;


    @Override
    public void readInGoodsData(String source, String classItem) throws RuntimeException{

        try{
            List<CoreEntity> goodsEntities = goodsCollector.collectGoodsFrom(source, classItem);

            coreService.addAllItems(goodsEntities);

        } catch (IOException ioe) {
            throw new WebPageReaderException(ioe);
        }

    }

    private class WebPageReaderException extends RuntimeException {

        WebPageReaderException(Throwable cause) {
            super(cause);

        }
    }

}
