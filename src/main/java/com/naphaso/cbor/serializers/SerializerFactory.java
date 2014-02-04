package com.naphaso.cbor.serializers;

import com.naphaso.cbor.exception.CborException;
import com.naphaso.cbor.util.ObjectMap;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

/**
 * Created by wolong on 03/02/14.
 */
public class SerializerFactory {
    private static SerializerFactory instance = new SerializerFactory();

    private SerializerFactory() {
        registerDefaultSerializers();
    }

    public static SerializerFactory getInstance() {
        return instance;
    }

    private ObjectMap<Type, Serializer> preparedSerializers = new ObjectMap<Type, Serializer>(1024);
    private ObjectMap<Type, Class<? extends Serializer>> parametrizedSerializers = new ObjectMap<Type, Class<? extends Serializer>>();

    public Serializer getSerializer(Class clazz) {
        Serializer serializer = preparedSerializers.get(clazz);
        if(serializer != null) {
            return serializer;
        }

        throw new CborException("unknown serializer");
    }

    public Serializer getSerializer(Type type) {
        Serializer serializer = preparedSerializers.get(type);

        if(serializer != null) {
            return serializer;
        }

        if(type instanceof ParameterizedType) {
            final ParameterizedType parameterizedType = (ParameterizedType) type;
            final Class<? extends Serializer> parametrizedSerializerClass = parametrizedSerializers.get(parameterizedType.getRawType());

            if(parametrizedSerializerClass != null) {
                try {
                    final Type[] genericTypes = parameterizedType.getActualTypeArguments();
                    final int genericArgsCount = genericTypes.length;
                    Constructor constructor;
                    if(genericArgsCount == 1) {
                        constructor = parametrizedSerializerClass.getConstructor(Type.class);
                    } else if(genericArgsCount == 2) {
                        constructor = parametrizedSerializerClass.getConstructor(Type.class, Type.class);
                    } else {
                        final Class[] classes = new Class[genericArgsCount];
                        for(int i = 0; i < genericArgsCount; i++) {
                            classes[i] = Type.class;
                        }
                        constructor = parametrizedSerializerClass.getConstructor(classes);
                    }

                    serializer = (Serializer) constructor.newInstance(genericTypes);
                } catch (NoSuchMethodException e) {

                } catch (InvocationTargetException e) {

                } catch (InstantiationException e) {

                } catch (IllegalAccessException e) {

                }
            }
        }

        if(serializer == null) {
            throw new CborException("unknown serializer");
        }

        preparedSerializers.put(type, serializer);
        return serializer;
    }

    private void registerDefaultSerializers() {
        preparedSerializers.put(byte.class, new SimpleSerializers.ByteSerializer());
        preparedSerializers.put(short.class, new SimpleSerializers.ShortSerializer());
        preparedSerializers.put(int.class, new SimpleSerializers.IntSerializer());
        preparedSerializers.put(long.class, new SimpleSerializers.LongSerializer());
        preparedSerializers.put(Byte.class, new SimpleSerializers.ByteSerializer());
        preparedSerializers.put(Short.class, new SimpleSerializers.ShortSerializer());
        preparedSerializers.put(Integer.class, new SimpleSerializers.IntSerializer());
        preparedSerializers.put(Long.class, new SimpleSerializers.LongSerializer());
        preparedSerializers.put(byte[].class, new SimpleSerializers.BytesSerializer());
        preparedSerializers.put(String.class, new SimpleSerializers.StringSerializer());

        parametrizedSerializers.put(ArrayList.class, SimpleSerializers.ArrayListSerializer.class);
        parametrizedSerializers.put(LinkedList.class, SimpleSerializers.LinkedListSerializer.class);
        parametrizedSerializers.put(HashMap.class, SimpleSerializers.HashMapSerializer.class);

        // unknown types serializers
        preparedSerializers.put(HashMap.class, new SimpleSerializers.UnknownHashMapSerializer());
        preparedSerializers.put(ArrayList.class, new SimpleSerializers.UnknownArrayListSerializer());
        preparedSerializers.put(LinkedList.class, new SimpleSerializers.UnknownLinkedListSerializer());
    }

}
