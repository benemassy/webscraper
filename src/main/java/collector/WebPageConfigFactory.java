package collector;

import java.util.HashMap;
import java.util.Map;

//Unused
public class WebPageConfigFactory<T extends GoodsCollector> {

    private Map<String,T> configurations = new HashMap<>();

    public void registerConfig(String id, T configuration) {
        configurations.put(id, configuration);
    }

    public T getConfig(String id) {
        return configurations.get(id);
    }

}
