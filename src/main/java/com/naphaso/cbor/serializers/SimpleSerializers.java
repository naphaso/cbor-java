package com.naphaso.cbor.serializers;

import com.naphaso.cbor.type.*;
import com.naphaso.cbor.type.number.CborInteger;
import com.naphaso.cbor.type.number.CborShort;
import com.sun.tools.javac.resources.compiler;

import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.util.*;

/**
 * Created by wolong on 04/02/14.
 */
public class SimpleSerializers {
    public static class ByteSerializer extends Serializer<Byte> {

        @Override
        public CborObject write(Byte object) {
            return new CborShort(object);
        }

        @Override
        public Byte read(CborObject object) {
            return object.asNumber().byteValue();
        }
    }

    public static class IntSerializer extends Serializer<Integer> {


        @Override
        public CborObject write(Integer object) {
            return new CborShort(object);
        }

        @Override
        public Integer read(CborObject object) {
            return object.asNumber().intValue();
        }
    }

    public static class ShortSerializer extends Serializer<Short> {

        @Override
        public CborObject write(Short object) {
            return new CborShort(object);
        }

        @Override
        public Short read(CborObject object) {
            return object.asNumber().shortValue();
        }
    }

    public static class LongSerializer extends Serializer<Long> {

        @Override
        public CborObject write(Long object) {
            return new CborInteger(object);
        }

        @Override
        public Long read(CborObject object) {
            return object.asNumber().longValue();
        }
    }

    public static class BytesSerializer extends Serializer<byte[]> {

        @Override
        public CborObject write(byte[] object) {
            return new CborBytes(object);
        }

        @Override
        public byte[] read(CborObject object) {
            return object.asBytes().getValue();
        }
    }

    public static class StringSerializer extends Serializer<String> {

        @Override
        public CborObject write(String object) {
            return new CborString(object);
        }

        @Override
        public String read(CborObject object) {
            return object.asString().getValue();
        }
    }

    public static class HashMapSerializer extends Serializer<HashMap> {
        private final Type keyType;
        private final Type valueType;
        private Serializer keySerializer;
        private Serializer valueSerializer;

        public HashMapSerializer(Type keyType, Type valueType) {
            this.keyType = keyType;
            this.valueType = valueType;
            this.keySerializer = null;
            this.valueSerializer = null;
        }

        @Override
        public CborObject write(HashMap object) {
            final SerializerFactory serializerFactory = SerializerFactory.getInstance();
            final HashMap<CborObject, CborObject> convertedMap = new HashMap<CborObject, CborObject>(object.size());

            if(keySerializer == null) {
                keySerializer = serializerFactory.getSerializer(keyType);
            }

            if(valueSerializer == null) {
                valueSerializer = serializerFactory.getSerializer(valueType);
            }

            for(Object obj : object.entrySet()) {
                final Map.Entry entry = (Map.Entry) obj;
                convertedMap.put(keySerializer.write(entry.getKey()), valueSerializer.write(entry.getValue()));
            }

            return new CborMap(convertedMap);
        }

        @Override
        public HashMap read(CborObject object) {
            final SerializerFactory serializerFactory = SerializerFactory.getInstance();
            final Map<CborObject, CborObject> cborMap = object.asMap().getMap();
            final HashMap convertedMap = new HashMap(cborMap.size());

            if(keySerializer == null) {
                keySerializer = serializerFactory.getSerializer(keyType);
            }

            if(valueSerializer == null) {
                valueSerializer = serializerFactory.getSerializer(valueType);
            }

            for(Map.Entry<CborObject, CborObject> entry : cborMap.entrySet()) {
                convertedMap.put(keySerializer.read(entry.getKey()), valueSerializer.read(entry.getValue()));
            }

            return convertedMap;
        }
    }

    public static class ArrayListSerializer extends Serializer<ArrayList> {
        private final Type valueType;
        private Serializer valueSerializer;

        public ArrayListSerializer(Class valueType) {
            this.valueType = valueType;
            this.valueSerializer = null;
        }

