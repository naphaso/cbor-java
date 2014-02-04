//
// Cbor for Java
//
// Copyright (C) 2014 Stanislav Ovsyannikov (Wolong Naphaso)
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
// http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
//

package com.naphaso.cbor;

import com.naphaso.cbor.exception.CborException;
import com.naphaso.cbor.io.Input;
import com.naphaso.cbor.type.*;
import com.naphaso.cbor.util.CborObjectListener;

import java.io.IOException;
import java.util.HashMap;
import java.util.Stack;

public class CborParser implements CborListener, CborObjectListener {
    private CborObjectListener listener;
    private CborObject parsedObject;

    public void parse(Input input, CborObjectListener listener) throws IOException {
        this.listener = listener;
        CborReader reader = new CborReader(input);
        reader.setListener(this);
        reader.run();
    }

    public CborObject parse(Input input) throws IOException {
        this.listener = this;
        CborReader reader = new CborReader(input);
        reader.setListener(this);
        reader.run();

        if(parsedObject != null) {
            return parsedObject;
        } else {
            throw new CborException("empty stream");
        }
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
            listener.onCborObject(object);
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
                        listener.onCborObject(map);
                    }
                }
            } else {
                state.currentArray[state.currentIndex++] = object;

                if(state.currentIndex == state.maximumIndex) {
                    parseStateStack.pop();
                    CborArray array = new CborArray(state.currentArray);
                    array.setTag(state.tag);
                    listener.onCborObject(array);
                }
            }
        }
    }

    @Override
    public void onCborObject(CborObject object) {
        parsedObject = object;
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
