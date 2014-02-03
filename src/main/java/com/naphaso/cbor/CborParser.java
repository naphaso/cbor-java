package com.naphaso.cbor;

import com.naphaso.cbor.io.Input;
import com.naphaso.cbor.type.*;
import com.naphaso.cbor.type.special.CborBoolean;
import org.omg.CORBA._IDLTypeStub;

import java.io.IOException;
import java.util.HashMap;
import java.util.Stack;

/**
 * Created by wolong on 03/02/14.
 */


public class CborParser implements CborListener {

    public void parse(Input input) throws IOException {
        CborReader reader = new CborReader(input);
        reader.setListener(this);
        reader.run();
    }

    private CborNumber currentTag;
    private Stack<ParseState> parseStateStack = new Stack<ParseState>();

    @Override
    public void onNumber(CborNumber number) {
        if(currentTag != null) {
            number.setTag(currentTag);
            currentTag = null;
        }

        onObject(number);
    }

    @Override
    public void onBytes(byte[] bytes) {
        CborBytes value = new CborBytes(bytes);

        if(currentTag != null) {
            value.setTag(currentTag);
            currentTag = null;
        }

        onObject(value);
    }

    @Override
    public void onString(String s) {
        CborString value = new CborString(s);

        if(currentTag != null) {
            value.setTag(currentTag);
            currentTag = null;
        }

        onObject(value);
    }

    @Override
    public void onArray(CborNumber size) {
        parseStateStack.push(ParseState.parseArray(size.intValue(), currentTag));
        currentTag = null;
    }

    @Override
    public void onMap(CborNumber size) {
        parseStateStack.push(ParseState.parseMap(size.intValue(), currentTag));
        currentTag = null;
    }

    @Override
    public void onTag(CborNumber tag) {
        currentTag = tag;
    }

    @Override
    public void onSpecial(CborNumber code) {
        CborSpecial value = CborSpecial.forValue(code);

        if(currentTag != null) {
            value.setTag(currentTag);
            currentTag = null;
        }

        onObject(value);
    }

    private void onObject(CborObject object) {
        if(parseStateStack.empty()) {
            onCompleteObject(object);
        } else {
            ParseState state = parseStateStack.peek();
            if(state.type == ParseType.MAP) {
                if(state.currentKey == null) {
                    state.currentKey = object;
                } else {
                    state.currentMap.put(state.currentKey, object);
                    state.currentKey = null;

                    state.currentIndex++;
                    if(state.currentIndex == state.maximumIndex) {
                        parseStateStack.pop();
                        CborMap map = new CborMap(state.currentMap);
                        map.setTag(state.tag);
                        onCompleteObject(map);
                    }
                }
            } else {
                state.currentArray[state.currentIndex++] = object;

                if(state.currentIndex == state.maximumIndex) {
                    parseStateStack.pop();
                    CborArray array = new CborArray(state.currentArray);
                    array.setTag(state.tag);
                    onCompleteObject(array);
                }
            }
        }
    }

    private void onCompleteObject(CborObject object) {
        System.out.println("complete object: " + object);
    }

    private static enum ParseType {
        MAP, ARRAY
    }

    private static class ParseState {
        public static ParseState parseArray(int size, CborNumber tag) {
            ParseState state = new ParseState();
            state.type = ParseType.ARRAY;
            state.currentArray = new CborObject[size];
            state.currentIndex = 0;
            state.maximumIndex = size;
            state.tag = tag;
            return state;
        }

        public static ParseState parseMap(int size, CborNumber tag) {
            ParseState state = new ParseState();
            state.type = ParseType.MAP;
            state.currentMap = new HashMap<CborObject, CborObject>(size);
            state.currentKey = null;
            state.currentIndex = 0;
            state.maximumIndex = size;
            state.tag = tag;
            return state;
        }


        ParseType type;
        HashMap<CborObject, CborObject> currentMap;
        CborObject[] currentArray;
        CborObject currentKey;
        CborNumber tag;
        int currentIndex;
        int maximumIndex;
    }
}