        @Override
        public CborObject write(ArrayList object) {
            if(valueSerializer == null) {
                valueSerializer = SerializerFactory.getInstance().getSerializer(valueType);
            }

            final CborObject[] list = new CborObject[object.size()];
            int index = 0;
            final Iterator iterator = object.iterator();
            while(iterator.hasNext()) {
                list[index++] = valueSerializer.write(iterator.next());
            }

            return new CborArray(list);
        }

        @Override
        public ArrayList read(CborObject object) {
            if(valueSerializer == null) {
                valueSerializer = SerializerFactory.getInstance().getSerializer(valueType);
            }

            final CborObject[] array = object.asArray().getValue();
            final ArrayList list = new ArrayList(array.length);


            for(int i = 0; i < array.length; i++) {
                list.add(valueSerializer.read(array[i]));
            }

            return list;
        }
    }

    public static class LinkedListSerializer extends Serializer<LinkedList> {
        private final Type valueType;
        private Serializer valueSerializer;

        public LinkedListSerializer(Type valueType) {
            this.valueType = valueType;
        }


        @Override
        public CborObject write(LinkedList object) {
            if(valueSerializer == null) {
                valueSerializer = SerializerFactory.getInstance().getSerializer(valueType);
            }

            final CborObject[] list = new CborObject[object.size()];
            int index = 0;
            final Iterator iterator = object.iterator();
            while(iterator.hasNext()) {
                list[index++] = valueSerializer.write(iterator.next());
            }

            return new CborArray(list);
        }



        @Override
        public LinkedList read(CborObject object) {
            if(valueSerializer == null) {
                valueSerializer = SerializerFactory.getInstance().getSerializer(valueType);
            }

            final CborObject[] array = object.asArray().getValue();
            final LinkedList list = new LinkedList();
            for(int i = 0; i < array.length; i++) {
                list.add(valueSerializer.read(array[i]));
            }

            return list;
        }
    }


    public static class UnknownHashMapSerializer extends Serializer<HashMap> {

        @Override
        public CborObject write(HashMap object) {
            final SerializerFactory serializerFactory = SerializerFactory.getInstance();
            final HashMap<CborObject, CborObject> convertedMap = new HashMap<CborObject, CborObject>(object.size());

            for(Object obj : object.entrySet()) {
                final Map.Entry entry = (Map.Entry) obj;
                final Object key = entry.getKey();
                final Object value = entry.getValue();
                final Serializer keySerializer = serializerFactory.getSerializer(key.getClass());
                final Serializer valueSerializer = serializerFactory.getSerializer(value.getClass());
                convertedMap.put(keySerializer.write(key), valueSerializer.write(value));
            }

            return new CborMap(convertedMap);
        }

        @Override
        public HashMap read(CborObject object) {
            return (HashMap) object.toObject();
        }
    }

    public static class UnknownArrayListSerializer extends Serializer<ArrayList> {

        @Override
        public CborObject write(ArrayList object) {
            final SerializerFactory serializerFactory = SerializerFactory.getInstance();
            final CborObject[] list = new CborObject[object.size()];

            for(int i = 0; i < object.size(); i++) {
                final Object value = object.get(i);
                final Serializer serializer = serializerFactory.getSerializer(value.getClass());
                list[i] = serializer.write(value);
            }

            return new CborArray(list);
        }

        @Override
        public ArrayList read(CborObject object) {
            final CborObject[] array = (CborObject[]) object.toObject();
            final ArrayList list = new ArrayList(array.length);

            for(int i = 0; i < array.length; i++) {
                list.add(array[i].toObject());
            }

            return list;
        }
    }

    public static class UnknownLinkedListSerializer extends Serializer<LinkedList> {

        @Override
        public CborObject write(LinkedList object) {
            final SerializerFactory serializerFactory = SerializerFactory.getInstance();
            final CborObject[] list = new CborObject[object.size()];

            final Iterator iterator = object.iterator();
            int index = 0;
            while(iterator.hasNext()) {
                final Object value = iterator.next();
                list[index++] = SerializerFactory.getInstance().getSerializer(value.getClass()).write(value);
            }

            return new CborArray(list);
        }

        @Override
        public LinkedList read(CborObject object) {
            final CborObject[] array = (CborObject[]) object.toObject();
            final LinkedList list = new LinkedList();

            for(int i = 0; i < array.length; i++) {
                list.add(array[i].toObject());
            }

            return list;
        }
    }


}
