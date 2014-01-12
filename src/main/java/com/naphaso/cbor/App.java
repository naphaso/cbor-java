package com.naphaso.cbor;

import com.naphaso.cbor.io.Input;
import com.naphaso.cbor.io.Output;
import com.naphaso.cbor.io.StreamInput;
import com.naphaso.cbor.io.StreamOutput;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

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
        writer.writeInt(123);

        // key 2
        writer.writeString("world");
        // value 2
        writer.writeArray(1);
        writer.writeString("hihihi");

        output.close();
    }

    public void testRead() throws IOException {
        FileInputStream fis = new FileInputStream("test.cbor");
        Input input = new StreamInput(fis);
        CborReader reader = new CborReader(input);

        reader.run();
    }


    public static void main( String[] args ) throws IOException {

        new App().testRead();
    }
}
