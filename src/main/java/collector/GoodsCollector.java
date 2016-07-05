package collector;

import entity.CoreEntity;
import java.io.IOException;
import java.util.List;

@SuppressWarnings("unused")
public interface GoodsCollector {

    enum FORMAT{
        HTML,
        JSON;
    }

    void setFormat(FORMAT format);

    FORMAT getFormat();

    List<CoreEntity> collectGoodsFrom(String source, String classItem) throws IOException;

    String getGoodsContentFromURL(String url) throws IOException;

}
