package com.naphaso.cbor;

import com.naphaso.cbor.io.Input;
import com.naphaso.cbor.io.Output;
import com.naphaso.cbor.io.StreamInput;
import com.naphaso.cbor.io.StreamOutput;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

/**
 * Hello world!
 *
 */
public class App 
{
    public void testWrite() throws IOException {
        FileOutputStream fos = new FileOutputStream("test.cbor");
        Output output = new StreamOutput(fos);
        CborWriter writer = new CborWriter(output);

        writer.writeMap(2);

        // key 1
        writer.writeString("hello");
        // value 1
        writer.writeInt(new BigInteger("5126378562348346534857632482374652582763453486542735654325645832478457363787456436263574"));

        // key 2
        writer.writeString("world");
        // value 2
        writer.writeArray(1);
        writer.writeString("hihihi");

        output.close();
    }

    public void testWrite2() throws IOException {
        FileOutputStream fos = new FileOutputStream("test.cbor");
        Output output = new StreamOutput(fos);
        CborWriter writer = new CborWriter(output);

        Map<String, String> someMap = new HashMap<String, String>();
        someMap.put("hello", "world");
        someMap.put("one", "two");
        someMap.put("test", "catch");

        writer.write(someMap);

        output.close();
    }

    public void testRead() throws IOException {
        FileInputStream fis = new FileInputStream("test.cbor");
        Input input = new StreamInput(fis);
        CborReader reader = new CborReader(input);
        CborDebugListener listener = new CborDebugListener();

        reader.setListener(listener);

        reader.run();
    }


    public static void main( String[] args ) throws IOException {
        new App().testWrite2();
        new App().testRead();
    }
}
