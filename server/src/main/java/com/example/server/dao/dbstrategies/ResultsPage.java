package com.example.server.dao.dbstrategies;
import com.amazonaws.services.dynamodbv2.document.Item;

import java.util.ArrayList;
import java.util.List;

public class ResultsPage {

    /**
     * The data values returned in this page of results
     */
    private List<Item> values;

    /**
     * The last value returned in this page of results
     * This value is typically included in the query for the next page of results
     */
    private String lastKey;

    public ResultsPage() {
        values = new ArrayList<>();
        lastKey = null;
    }

    // Values property

    public void addValue(Item v) {
        values.add(v);
    }

    public boolean hasValues() {
        return (values != null && values.size() > 0);
    }

    public List<Item> getValues() {
        return values;
    }

    // LastKey property

    public void setLastKey(String value) {
        lastKey = value;
    }

    public String getLastKey() {
        return lastKey;
    }

    public boolean hasLastKey() {
        return (lastKey != null && lastKey.length() > 0);
    }
}
