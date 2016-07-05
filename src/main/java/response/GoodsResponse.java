package response;

import entity.CoreEntity;

import java.util.Collection;
import java.util.List;

@SuppressWarnings("unused")
public class GoodsResponse {

    private Collection<CoreEntity> results;

    private String total;

    public void setResults(Collection<CoreEntity> results) {
        this.results = results;
    }

    public Collection<CoreEntity> getResults() {
        return results;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }
}
