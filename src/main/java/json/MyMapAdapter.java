package json;

import json.model.MyMapEntryType;
import model.MyMapType;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by andrew on 9/2/15.
 */
public class MyMapAdapter extends XmlAdapter<MyMapType,Map<Integer, String>> {

    @Override
    public MyMapType marshal(Map<Integer , String> arg0) throws Exception {
        MyMapType myMapType = new MyMapType();
        for(Map.Entry<Integer, String> entry : arg0.entrySet()) {
            MyMapEntryType myMapEntryType = new MyMapEntryType();
            myMapEntryType.key = entry.getKey();
            myMapEntryType.value = entry.getValue();
            myMapType.entry.add(myMapEntryType);
        }
        return myMapType;
    }

    @Override
    public Map<Integer, String> unmarshal(MyMapType arg0) throws Exception {
        HashMap<Integer, String> hashMap = new HashMap<Integer, String>();
        for(MyMapEntryType myMapEntryType : arg0.entry) {
            hashMap.put(myMapEntryType.key, myMapEntryType.value );
        }
        return hashMap;
    }
}
