package com.naphaso.cbor.type;

/**
 * Created by wolong on 1/12/14.
 */
public interface Value {
    public MajorType majorType();
    public MinorType minorType();



    public boolean isNilValue();

    public boolean isBooleanValue();

    public boolean isIntegerValue();

    public boolean isFloatValue();

    public boolean isArrayValue();

    public boolean isMapValue();

    public boolean isRawValue();
    /*&

    public NilValue asNilValue();

    public BooleanValue asBooleanValue();

    public IntegerValue asIntegerValue();

    public FloatValue asFloatValue();

    public ArrayValue asArrayValue();

    public MapValue asMapValue();

    public RawValue asRawValue();

    public void writeTo(Packer pk) throws IOException;
*/
    public StringBuilder toString(StringBuilder sb);
}
